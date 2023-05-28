package com.spark.projectmanager.services;


import com.spark.projectmanager.model.Book;
import com.spark.projectmanager.model.Person;
import com.spark.projectmanager.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll(boolean sorted) {
        if (sorted)
            return booksRepository.findAll(Sort.by("edition"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findAll(Integer pageCount, Integer pageSize, boolean sorted) {
        if (sorted) {
            return booksRepository.findAll(PageRequest.of( pageSize,pageCount, Sort.by("edition"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(pageSize,pageCount)).getContent();
        }

    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> findBookOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.map(b -> Optional.ofNullable(b.getOwner())).orElse(null);
    }

    @Transactional
    public void returnBook(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setDateOfTaking(null);
                });
    }

    @Transactional
    public void appointBook(int id, Person holder) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(holder);
                    book.setDateOfTaking(new Date());
                }
        );
        Hibernate.initialize(holder);
    }

    public List<Book> searchBook(String letters){
       return booksRepository.findByNameStartingWith(letters);
    }

}
