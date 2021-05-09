package dev.j4d.twitchchatreader.activechannel.rest;

import dev.j4d.twitchchatreader.activechannel.rest.resource.ActiveChannelResource;
import dev.j4d.twitchchatreader.activechannel.rest.resource.CreateActiveChannelResource;
import dev.j4d.twitchchatreader.activechannel.rest.resource.JoinedStateResource;
import dev.j4d.twitchchatreader.activechannel.rest.resource.assembler.ActiveChannelResourceAssembler;
import dev.j4d.twitchchatreader.activechannel.rest.resource.assembler.JoinedStateResourceAssembler;
import dev.j4d.twitchchatreader.activechannel.service.ActiveChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("active-channel")
public class ActiveChannelController {

    private final ActiveChannelService service;
    private final ActiveChannelResourceAssembler activeChannelResourceAssembler;
    private final JoinedStateResourceAssembler joinedStateResourceAssembler;

    public ActiveChannelController(ActiveChannelService service,
                                   ActiveChannelResourceAssembler activeChannelResourceAssembler,
                                   JoinedStateResourceAssembler joinedStateResourceAssembler) {
        this.service = service;
        this.activeChannelResourceAssembler = activeChannelResourceAssembler;
        this.joinedStateResourceAssembler = joinedStateResourceAssembler;
    }

    @PostMapping
    public ResponseEntity<JoinedStateResource> create(@RequestBody CreateActiveChannelResource resource) {
        final var joinedState = service.joinNewActiveChannels(resource.getActiveChannels());
        return ResponseEntity.ok(joinedStateResourceAssembler.assemble(joinedState));
    }

    @GetMapping
    public ResponseEntity<List<ActiveChannelResource>> getAll() {
        final var activeChannels = service.getAll();
        return ResponseEntity.ok(activeChannelResourceAssembler.assemble(activeChannels));
    }
}
