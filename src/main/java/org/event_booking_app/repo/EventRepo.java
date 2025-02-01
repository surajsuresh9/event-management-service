package org.event_booking_app.repo;


import org.event_booking_app.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findByStartDateBetween(LocalDateTime from, LocalDateTime to);
}
