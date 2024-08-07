package com.keyin.repository;

import com.keyin.model.Event;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface EventRepository extends ListCrudRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCase(String keyword);
}
