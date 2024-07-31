package com.keyin.repository;

import com.keyin.model.Account;
import org.springframework.data.repository.ListCrudRepository;

public interface AccountRepository extends ListCrudRepository<Account, Long> {
}
