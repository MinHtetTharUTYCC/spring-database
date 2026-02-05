package com.minhtetthar.database;

import com.minhtetthar.database.domain.Author;
import com.minhtetthar.database.domain.Book;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author createAuthorTestA() {
        return Author.builder()
                .id(111L)
                .name("Minzo")
                .age(80)
                .build();
    }

    public static Author createAuthorTestB() {
        return Author.builder()
                .id(222L)
                .name("Alex")
                .age(75)
                .build();
    }

    public static Author createAuthorTestC() {
        return Author.builder()
                .id(333L)
                .name("Jordan")
                .age(70)
                .build();
    }

    public static Book createBookTestA() {
        return Book.builder()
                .isbn("1234-5678-9012-3456-1")
                .title("Mockito for Dummies")
                .authorId(999L)
                .build();
    }

    public static Book createBookTestB() {
        return Book.builder()
                .isbn("1234-5678-9012-3456-2")
                .title("JUnit for Dummies")
                .authorId(999L)
                .build();
    }

    public static Book createBookTestC() {
        return Book.builder()
                .isbn("1234-5678-9012-3456-3")
                .title("Spring Boot for Dummies")
                .authorId(999L)
                .build();
    }
}
