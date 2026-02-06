package com.minhtetthar.database.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.minhtetthar.database.domain.dto.AuthorDto;
import com.minhtetthar.database.domain.entities.AuthorEntity;
import com.minhtetthar.database.mappers.Mapper;
import com.minhtetthar.database.services.AuthorService;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    private AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createUpdateAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
