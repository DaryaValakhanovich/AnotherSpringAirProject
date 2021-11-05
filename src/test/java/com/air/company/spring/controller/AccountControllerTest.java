package com.air.company.spring.controller;

import com.air.company.spring.prototypes.AccountPrototype;
import com.air.company.spring.repository.AccountsRepository;
import com.air.company.spring.service.defalt.DefaultAccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class AccountControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    DefaultAccountsService accountsService;
    AccountsRepository accountsRepository;

    @BeforeEach
    void setUp() {
        accountsService = mock(DefaultAccountsService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void saveUsers() throws Exception {
        accountsService = mock(DefaultAccountsService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
        objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/createUser")
                        .flashAttr("userForm", AccountPrototype.testAccount())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(AccountPrototype.testAccountDto())))
                .andExpect(status().isOk());
    }

    @Test
    public void makeAdmin() throws Exception {
        accountsService = mock(DefaultAccountsService.class);
        accountsRepository = mock(AccountsRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
        objectMapper = new ObjectMapper();
        when(accountsRepository.findByEmail(any())).thenReturn(AccountPrototype.testAccount());
        mockMvc.perform(post("/accounts/makeAdmin")
                        .flashAttr("userForEmail", AccountPrototype.testAccount())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(AccountPrototype.testAccountDto())))
                .andExpect(status().isOk());
    }
}
