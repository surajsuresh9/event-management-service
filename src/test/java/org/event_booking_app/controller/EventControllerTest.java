package org.event_booking_app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.event_booking_app.model.Event;
import org.event_booking_app.model.EventRequest;
import org.event_booking_app.service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;
    private Event event1;
    private Event event2;
    private Event event3;
    private EventRequest eventRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        event1 = new Event(1L, "TechnoGig", "ABC Events", LocalDateTime.now().toString(), LocalDateTime.now().plusDays(3).toString());
        event2 = new Event(2L, "ABC Event", "Eventnetic", LocalDateTime.now().plusDays(6).toString(), LocalDateTime.now().plusDays(9).toString());
        event3 = new Event(3L, "DEF Event", "Formalin Works", LocalDateTime.of(2025, Month.JANUARY, 1, 12, 0).toString(), LocalDateTime.of(2025, Month.FEBRUARY, 1, 12, 0).toString());
        eventRequest = new EventRequest("TechnoGig", "ABC Events", LocalDateTime.now().toString(), LocalDateTime.now().plusDays(3).toString());
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    @Test
    void getAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(event1, event2, event3));
        MvcResult mvcResult = mockMvc.
                perform(get("/api/event/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Event> events = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertEquals(3, events.size());

    }

    @Test
    void getEventById() throws Exception {
        when(eventService.getEventById(any())).thenReturn(event1);
        mockMvc.perform(get("/api/event/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getEventByStartDate() throws Exception {
        when(eventService.getEventByStartDate(any(), any())).thenReturn(Arrays.asList(event1, event2, event3));
        mockMvc.perform(get("/api/event/date?from=2025-01-01&to=2025-10-10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveEvent() throws Exception {
        when(eventService.saveEvent(any())).thenReturn(event2);
        mockMvc.perform(post("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateEvent() throws Exception {
        when(eventService.updateEvent(any())).thenReturn(event3);
        mockMvc.perform(put("/api/event/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteEvent() throws Exception {
        when(eventService.deleteEvent(any())).thenReturn("Event deleted successfully");
        mockMvc.perform(delete("/api/event/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful());
    }
}