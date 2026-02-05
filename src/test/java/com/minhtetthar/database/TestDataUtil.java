package com.minhtetthar.database;

import com.minhtetthar.database.domain.Author;
import com.minhtetthar.database.domain.Book;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author createAuthorTestA() {
        return Author.builder()
                .name("Minzo")
                .age(80)
                .build();
    }

    public static Author createAuthorTestB() {
        return Author.builder()
                .name("Alex")
                .age(75)
                .build();
    }

    public static Author createAuthorTestC() {
        return Author.builder()
                .name("Jordan")
                .age(70)
                .build();
    }

    public static Book createBookTestA(final Author author) {
        return Book.builder()
                .isbn("1234-5678-9012-3456-1")
                .title("Mockito for Dummies")
                .author(author)
                .build();
    }

    public static Book createBookTestB(final Author author) {
        return Book.builder()
                .isbn("1234-5678-9012-3456-2")
                .title("JUnit for Dummies")
                .author(author)
                .build();
    }

    public static Book createBookTestC(final Author author) {
        return Book.builder()
                .isbn("1234-5678-9012-3456-3")
                .title("Spring Boot for Dummies")
                .author(author)
                .build();
    }
}
