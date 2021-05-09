package dev.j4d.twitchchatreader.activechannel.rest.resource.assembler;

import dev.j4d.twitchchatreader.activechannel.domain.JoinedState;
import dev.j4d.twitchchatreader.activechannel.rest.resource.JoinedStateResource;
import org.springframework.stereotype.Component;

@Component
public class JoinedStateResourceAssembler {

    public JoinedStateResource assemble(JoinedState joinedState) {
        return new JoinedStateResource(joinedState.getJoined(), joinedState.getDuplicate());
    }
}
