package org.event_booking_app.repo;

import org.event_booking_app.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepoTest {

    @Autowired
    EventRepo eventRepo;
    Event event1;
    Event event2;

    @BeforeEach
    public void setUp() {
        event1 = new Event(null, "Bryan Adams Concert", "ABC Events", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        event2 = new Event(null, "Linkin Park Concert", "DEF Events", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5));
        eventRepo.save(event1);
        eventRepo.save(event2);
    }

    @AfterEach
    public void tearDown() {
        eventRepo.deleteAll();
    }

    @Test
    void findByStartDateBetween() {
        List<Event> events = eventRepo.findByStartDateBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Assertions.assertEquals(2, events.size());
        Assertions.assertNotNull(events);
    }
}