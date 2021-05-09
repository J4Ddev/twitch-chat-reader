package dev.j4d.twitchchatreader.event.assembler;

import dev.j4d.twitchchatreader.event.domain.EventType;
import dev.j4d.twitchchatreader.event.domain.Metadata;
import dev.j4d.twitchchatreader.event.domain.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class MetadataAssembler {

    private final String applicationName;
    private final Supplier<UUID> uuid;
    private final Supplier<Instant> instant;

    public MetadataAssembler(@Value("${spring.application.name}") String applicationName, Supplier<UUID> uuid, Supplier<Instant> instant) {
        this.applicationName = applicationName;
        this.uuid = uuid;
        this.instant = instant;
    }

    public Metadata assemble(EventType eventType, Version version) {
        return new Metadata(
                uuid.get().toString(),
                uuid.get().toString(),
                eventType.name(),
                version.name(),
                applicationName,
                instant.get().toString());
    }
}
