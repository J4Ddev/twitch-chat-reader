package dev.j4d.twitchchatreader.activechannel.domain;

import java.time.Instant;

public class ActiveChannel {

    private final long id;
    private final String channel;
    private final Instant timeOfJoin;

    public ActiveChannel(long id, String channel, Instant timeOfJoin) {
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

    public Instant getTimeOfJoin() {
        return timeOfJoin;
    }
}
