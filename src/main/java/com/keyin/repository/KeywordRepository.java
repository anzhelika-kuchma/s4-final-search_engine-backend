package com.keyin.repository;

import com.keyin.model.Keyword;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface KeywordRepository extends ListCrudRepository<Keyword, Long> {
    Optional<Keyword> findDistinctByName(String name);
}
