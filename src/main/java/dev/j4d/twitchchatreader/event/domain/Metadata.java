package dev.j4d.twitchchatreader.event.domain;

public class Metadata {

    private final String eventId;
    private final String correlationId;
    private final String eventType;
    private final String version;
    private final String source;
    private final String created;

    public Metadata(String eventId, String correlationId, String eventType, String version, String source, String created) {
        this.eventId = eventId;
        this.correlationId = correlationId;
        this.eventType = eventType;
        this.version = version;
        this.source = source;
        this.created = created;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getVersion() {
        return version;
    }

    public String getSource() {
        return source;
    }

    public String getCreated() {
        return created;
    }
}
