package com.keyin.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "keyword")
@Table(name = "keyword")
public class Keyword {
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

    @OneToMany(mappedBy = "keyword")
    private List<AccountKeyword> accountKeywordList;

    public Keyword() {}

    public Keyword(String name) {
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
