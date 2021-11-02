package com.air.company.spring.controller;

import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.defalt.DefaultAccountsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           // model.addAttribute("errorString", ex.getMessage());
        }

    }

    @PostMapping("/createUser")
    public ResponseEntity<AccountsDto> saveUser(@RequestBody Account userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
          //  model.addAttribute("errorString", "Пароли не совпадают");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok(accountsService.saveAccount(userForm));
        } catch (ValidationException ex){
         //   model.addAttribute("errorString", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
