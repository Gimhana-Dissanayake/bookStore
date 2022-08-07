package com.giimhana.bookStore.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.giimhana.bookStore.dto.BookDto;
import com.giimhana.bookStore.model.Book;
import com.giimhana.bookStore.repository.BookRespository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRespository bookRespository;

    private final ModelMapper modelMapper;

    public BookService(BookRespository bookRespository, ModelMapper modelMapper) {
        this.bookRespository = bookRespository;
        this.modelMapper = modelMapper;
    }

    public List<BookDto> getBooks() {

        Iterable<Book> all = bookRespository.findAll();

        return StreamSupport.stream(all.spliterator(), false)
                .map(convertBookModelToBookdto())
                .collect(Collectors.toList());

    }

    private Function<Book, BookDto> convertBookModelToBookdto() {
        return book -> modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getBooksByTitle(String bookTitle) {

        List<Book> booksByTitle = bookRespository.findBooksByTitleIgnoreCase(bookTitle);
        return booksByTitle.stream().map(convertBookModelToBookdto()).collect(Collectors.toList());

    }
}
