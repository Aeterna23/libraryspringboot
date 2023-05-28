package com.spark.projectmanager.services;


import com.spark.projectmanager.model.Book;
import com.spark.projectmanager.model.Person;
import com.spark.projectmanager.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }
    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }





    public List<Book> findBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> booksList = person.get().getBooks();
            for (Book book : booksList) {

                Instant instant =book.getDateOfTaking().toInstant();
                LocalDateTime bookDateEdition = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                if (bookDateEdition.plusDays(10).isBefore(LocalDateTime.now())) {
                    book.setExpired(true);
                }
            }
            return person.get().getBooks();
        }
        return Collections.emptyList();

    }
}
