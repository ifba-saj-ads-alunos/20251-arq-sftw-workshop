package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.domain.models.Room;
import br.ifba.ads.workshop.core.domain.repositories.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {
    private final RoomRepository roomRepository;

    public RoomsController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public ResponseEntity<List<RoomOutput>> list() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomOutput> outs = rooms.stream().map(RoomOutput::fromDomain).toList();
        return ResponseEntity.ok(outs);
    }

    @PostMapping
    public ResponseEntity<RoomOutput> create(@RequestBody CreateRoomRequest req) {
        Room r = new Room(req.name(), req.capacity(), req.location());
        Room saved = roomRepository.save(r);
        return ResponseEntity.created(URI.create("/api/v1/rooms/" + saved.getId())).body(RoomOutput.fromDomain(saved));
    }

    public record CreateRoomRequest(String name, Integer capacity, String location) {}

    public record RoomOutput(UUID id, String name, Integer capacity, String location) {
        static RoomOutput fromDomain(Room r) {
            return new RoomOutput(r.getId(), r.getName(), r.getCapacity(), r.getLocation());
        }
    }
}
