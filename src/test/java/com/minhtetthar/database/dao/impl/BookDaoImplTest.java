package com.minhtetthar.database.dao.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.minhtetthar.database.TestDataUtil;
import com.minhtetthar.database.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = Book.builder()
                .isbn("1232-2434-2353-2353")
                .title("Mockito for Dummies")
                .authorId(2L)
                .build();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("1232-2434-2353-2353"),
                eq("Mockito for Dummies"),
                eq(2L));
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne("1232-2434-2353-2353");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id from books WHERE isbn = ? LIMIT 1"),
                any(BookDaoImpl.BookRowMapper.class),
                eq("1232-2434-2353-2353"));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        underTest.findMany();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id from books"),
                any(BookDaoImpl.BookRowMapper.class));
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createBookTestA();
        underTest.update("1232-2434-2353-2353", book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq("1234-5678-9012-3456-1"),
                eq("Mockito for Dummies"),
                eq(999L),
                eq("1232-2434-2353-2353"));
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete("1232-2434-2353-2353");

        verify(jdbcTemplate).update(
                eq("DELETE from books WHERE isbn = ?"),
                eq("1232-2434-2353-2353"));
    }

}
