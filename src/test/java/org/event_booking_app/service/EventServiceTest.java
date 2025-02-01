package org.event_booking_app.service;

import org.event_booking_app.model.Event;
import org.event_booking_app.model.EventRequest;
import org.event_booking_app.repo.EventRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

// service layer test
// mock the repo (no db layer, being mocked)

class EventServiceTest {

    private EventService eventService;
    private Event event;
    private EventRequest eventRequest;
    private EventRepo eventRepo;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        eventRepo = mock(EventRepo.class);
        eventService = new EventService(eventRepo);
        eventRequest = new EventRequest("TechnoGig", "ABC Events", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        event = new Event(1L, "TechnoGig", "ABC Events", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllEvents() {
        when(eventRepo.findAll()).thenReturn(Arrays.asList(event));
        List<Event> savedEvents = eventService.getAllEvents();
        Assertions.assertNotNull(savedEvents);
        Assertions.assertEquals(1, savedEvents.size());
    }

    @Test
    void saveEvent() {
        when(eventRepo.save(ArgumentMatchers.any())).thenReturn(event);
        Event savedEvent = eventService.saveEvent(eventRequest);
        Assertions.assertEquals("TechnoGig", savedEvent.getEventName());
        Assertions.assertEquals(1, savedEvent.getId());
    }

    @Test
    void getEventById() {
        when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
        Event savedEvent = eventService.getEventById(1L);
        Assertions.assertEquals("TechnoGig", savedEvent.getEventName());
        Assertions.assertEquals(1, savedEvent.getId());
    }

    @Test
    void updateEvent() {
        Event updatedEvent = new Event(1L, "ABC Events", "ABC Events", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        when(eventRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));
        when(eventRepo.save(ArgumentMatchers.any())).thenReturn(updatedEvent);
        Event updatedEventInDB = eventService.updateEvent(event);
        Assertions.assertEquals("ABC Events", updatedEventInDB.getEventName());
        Assertions.assertEquals(1, updatedEventInDB.getId());
    }

    @Test
    void deleteEvent() {
        when(eventRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));
        doAnswer(Answers.CALLS_REAL_METHODS).when(eventRepo).deleteById(ArgumentMatchers.any());
        String msg = eventService.deleteEvent(1L);
        Assertions.assertEquals("Event deleted successfully", msg);
        Assertions.assertNotNull(msg);
    }

    @Test
    void getEventByStartDate() {
        when(eventRepo.findByStartDateBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Arrays.asList(event));
        List<Event> events = eventService.getEventByStartDate("2025-01-01", "2025-02-01");
        Assertions.assertNotNull(events);
        Assertions.assertEquals(1, events.size());
    }
}