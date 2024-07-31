package com.keyin.controller;

import com.keyin.model.Event;
import com.keyin.service.SearchEngineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class SearchEngineController {
    private final SearchEngineService searchEngineService;

    public SearchEngineController(SearchEngineService searchEngineService) {
        this.searchEngineService = searchEngineService;
    }

    @GetMapping
    public List<Event> getAllProcedures() {
        return this.searchEngineService.fetchEvents();
    }

    @GetMapping(params = "keyword")
    public List<Event> getProcedureByKeyword(@RequestParam(value = "keyword") String keyword) {
        return this.searchEngineService.processQuery(keyword);
    }
}
