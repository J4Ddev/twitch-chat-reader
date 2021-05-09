package dev.j4d.twitchchatreader.activechannel.service;

import dev.j4d.twitchchatreader.activechannel.domain.ActiveChannel;
import dev.j4d.twitchchatreader.activechannel.repository.ActiveChannelRepository;
import dev.j4d.twitchchatreader.twitchconnection.TwitchConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

@Service
public class ActiveChannelService {

    private static final Logger logger = LoggerFactory.getLogger(ActiveChannelService.class);

    private final ActiveChannelRepository repository;
    private final TwitchConnection twitchConnection;
    private final Supplier<Instant> instant;

    public ActiveChannelService(ActiveChannelRepository repository, TwitchConnection twitchConnection, Supplier<Instant> instant) {
        this.repository = repository;
        this.twitchConnection = twitchConnection;
        this.instant = instant;
    }

    public void joinNewActiveChannels(List<String> activeChannels) {
        logger.debug("Joining {} active channels", activeChannels.size());
        activeChannels.forEach(channel -> {
            repository.insert(channel, instant.get().toString());
            twitchConnection.send("JOIN #" + channel);
        });
        logger.info("Joined {} active channels", activeChannels.size());
    }

    public void joinActiveChannels() {
        logger.debug("Joining active channels");
        final var activeChannels = repository.getAll();
        activeChannels.forEach(activeChannel -> twitchConnection.send("JOIN #" + activeChannel.getChannel()));
        logger.info("Joined {} active channels", activeChannels.size());
    }

    public List<ActiveChannel> getAll() {
        return repository.getAll();
    }
}
