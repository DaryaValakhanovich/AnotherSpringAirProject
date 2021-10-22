package com.air.company.spring.service.convertors;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountsConverter {
    public Account fromAccountDtoToAccount(AccountsDto accountsDto) {
        Account account = new Account();
        account.setId(accountsDto.getId());
        account.setEmail(accountsDto.getEmail());
        account.setNumber(accountsDto.getNumber());
        account.setPassword(accountsDto.getPassword());
        account.setRoles(accountsDto.getRoles());
        return account;
    }

    public AccountsDto fromAccountToAccountDto(Account account) {
        return AccountsDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .number(account.getNumber())
                .password(account.getPassword())
                .roles(account.getRoles())
                .build();
    }
}
