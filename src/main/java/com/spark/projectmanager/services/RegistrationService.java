package com.spark.projectmanager.services;

import com.spark.projectmanager.model.Person;
import com.spark.projectmanager.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void registerPerson(Person person) {
        peopleRepository.save(person);
    }
}
