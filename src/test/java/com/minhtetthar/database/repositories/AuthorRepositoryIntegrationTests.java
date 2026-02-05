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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository authorRepository) {
        this.underTest = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertTrue(result.isPresent(), "Author should be found in database");
        assertEquals(author, result.get(), "Retrieved author should match created author");
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createAuthorTestB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createAuthorTestC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();
        assertEquals(3, ((Collection<?>) result).size(), "There should be three authors in the database");
        assertTrue(((Collection<?>) result).contains(authorA), "Author A should be in the database");
        assertTrue(((Collection<?>) result).contains(authorB), "Author B should be in the database");
        assertTrue(((Collection<?>) result).contains(authorC), "Author C should be in the database");
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.save(authorA);

        authorA.setName("UPDATED NAME");
        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());
        assertTrue(result.isPresent(), "Author should be found in database after update");
        assertEquals(authorA, result.get(), "Retrieved author should match updated author");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.save(authorA);

        underTest.deleteById(authorA.getId());

        Optional<Author> result = underTest.findById(authorA.getId());
        assertTrue(result.isEmpty(), "Author should not be found in database afterdeletion");
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createAuthorTestB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createAuthorTestC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.ageLessThan(78);
        assertTrue(((Collection<?>) result).contains(authorA), "Author B should be in the result");
        assertTrue(((Collection<?>) result).contains(authorC), "Author C should be in the result");
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createAuthorTestB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createAuthorTestC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(78);
        assertTrue(((Collection<?>) result).contains(authorA), "Author A should be in the result");
    }

}
