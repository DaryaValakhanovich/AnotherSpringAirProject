package com.air.company.spring.controller;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.defalt.DefaultAccountsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RequestMapping("/accounts")
@RestController
@Log
public class AccountController {
    @Autowired
    private DefaultAccountsService accountsService;

    @PostMapping("/makeAdmin")
    public ResponseEntity<?> saveAdmin(@RequestBody Account account) {
        try {
            accountsService.makeAdmin(account.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<Resource<AccountsDto>> saveUser(@RequestBody Account userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            //  model.addAttribute("errorString", "Пароли не совпадают");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            AccountsDto accountsDto = accountsService.saveAccount(userForm);
            return new ResponseEntity<>(new Resource(accountsDto,
                    linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(AccountController.class, "findAccountById", Integer.class)),
                            accountsDto.getId()).withSelfRel()), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAccountById/{id}")
    public ResponseEntity<Resource<AccountsDto>> findAccountById(@PathVariable Integer id) {
        log.info("Handling find account request");
        AccountsDto accountsDto = accountsService.findById(id);
        return new ResponseEntity<>(new Resource(accountsDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(AccountController.class, "findAccountById", Integer.class)),
                        id).withSelfRel()), HttpStatus.OK);
    }
}
