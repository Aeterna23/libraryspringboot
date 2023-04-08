package com.spark.libraryspringboot.repositories;


import com.spark.libraryspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {


    List<Book> findByNameStartingWith(String letters);

    }

