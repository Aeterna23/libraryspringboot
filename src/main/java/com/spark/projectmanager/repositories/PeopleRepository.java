package com.spark.projectmanager.repositories;


import com.spark.projectmanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFullName(String fullName);
    Optional<Person> findByUsername(String username);

}
