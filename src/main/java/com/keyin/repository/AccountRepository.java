package com.keyin.repository;

import com.keyin.model.Account;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface AccountRepository extends ListCrudRepository<Account, Long> {
    Optional<Account> findByName(String name);
}

