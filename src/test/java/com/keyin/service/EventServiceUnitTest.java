package com.keyin.service;

import com.keyin.model.Event;
import com.keyin.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceUnitTest {

    EventService eventServiceUnderTest;

    @Mock
    EventRepository mockEventRepository;

    @BeforeEach
    void setup() {
        this.eventServiceUnderTest = new EventService(this.mockEventRepository);
    }

    @Test
    @DisplayName("Should return all events")
    void findAllEvents() {
        List<Event> eventListExpected = new ArrayList<>();

        eventListExpected.add(new Event("first event"));
        eventListExpected.add(new Event("second event"));

        when(this.mockEventRepository.findAll()).thenReturn(eventListExpected);

        List<Event> eventListActual = this.eventServiceUnderTest.findAllEvents();

        assertAll(
                () -> assertEquals(2, eventListActual.size()),
                () -> assertTrue(eventListActual.stream().allMatch(event -> event.getName().contains("event")))
        );
    }

    @Test
    @DisplayName("Should return all events containing keyword")
    void findEventByKeyword() {
        List<Event> eventListExpected = new ArrayList<>();

        eventListExpected.add(new Event("second event with test keyword"));

        when(this.mockEventRepository.findByNameContainingIgnoreCase("test")).thenReturn(eventListExpected);

        List<Event> eventListActual = this.eventServiceUnderTest.findEventByKeyword("test");

        assertAll(
                () -> assertEquals(1, eventListActual.size()),
                () -> assertEquals(eventListExpected.getFirst(), eventListActual.getFirst())
        );
    }
}
