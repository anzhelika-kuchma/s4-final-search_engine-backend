package com.keyin.model;

import jakarta.persistence.*;

@Entity(name = "event")
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "name",
            unique = true,
            nullable = false,
            columnDefinition = "text"
    )
    private String name;

    public Event() {}

    public Event(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
