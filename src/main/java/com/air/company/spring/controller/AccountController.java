package com.air.company.spring.controller;

import com.air.company.spring.entity.Account;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.defalt.DefaultAccountsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/accounts")
@Controller
@Log
public class AccountController {
    @Autowired
    private DefaultAccountsService accountsService;

    @GetMapping("/makeAdmin")
    public String makeAdmin(Model model) {
        model.addAttribute("userForEmail", new Account());
        return "makeAdmin";
    }

    @PostMapping("/makeAdmin")
    public String saveAdmin(@ModelAttribute("userForEmail") @Valid Account account, Model model) {
        try {
            accountsService.makeAdmin(account.getEmail());
        } catch (Exception ex){
            model.addAttribute("errorString", ex.getMessage());
            return "makeAdmin";
        }
        return "redirect:/";
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        model.addAttribute("userForm", new Account());
        return "createUserView";
    }

    @PostMapping("/createUser")
    public String saveUser(@ModelAttribute("userForm") @Valid Account userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "createUserView";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("errorString", "Пароли не совпадают");
            return "createUserView";
        }
        try {
            accountsService.saveAccount(userForm);
        } catch (ValidationException ex){
            model.addAttribute("errorString", ex.getMessage());
            return "createUserView";
        }
        return "redirect:/";
    }
}
