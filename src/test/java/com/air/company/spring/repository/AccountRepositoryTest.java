package com.air.company.spring.repository;

import com.air.company.spring.entity.Account;
import com.air.company.spring.prototypes.AccountPrototype;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

   @Autowired
    private AccountsRepository accountsRepository;


    @Test
    public void saveAccountTest() {
        accountsRepository.save(AccountPrototype.testAccount());
        Account foundAccount = accountsRepository.findByEmail(AccountPrototype.testAccount().getEmail());
        Assertions.assertNotNull(foundAccount);
        Assertions.assertEquals(foundAccount.getNumber(),AccountPrototype.testAccount().getNumber());
        Assertions.assertEquals(foundAccount.getEmail(),AccountPrototype.testAccount().getEmail());
        Assertions.assertEquals(foundAccount.getPassword(),AccountPrototype.testAccount().getPassword());
        accountsRepository.delete(foundAccount);
    }

}
