package dev.j4d.twitchchatreader.event.service;

import dev.j4d.twitchchatreader.event.assembler.EventJsonAssembler;
import dev.j4d.twitchchatreader.event.assembler.MetadataAssembler;
import dev.j4d.twitchchatreader.event.domain.EventType;
import dev.j4d.twitchchatreader.event.domain.Version;
import dev.j4d.twitchchatreader.event.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final MetadataAssembler metadataAssembler;
    private final EventJsonAssembler eventJsonAssembler;
    private final ChatMessageProducer chatMessageProducer;
    private final PresenceProducer presenceProducer;
    private final HostProducer hostProducer;
    private final PunishmentProducer punishmentProducer;
    private final UserNoticeProducer userNoticeProducer;

    public EventService(MetadataAssembler metadataAssembler,
                        EventJsonAssembler eventJsonAssembler,
                        ChatMessageProducer chatMessageProducer,
                        PresenceProducer presenceProducer,
                        HostProducer hostProducer,
                        PunishmentProducer punishmentProducer,
                        UserNoticeProducer userNoticeProducer) {
        this.metadataAssembler = metadataAssembler;
        this.eventJsonAssembler = eventJsonAssembler;
        this.chatMessageProducer = chatMessageProducer;
        this.presenceProducer = presenceProducer;
        this.hostProducer = hostProducer;
        this.punishmentProducer = punishmentProducer;
        this.userNoticeProducer = userNoticeProducer;
    }

    public void saveChatMessageEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.CHAT_MESSAGE, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving chat message event with id: {}", eventId);
        chatMessageProducer.produce(correlationId, eventJson);
        logger.info("Saved chat message event with id: {}", eventId);
    }

    public void saveUserJoinEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.USER_JOIN, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving user join event with id: {}", eventId);
        presenceProducer.produce(correlationId, eventJson);
        logger.info("Saved user join event with id: {}", eventId);
    }

    public void saveUserLeaveEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.USER_LEAVE, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving user leave event with id: {}", eventId);
        presenceProducer.produce(correlationId, eventJson);
        logger.info("Saved user leave event with id: {}", eventId);
    }

    public void saveHostEnabledEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.HOST_ENABLED, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving host enabled event with id: {}", eventId);
        hostProducer.produce(correlationId, eventJson);
        logger.info("Saved host enabled event with id: {}", eventId);
    }

    public void saveHostDisabledEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.HOST_DISABLED, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving host disabled event with id: {}", eventId);
        hostProducer.produce(correlationId, eventJson);
        logger.info("Saved host disabled event with id: {}", eventId);
    }

    public void saveClearMessageEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.CLEAR_MESSAGE, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving clear message event with id: {}", eventId);
        punishmentProducer.produce(correlationId, eventJson);
        logger.info("Saved clear message event with id: {}", eventId);
    }

    public void saveClearChatEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.CLEAR_CHAT, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving clear chat event with id: {}", eventId);
        punishmentProducer.produce(correlationId, eventJson);
        logger.info("Saved clear chat event with id: {}", eventId);
    }

    public void saveGlobalClearChatEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.GLOBAL_CLEAR_CHAT, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving global clear chat event with id: {}", eventId);
        punishmentProducer.produce(correlationId, eventJson);
        logger.info("Saved global clear chat event with id: {}", eventId);
    }

    public void saveUserNoticeEvent(String rawData) {
        final var metadata = metadataAssembler.assemble(EventType.USER_NOTICE, Version.V1);
        final var eventJson = eventJsonAssembler.assemble(metadata, rawData);
        final var eventId = metadata.getEventId();
        final var correlationId = metadata.getCorrelationId();
        logger.debug("Saving user notice event with id: {}", eventId);
        userNoticeProducer.produce(correlationId, eventJson);
        logger.info("Saved user notice event with id: {}", eventId);
    }
}
