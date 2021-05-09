package dev.j4d.twitchchatreader.activechannel.rest;

import dev.j4d.twitchchatreader.activechannel.rest.resource.ActiveChannelResource;
import dev.j4d.twitchchatreader.activechannel.rest.resource.CreateActiveChannelResource;
import dev.j4d.twitchchatreader.activechannel.rest.resource.assembler.ActiveChannelResourceAssembler;
import dev.j4d.twitchchatreader.activechannel.service.ActiveChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("active-channel")
public class ActiveChannelController {

    private final ActiveChannelService service;
    private final ActiveChannelResourceAssembler resourceAssembler;

    public ActiveChannelController(ActiveChannelService service, ActiveChannelResourceAssembler resourceAssembler) {
        this.service = service;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateActiveChannelResource resource) {
        service.joinNewActiveChannels(resource.getActiveChannels());
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<ActiveChannelResource>> getAll() {
        final var activeChannels = service.getAll();
        return ResponseEntity.ok(resourceAssembler.assemble(activeChannels));
    }
}
