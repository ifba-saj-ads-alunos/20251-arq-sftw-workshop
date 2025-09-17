package br.ifba.ads.workshop.web.config;

import br.ifba.ads.workshop.core.domain.models.Room;
import br.ifba.ads.workshop.core.domain.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class RoomDataSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(RoomDataSeeder.class);
    private final RoomRepository roomRepository;

    public RoomDataSeeder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            var existing = roomRepository.findAll();
            if (existing == null || existing.isEmpty()) {
                log.info("Seeding default rooms...");
                roomRepository.save(new Room("Sala 101", 40, "Bloco A"));
                roomRepository.save(new Room("Sala 102", 30, "Bloco A"));
                roomRepository.save(new Room("Auditório", 200, "Bloco B"));
            } else {
                log.info("Rooms already present: {}", existing.size());
            }
        } catch (Exception ex) {
            log.warn("Failed to seed rooms: {}", ex.getMessage());
        }
    }
}
