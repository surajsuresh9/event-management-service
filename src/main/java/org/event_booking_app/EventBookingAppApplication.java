package org.event_booking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class EventBookingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventBookingAppApplication.class, args);
    }
}
