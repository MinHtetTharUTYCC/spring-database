package com.minhtetthar.database.dao;

import java.util.List;
import java.util.Optional;

import com.minhtetthar.database.domain.Book;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> findMany();

    void update(String string, Book book);

    void delete(String string);
}
