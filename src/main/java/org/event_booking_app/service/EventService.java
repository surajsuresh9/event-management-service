package org.event_booking_app.service;

import org.event_booking_app.model.Event;
import org.event_booking_app.model.EventRequest;
import org.event_booking_app.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventService {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event saveEvent(EventRequest eventRequest) {
        return eventRepo.save(getEvent(eventRequest));
    }

    private Event getEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setEventOrganizer(eventRequest.getEventOrganizer());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        return event;
    }


    public Event getEventById(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event updateEvent(Event event) {
        Event eventInDB = eventRepo.findById(event.getId()).orElseThrow(() -> new RuntimeException("Event not Found"));
        eventInDB.setStartDate(event.getStartDate());
        eventInDB.setEndDate(event.getEndDate());
        eventInDB.setEventOrganizer(event.getEventOrganizer());
        eventInDB.setEventName(event.getEventName());
        return eventRepo.save(eventInDB);
    }

    public String deleteEvent(Long id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepo.delete(event);
        return "Event deleted successfully";
    }

    public List<Event> getEventByStartDate(String from, String to) {
//        LocalDateTime fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
//        LocalDateTime toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        return eventRepo.findByStartDateBetween(from, to);
    }
}
