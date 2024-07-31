package com.keyin.service;

import com.keyin.model.Keyword;
import com.keyin.repository.KeywordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Optional<Keyword> findKeywordByName(String name) {
        return this.keywordRepository.findDistinctByName(name);
    }

    public Keyword createKeyword(String keyword) {
        String name = keyword.toLowerCase();

        Optional<Keyword> keywordOptional = this.findKeywordByName(name);

        return keywordOptional.orElseGet(() -> this.keywordRepository.save(new Keyword(name)));
    }
}
