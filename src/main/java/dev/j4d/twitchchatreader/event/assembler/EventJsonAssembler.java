package dev.j4d.twitchchatreader.event.assembler;

import dev.j4d.twitchchatreader.event.domain.Metadata;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class EventJsonAssembler {

    public String assemble(Metadata metadata, String rawData) {
        final var metadataJson = assemble(metadata);
        final var dataJson = new JSONObject().put("base64EncodedRawData", Base64.getEncoder().encodeToString(rawData.getBytes(StandardCharsets.UTF_8)));
        return new JSONObject().put("metadata", metadataJson).put("data", dataJson).toString();
    }

    private JSONObject assemble(Metadata metadata) {
        return new JSONObject()
                .put("eventId", metadata.getEventId())
                .put("correlationId", metadata.getCorrelationId())
                .put("eventType", metadata.getEventType())
                .put("version", metadata.getVersion())
                .put("source", metadata.getSource())
                .put("created", metadata.getCreated());
    }
}
