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

}
