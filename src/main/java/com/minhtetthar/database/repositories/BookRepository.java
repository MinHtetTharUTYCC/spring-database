package com.minhtetthar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.minhtetthar.database.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

}
