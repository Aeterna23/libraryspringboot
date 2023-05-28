package com.spark.projectmanager.controller;

import com.spark.projectmanager.model.Person;
import com.spark.projectmanager.services.RegistrationService;
import com.spark.projectmanager.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService service;
    private  final PersonValidator validator;
@Autowired
    public AuthController(RegistrationService service, PersonValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
    return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerPerson(@ModelAttribute("person") @Valid Person person, BindingResult result){
    validator.validate(person,result);
    if(result.hasErrors()){
        return "/auth/registration";
    }
    service.registerPerson(person);
return "redirect:/auth/login";
    }
}
