package com.minhtetthar.database.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.minhtetthar.database.domain.entities.AuthorEntity;
import com.minhtetthar.database.repositories.AuthorRepository;
import com.minhtetthar.database.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRespository;

    public AuthorServiceImpl(AuthorRepository authorRespository) {
        this.authorRespository = authorRespository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRespository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRespository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRespository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRespository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRespository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRespository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRespository.deleteById(id);
    }

}
