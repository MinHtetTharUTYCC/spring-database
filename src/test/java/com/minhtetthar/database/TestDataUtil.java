package com.minhtetthar.database;

import com.minhtetthar.database.domain.dto.AuthorDto;
import com.minhtetthar.database.domain.dto.BookDto;
import com.minhtetthar.database.domain.entities.AuthorEntity;
import com.minhtetthar.database.domain.entities.BookEntity;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static AuthorEntity createAuthorTestA() {
        return AuthorEntity.builder()
                .name("Minzo")
                .age(80)
                .build();
    }

    public static AuthorDto createAuthorDtoTestA() {
        return AuthorDto.builder()
                .name("Minzo")
                .age(80)
                .build();
    }

    public static AuthorEntity createAuthorTestB() {
        return AuthorEntity.builder()
                .name("Alex")
                .age(75)
                .build();
    }

    public static AuthorDto createAuthorDtoTestB() {
        return AuthorDto.builder()
                .name("Alex")
                .age(75)
                .build();
    }

    public static AuthorEntity createAuthorTestC() {
        return AuthorEntity.builder()
                .name("Jordan")
                .age(70)
                .build();
    }

    public static BookEntity createBookTestA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("1234-5678-9012-3456-1")
                .title("Mockito for Dummies")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("1234-5678-9012-3456-1")
                .title("Mockito for Dummies")
                .author(author)
                .build();
    }

    public static BookEntity createBookTestB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("1234-5678-9012-3456-2")
                .title("JUnit for Dummies")
                .author(author)
                .build();
    }

    public static BookEntity createBookTestC(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("1234-5678-9012-3456-3")
                .title("Spring Boot for Dummies")
                .author(author)
                .build();
    }
}
