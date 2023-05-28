package com.spark.projectmanager.util;


import com.spark.projectmanager.model.Person;
import com.spark.projectmanager.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findByUsername(person.getUsername()).isPresent())
            errors.rejectValue("username", "", "Already exists person with same username.");
        if (person.getBirthDate() == null) {
            errors.rejectValue("birthDate", "", "Birthday cannot be blank. Please check.");
        }
    }
}
