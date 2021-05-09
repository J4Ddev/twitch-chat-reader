package dev.j4d.twitchchatreader.activechannel.rest.resource.assembler;

import dev.j4d.twitchchatreader.activechannel.domain.ActiveChannel;
import dev.j4d.twitchchatreader.activechannel.rest.resource.ActiveChannelResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActiveChannelResourceAssembler {

    public List<ActiveChannelResource> assemble(List<ActiveChannel> activeChannels) {
        return activeChannels.stream().map(this::assemble).collect(Collectors.toUnmodifiableList());
    }

    private ActiveChannelResource assemble(ActiveChannel activeChannel) {
        return new ActiveChannelResource(activeChannel.getId(), activeChannel.getChannel(), activeChannel.getTimeOfJoin().toString());
    }
}
