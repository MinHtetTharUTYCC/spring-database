package com.minhtetthar.database.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.minhtetthar.database.TestDataUtil;
import com.minhtetthar.database.domain.Author;
import com.minhtetthar.database.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        authorRepository.save(author);
        Book book = TestDataUtil.createBookTestA(author);
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertTrue(result.isPresent(), "Book should be found in database");
        assertEquals(book, result.get(), "Retrieved book should match created book");
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createBookTestA(author);
        bookRepository.save(bookA);

        Book bookB = TestDataUtil.createBookTestB(author);
        bookRepository.save(bookB);

        Book bookC = TestDataUtil.createBookTestC(author);
        bookRepository.save(bookC);

        Iterable<Book> result = bookRepository.findAll();
        assertEquals(3, ((Collection<?>) result).size(), "There should be three books in the database");
        assertTrue(((Collection<?>) result).contains(bookA), "Book A should be in the database");
        assertTrue(((Collection<?>) result).contains(bookB), "Book B should be in the database");
        assertTrue(((Collection<?>) result).contains(bookC), "Book C should be in the database");
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createAuthorTestA();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createBookTestA(author);
        bookRepository.save(bookA);

        bookA.setTitle(("UPDATED"));
        bookRepository.save(bookA);

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assertTrue(result.isPresent(), "Updated book should be found in database");
        assertEquals(bookA, result.get(), "Retrieved book should match updated book");
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createAuthorTestA();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createBookTestA(author);
        bookRepository.save(bookA);

        bookRepository.deleteById(bookA.getIsbn());

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assertTrue(result.isEmpty(), "Deleted book should not be found in database");
    }
}
