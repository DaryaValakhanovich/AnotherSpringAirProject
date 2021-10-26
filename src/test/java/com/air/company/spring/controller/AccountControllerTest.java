package com.air.company.spring.controller;

import com.air.company.spring.dto.AccountsDto;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
     //   when(accountsService.saveAccount(any())).
        //!userForm.getPassword().equals(userForm.getPasswordConfirm())
     //   when(!any().equals(any())).thenReturn(false);
        accountsService = mock(DefaultAccountsService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
        objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/createUser")
                      //  .param("userForm", AccountPrototype.testAccount().toString())
                        .flashAttr("userForm", AccountPrototype.testAccount())
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content()
                        .content(objectMapper.writeValueAsString(AccountPrototype.testAccountDto())))
                .andExpect(status().isOk());
               // .andExpect(content().contentType(M))
              //  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             //   .andExpect(content().json(objectMapper.writeValueAsString(AccountPrototype.testAccountDto())));
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

/*
    @Test
    void findAllUsers() throws Exception {
        when(accountsService.findAll()).thenReturn(Collections.singletonList(UsersPrototype.aUserDTO()));
        mockMvc.perform(get("/users/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(UsersPrototype.aUserDTO()))))
                .andExpect(status().isOk());
    }

    @Test
    void findByLogin() {
    }

    @Test
    void deleteUsers() {
    }
    */

}
