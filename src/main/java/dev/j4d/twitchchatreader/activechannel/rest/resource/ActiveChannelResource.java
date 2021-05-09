package dev.j4d.twitchchatreader.activechannel.rest.resource;

public class ActiveChannelResource {

    private final long id;
    private final String channel;
    private final String timeOfJoin;

    public ActiveChannelResource(long id, String channel, String timeOfJoin) {
        this.id = id;
        this.channel = channel;
        this.timeOfJoin = timeOfJoin;
    }

    public long getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public String getTimeOfJoin() {
        return timeOfJoin;
    }
}
