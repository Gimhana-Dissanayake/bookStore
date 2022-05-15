package com.giimhana.bookStore.controller;

import java.util.List;

import com.giimhana.bookStore.dto.BookDto;
import com.giimhana.bookStore.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {

        List<BookDto> books = bookService.getBooks();

        return ResponseEntity.ok(books);

    }
}
