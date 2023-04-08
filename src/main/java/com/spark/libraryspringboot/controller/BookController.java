package com.spark.libraryspringboot.controller;

import com.spark.libraryspringboot.model.Book;
import com.spark.libraryspringboot.model.Person;
import com.spark.libraryspringboot.services.BookService;
import com.spark.libraryspringboot.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {


    private final BookService bookService;

    private final PeopleService peopleService;


    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {

        this.bookService = bookService;

        this.peopleService = peopleService;
    }

    @GetMapping
    public String findAll(Model model, @RequestParam(value = "pagecount", required = false) Integer pageCount,
                          @RequestParam(value = "pagesize", required = false) Integer pageSize,
                          @RequestParam(value = "sorted",required = false) boolean sorted) {
        if (pageSize == null || pageCount == null) {
            model.addAttribute("books", bookService.findAll(sorted));
        } else  {
model.addAttribute("books", bookService.findAll(pageSize, pageCount, sorted));
        }

        return "books/getall";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findById(id));
        Optional<Person> bookOwner = bookService.findBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("bookOwner", bookOwner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/getbook";
    }

    @GetMapping("/newbook")
    public String getCreateNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/newbook";
    }

    @PostMapping()
    public String newBook(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/newbook";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/updatebook")
    public String getUpdateBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/getupdatebook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            return "books/getupdatebook";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") Integer id) {
        bookService.returnBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/appoint")
    public String appointBook(@PathVariable("id") Integer id, @ModelAttribute("person") Person holder) {
        bookService.appointBook(id, holder);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String getSearchBookByTitle(){
        return "books/search";
    }
    @PostMapping("/search")
    public String searchBookByTitle(Model model, @RequestParam("letters") String letters){
        model.addAttribute("books",bookService.searchBook(letters));
        return "books/search";
    }
}