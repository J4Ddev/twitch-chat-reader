package dev.j4d.twitchchatreader.activechannel.rest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class CreateActiveChannelResource {

    private final List<String> activeChannels;

    @JsonCreator
    public CreateActiveChannelResource(List<String> activeChannels) {
        this.activeChannels = activeChannels;
    }

    public List<String> getActiveChannels() {
        return activeChannels;
    }
}
