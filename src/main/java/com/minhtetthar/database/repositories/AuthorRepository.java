package com.minhtetthar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.minhtetthar.database.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
