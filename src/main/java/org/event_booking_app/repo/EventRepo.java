package org.event_booking_app.repo;


import org.event_booking_app.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findByStartDateBetween(String from, String to);
}
