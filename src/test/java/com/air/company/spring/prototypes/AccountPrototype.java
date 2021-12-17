package com.air.company.spring.prototypes;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;

public class AccountPrototype {
    public static Account testAccount() {
        Account a = new Account();
        a.setEmail("test@email.ru");
        a.setNumber("1111111111");
        a.setPassword("testPassword1");
        return a;
    }

    public static AccountsDto testAccountDto() {
        return AccountsDto.builder()
                .id(1)
                .email("test@email.ru")
                .number("1111111111")
                .password("testPassword1")
                .passwordConfirm("testPassword1")
                .build();
    }
}
