package com.keyin.controller;

import com.keyin.model.Event;
import com.keyin.service.EventService;
import com.keyin.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final SearchEngineService searchEngineService;

    @Autowired
    public EventController(EventService eventService, SearchEngineService searchEngineService) {
        this.eventService = eventService;
        this.searchEngineService = searchEngineService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return this.eventService.findAllEvents();
    }

    @GetMapping(params = "keyword")
    public List<Event> getEventsByKeyword(@RequestParam(value = "keyword") String keyword) {
        return this.searchEngineService.processQuery(keyword);
    }
}
