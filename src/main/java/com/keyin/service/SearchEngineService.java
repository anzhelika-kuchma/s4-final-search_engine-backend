package com.keyin.service;

import com.keyin.model.Keyword;
import com.keyin.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchEngineService {
    private final KeywordService keywordService;
    private final AccountKeywordService accountKeywordService;
    private final EventService eventService;
    private final AccountService accountService;

    @Autowired
    public SearchEngineService(
            KeywordService keywordService,
            AccountKeywordService accountKeywordService,
            EventService eventService,
            AccountService accountService
    ) {
        this.keywordService = keywordService;
        this.accountKeywordService = accountKeywordService;
        this.eventService = eventService;
        this.accountService = accountService;
    }

    public List<Event> fetchEvents() {
        return this.eventService.findAllEvents();
    }

    public List<Event> processQuery(String query) {
        Keyword keyword = this.keywordService.createKeyword(query);

        this.accountKeywordService.createAccountKeywordAssociation(this.accountService.findAccountById(1L).get(), keyword);

        return this.eventService.findEventByKeyword(keyword.getName());
    }

}
