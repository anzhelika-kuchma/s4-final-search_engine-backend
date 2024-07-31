package com.keyin.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "account")
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "name",
            unique = true,
            nullable = false
    )
    private String name;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @OneToMany(mappedBy = "account")
    private List<AccountKeyword> accountKeywordList;

    public Account() {}

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
