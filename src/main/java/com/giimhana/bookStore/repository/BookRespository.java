package com.giimhana.bookStore.repository;

import java.util.UUID;

import com.giimhana.bookStore.model.Book;

import org.springframework.data.repository.CrudRepository;

public interface BookRespository extends CrudRepository<Book, UUID> {

}
