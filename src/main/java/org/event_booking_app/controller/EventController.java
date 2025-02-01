package org.event_booking_app.controller;

import jakarta.websocket.server.PathParam;
import org.event_booking_app.model.Event;
import org.event_booking_app.model.EventRequest;
import org.event_booking_app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<List<Event>>(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return new ResponseEntity<Event>(eventService.getEventById(id), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Event>> getEventByStartDate(@RequestParam String from, @RequestParam String to) {
        return new ResponseEntity<List<Event>>(eventService.getEventByStartDate(from, to), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Event> saveEvent(@RequestBody EventRequest eventRequest) {
        return new ResponseEntity<Event>(eventService.saveEvent(eventRequest), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        return new ResponseEntity<Event>(eventService.updateEvent(event), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        return new ResponseEntity<String>(eventService.deleteEvent(id), HttpStatus.OK);
    }
}
