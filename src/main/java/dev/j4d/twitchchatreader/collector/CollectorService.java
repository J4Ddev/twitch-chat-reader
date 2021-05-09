package dev.j4d.twitchchatreader.collector;

import dev.j4d.twitchchatreader.activechannel.service.ActiveChannelService;
import dev.j4d.twitchchatreader.event.service.EventService;
import dev.j4d.twitchchatreader.twitchconnection.TwitchConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectorService {

    private static final Logger logger = LoggerFactory.getLogger(CollectorService.class);

    private final TwitchConnection twitchConnection;
    private final EventService eventService;
    private final ActiveChannelService activeChannelService;

    public CollectorService(TwitchConnection twitchConnection, EventService eventService, ActiveChannelService activeChannelService) {
        this.twitchConnection = twitchConnection;
        this.eventService = eventService;
        this.activeChannelService = activeChannelService;
    }

    @Scheduled(fixedDelay = 10)
    public void listenForMessage() {
        if (!twitchConnection.isLoggedIn()) {
            return;
        }
        final var optionalInput = twitchConnection.read();
        if (optionalInput.isEmpty()) {
            return;
        }
        final var input = optionalInput.get();
        logInput(input);
        final var inputParts = input.split(" ");
        if (shouldIgnore(inputParts[1])) {
            return;
        }
        switch (inputParts[0]) {
            case "PING":
                twitchConnection.send("PONG :tmi.twitch.tv");
                break;
            case ":tmi.twitch.tv":
                if ("001".equals(inputParts[1])) {
                    twitchConnection.send("CAP REQ :twitch.tv/membership");
                } else {
                    switch (inputParts[4]) {
                        case ":twitch.tv/membership" -> twitchConnection.send("CAP REQ :twitch.tv/tags");
                        case ":twitch.tv/tags" -> twitchConnection.send("CAP REQ :twitch.tv/commands");
                        case ":twitch.tv/commands" -> {
                            logger.info("Initialization complete");
                            activeChannelService.joinActiveChannels();
                        }
                    }
                }
                break;
        }
        if (inputParts.length >= 3) {
            switch (inputParts[2]) {
                case "PRIVMSG":
                    eventService.saveChatMessageEvent(input);
                    break;
                case "CLEARMSG":
                    eventService.saveClearMessageEvent(input);
                    break;
                case "CLEARCHAT":
                    if (inputParts.length == 5) {
                        eventService.saveClearChatEvent(input);
                    } else if (inputParts.length == 4) {
                        eventService.saveGlobalClearChatEvent(input);
                    }
                    break;
                case "USERNOTICE":
                    eventService.saveUserNoticeEvent(input);
                    break;
            }
            switch (inputParts[1]) {
                case "JOIN":
                    eventService.saveUserJoinEvent(input);
                    break;
                case "PART":
                    eventService.saveUserLeaveEvent(input);
                    break;
                case "HOSTTARGET":
                    if (!input.contains(":-")) {
                        eventService.saveHostEnabledEvent(input);
                    } else {
                        eventService.saveHostDisabledEvent(input);
                    }
                    break;
            }
        }

        // inputParts[1] == 001 -> Logged in, send: CAP REQ :twitch.tv/membership
        // inputParts[1] == CAP * ACK && inputParts[2] == :twitch.tv/membership -> CAP REQ :twitch.tv/tags
        // inputParts[1] == CAP * ACK && inputParts[2] == :twitch.tv/tags -> CAP REQ :twitch.tv/commands
        // inputParts[1] == CAP * ACK && inputParts[2] == :twitch.tv/commands -> Initialization complete, log?
        // inputParts[1] == JOIN -> Join event
        // inputParts[1] == PART -> Leave event
        // inputParts[1] == HOSTTARGET ->
        //                             -> input !contains :- -> Host enabled event
        //                             -> else -> Host disabled event
        // inputParts[2] == PRIVMSG -> Chat message event
        // inputParts[2] == CLEARMSG -> Clear message event
        // inputParts[2] == CLEARCHAT ->
        //                            -> inputParts.size == 5 -> Clear chat event
        //                            -> inputParts.size == 4 -> Global clear chat event
        // input == PING :tmi.twitch.tv  -> PONG :tmi.twitch.tv
    }

    private void logInput(String input) {
        logger.debug("FROM TWITCH >> {}", input);
    }

    private boolean shouldIgnore(String number) {
        return List.of("002", "003", "004", "375", "372", "376").contains(number);
    }
}
