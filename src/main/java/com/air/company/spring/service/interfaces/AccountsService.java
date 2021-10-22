package com.air.company.spring.service.interfaces;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.exception.ValidationException;


public interface AccountsService {
    void makeAdmin(String email) throws ValidationException;

    void saveAccount(Account account) throws ValidationException;

    AccountsDto findById(Integer id);

    AccountsDto findByEmail(String email);
}
