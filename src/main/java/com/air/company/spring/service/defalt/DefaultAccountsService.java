package com.air.company.spring.service.defalt;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.entity.Role;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.AccountsRepository;
import com.air.company.spring.service.interfaces.AccountsService;
import com.air.company.spring.service.convertors.AccountsConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultAccountsService implements AccountsService, UserDetailsService {
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AccountsRepository accountsRepository;
    private final AccountsConverter accountsConverter;

    @Override
    public void makeAdmin(String email) throws ValidationException {
        Account account = accountsRepository.findByEmail(email);
        validateAccount(account);
        account.addRole(new Role(2L, "ROLE_ADMIN"));
        accountsRepository.save(account);
    }

    @Override
    public AccountsDto saveAccount(Account account) throws ValidationException {
        validateAccount(account);
        Account userFromDB = accountsRepository.findByEmail(account.getEmail());
        if (userFromDB != null) {
            throw new ValidationException("User with this email already exists. ");
        }
        account.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        validateAccount(account);
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return accountsConverter.fromAccountToAccountDto(accountsRepository.save(account));
    }


    private void validateAccount(Account account) throws ValidationException {
        if (isNull(account)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(account.getEmail()) || account.getEmail().isEmpty()) {
            throw new ValidationException("Email is empty");
        }
        if (isNull(account.getPassword()) || account.getPassword().isEmpty()) {
            throw new ValidationException("Password is empty");
        }
        if (isNull(account.getNumber()) || account.getNumber().isEmpty()) {
            throw new ValidationException("Number is empty");
        }
        if (!account.getEmail().matches("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}")) {
            throw new ValidationException("Wrong email.");
        }
        if (!account.getNumber().matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
            throw new ValidationException("Wrong number.");
        }
        if (!account.getPassword().matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}")) {
            throw new ValidationException("Wrong password.");
        }
    }

    @Override
    public AccountsDto findByEmail(String email) {
        Account account = accountsRepository.findByEmail(email);
        if (account != null) {
            return accountsConverter.fromAccountToAccountDto(account);
        }
        return null;
    }

    @Override
    public AccountsDto findById(Integer id) {
        Optional<Account> accounts = accountsRepository.findById(id);
        return accounts.map(accountsConverter::fromAccountToAccountDto).orElse(null);
    }


    public void delete(AccountsDto accountsDto) {
        accountsRepository.delete(accountsConverter.fromAccountDtoToAccount(accountsDto));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountsRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return account;
    }
}
