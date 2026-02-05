package com.minhtetthar.database.dao;

import java.util.List;
import java.util.Optional;

import com.minhtetthar.database.domain.Author;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(Long id);

    List<Author> findMany();

    void update(long id, Author author);

    void delete(long l);

}