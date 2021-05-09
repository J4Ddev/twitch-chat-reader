package dev.j4d.twitchchatreader.activechannel.rest.resource;

import java.util.List;

public class JoinedStateResource {

    private final List<String> joined;
    private final List<String> duplicate;

    public JoinedStateResource(List<String> joined, List<String> duplicate) {
        this.joined = joined;
        this.duplicate = duplicate;
    }

    public List<String> getJoined() {
        return joined;
    }

    public List<String> getDuplicate() {
        return duplicate;
    }
}
