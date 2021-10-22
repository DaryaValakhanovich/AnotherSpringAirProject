package com.air.company.spring.repository;

import com.air.company.spring.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
}
