package com.air.company.spring.service;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Role;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.prototypes.AccountPrototype;
import com.air.company.spring.service.defalt.DefaultAccountsService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DefaultAccountsService accountsService;


    @Test
    public void saveAccountTest() throws ValidationException {
        accountsService.saveAccount(AccountPrototype.testAccount());
        AccountsDto foundAccount = accountsService.findByEmail(AccountPrototype.testAccount().getEmail());
        Assertions.assertNotNull(foundAccount);
        Assertions.assertEquals(foundAccount.getNumber(),AccountPrototype.testAccount().getNumber());
        Assertions.assertEquals(foundAccount.getEmail(),AccountPrototype.testAccount().getEmail());
        Assertions.assertTrue(bCryptPasswordEncoder
                .matches(AccountPrototype.testAccount().getPassword(), foundAccount.getPassword()));
        Assertions.assertTrue(foundAccount.getRoles().contains(new Role(1L, "ROLE_USER")));
        Assertions.assertFalse(foundAccount.getRoles().contains(new Role(2L, "ROLE_ADMIN")));
        accountsService.delete(foundAccount);
    }

    @Test
    public void makeAccountAdminTest() throws ValidationException {
        accountsService.saveAccount(AccountPrototype.testAccount());
        accountsService.makeAdmin(AccountPrototype.testAccount().getEmail());
        AccountsDto foundAccount = accountsService.findByEmail(AccountPrototype.testAccount().getEmail());
        Assertions.assertNotNull(foundAccount);
        Assertions.assertTrue(foundAccount.getRoles().contains(new Role(1L, "ROLE_USER")));
        Assertions.assertTrue(foundAccount.getRoles().contains(new Role(2L, "ROLE_ADMIN")));
       accountsService.delete(foundAccount);
    }

    @Test
    public void loadUserByUsernameTest() throws ValidationException {
        accountsService.saveAccount(AccountPrototype.testAccount());
        UserDetails foundAccount = accountsService.loadUserByUsername(AccountPrototype.testAccount().getEmail());
        Assertions.assertNotNull(foundAccount);
        Assertions.assertEquals(foundAccount.getUsername(),AccountPrototype.testAccount().getEmail());
        Assertions.assertTrue(bCryptPasswordEncoder.matches(AccountPrototype.testAccount().getPassword(), foundAccount.getPassword()));
        accountsService.delete(accountsService.findByEmail(AccountPrototype.testAccount().getEmail()));
    }
}
