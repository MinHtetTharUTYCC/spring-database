package com.minhtetthar.database.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.minhtetthar.database.TestDataUtil;
import com.minhtetthar.database.domain.Author;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTest {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTest(AuthorDaoImpl authorDaoImpl) {
        this.underTest = authorDaoImpl;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createAuthorTestA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertTrue(result.isPresent(), "Author should be found in database");
        assertEquals(author, result.get(), "Retrieved author should match created author");
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.create(authorA);
        Author authorB = TestDataUtil.createAuthorTestB();
        underTest.create(authorB);
        Author authorC = TestDataUtil.createAuthorTestC();
        underTest.create(authorC);

        List<Author> result = underTest.findMany();
        assertEquals(3, result.size(), "There should be three authors in the database");
        assertTrue(result.contains(authorA), "Author A should be in the database");
        assertTrue(result.contains(authorB), "Author B should be in the database");
        assertTrue(result.contains(authorC), "Author C should be in the database");
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.create(authorA);

        authorA.setName("Updated Name");
        underTest.update(authorA.getId(), authorA);

        Optional<Author> result = underTest.findOne(authorA.getId());
        assertTrue(result.isPresent(), "Author should be found in database after update");
        assertEquals(authorA, result.get(), "Retrieved author should match updated author");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author authorA = TestDataUtil.createAuthorTestA();
        underTest.create(authorA);

        underTest.delete(authorA.getId());

        Optional<Author> result = underTest.findOne(authorA.getId());
        assertTrue(result.isEmpty(), "Author should not be found in database after deletion");
    }

}
