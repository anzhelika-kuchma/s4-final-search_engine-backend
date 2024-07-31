package com.keyin.service;

import com.keyin.model.Event;
import com.keyin.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents() {
        return this.eventRepository.findAll();
    }

    public List<Event> findEventByKeyword(String keyword) {
        return this.eventRepository.findByNameContainingIgnoreCase(keyword);
    }
}
