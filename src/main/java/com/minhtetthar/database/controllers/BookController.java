package com.minhtetthar.database.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.minhtetthar.database.domain.dto.BookDto;
import com.minhtetthar.database.domain.entities.BookEntity;
import com.minhtetthar.database.mappers.Mapper;
import com.minhtetthar.database.services.BookService;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;

    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);

        boolean isExists = bookService.isExists(isbn);

        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        if (isExists) {
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }

    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> particalUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        boolean isExists = bookService.isExists(isbn);
        if (!isExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.particalUpdate(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.OK);

    }

    @GetMapping("/books")
    public Page<BookDto> listBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        return bookService.findOne(isbn)
                .map(bookMapper::mapTo)
                .map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        boolean isExists = bookService.isExists(isbn);
        if (!isExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
