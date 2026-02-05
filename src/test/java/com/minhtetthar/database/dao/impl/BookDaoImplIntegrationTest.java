package com.minhtetthar.database.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.minhtetthar.database.TestDataUtil;
import com.minhtetthar.database.dao.AuthorDao;
import com.minhtetthar.database.domain.Author;
import com.minhtetthar.database.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTest {

    private AuthorDao authorDao;
    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl bookDaoImpl, AuthorDao authorDao) {
        this.underTest = bookDaoImpl;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        authorDao.create(author);
        Book book = TestDataUtil.createBookTestA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertTrue(result.isPresent(), "Book should be found in database");
        assertEquals(book, result.get(), "Retrieved book should match created book");
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        authorDao.create(author);

        Book bookA = TestDataUtil.createBookTestA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);
        Book bookB = TestDataUtil.createBookTestB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);
        Book bookC = TestDataUtil.createBookTestC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        var result = underTest.findMany();
        assertEquals(3, result.size(), "There should be three books in the database");
        assertTrue(result.contains(bookA), "Book A should be in the database");
        assertTrue(result.contains(bookB), "Book B should be in the database");
        assertTrue(result.contains(bookC), "Book C should be in the database");
    }

}
