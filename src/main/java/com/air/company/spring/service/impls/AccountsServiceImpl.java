package com.air.company.spring.service.impls;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.entity.Role;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.AccountsRepository;
import com.air.company.spring.service.AccountsService;
import com.air.company.spring.dto.mappers.AccountsMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService, UserDetailsService {

    private final AccountsRepository accountsRepository;
    private final AccountsMapper accountsConverter;


    @Override
    public void makeAdmin(String email){
        Account account = accountsRepository.findByEmail(email);
        account.addRole(new Role(2L, "ROLE_ADMIN"));
        accountsRepository.save(account);
    }

    @Override
    public AccountsDto saveAccount(AccountsDto account) throws ValidationException {
        Account userFromDB = accountsRepository.findByEmail(account.getEmail());
        if (userFromDB != null) {
            throw new ValidationException("User with this email already exists. ");
        }
        account.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        return accountsConverter.fromAccountToAccountDto(accountsRepository.save(accountsConverter.fromAccountDtoToAccount(account)));
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
