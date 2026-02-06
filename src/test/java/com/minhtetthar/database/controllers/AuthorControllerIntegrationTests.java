package com.minhtetthar.database.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhtetthar.database.TestDataUtil;
import com.minhtetthar.database.domain.dto.AuthorDto;
import com.minhtetthar.database.domain.entities.AuthorEntity;
import com.minhtetthar.database.services.AuthorService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

        @Autowired
        private AuthorService authorService;

        @Autowired
        private MockMvc mockMvc;

        private ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void testThatCreateAuthorSuccessfullyReturned201Created() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                testAuthorA.setId(null);

                String authorJson = objectMapper.writeValueAsString(testAuthorA);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/authors")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testThatCreateAuthorSuccessfullyReturnedSavedAuthor() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                testAuthorA.setId(null);

                String authorJson = objectMapper.writeValueAsString(testAuthorA);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/authors")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Minzo"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80));
        }

        @Test
        public void testThatListAuthorsSuccessfullyReturned200Ok() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatListAuthorsSuccessfullyReturnedAllAuthors() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                authorService.save(testAuthorA);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].id")).isNumber())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Minzo"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(80));
        }

        @Test
        public void testThatGetAuthorSuccessfullyReturned200OkWhenAuthorExists() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                AuthorEntity savedAuthor = authorService.save(testAuthorA);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/{id}", savedAuthor.getId())
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatGetAuthorSuccessfullyReturned400NotFoundWhenAuthorDoesNotExist() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/{id}", 1)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatGetAuthorSuccessfullyReturnsAuthorWhenAuthorExists() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                AuthorEntity savedAuthor = authorService.save(testAuthorA);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/{id}", savedAuthor.getId())
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath(("$.id")).isNumber())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Minzo"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80));
        }

        @Test
        public void testThatFullUpdateAuthorSuccessfullyReturned400NotFoundWhenNoAuthorExists() throws Exception {
                AuthorDto authorDtoA = TestDataUtil.createAuthorDtoTestA();
                String authorDtoJson = objectMapper.writeValueAsString(authorDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders
                                                .put("/authors/{id}", 999L)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorDtoJson))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatFullUpdateAuthorSuccessfullyReturned200NotFoundWhenAuthorExists() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                AuthorEntity savedAuthor = authorService.save(testAuthorA);

                AuthorDto authorDtoA = TestDataUtil.createAuthorDtoTestA();
                String authorDtoJson = objectMapper.writeValueAsString(authorDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders
                                                .put("/authors/{id}", savedAuthor.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorDtoJson))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
                AuthorEntity testAuthorA = TestDataUtil.createAuthorTestA();
                AuthorEntity savedAuthor = authorService.save(testAuthorA);

                AuthorDto authorDtoB = TestDataUtil.createAuthorDtoTestB();
                authorDtoB.setId(savedAuthor.getId());
                String authorDtoJsonB = objectMapper.writeValueAsString(authorDtoB);

                mockMvc.perform(
                                MockMvcRequestBuilders
                                                .put("/authors/{id}", savedAuthor.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorDtoJsonB))
                                .andExpect(MockMvcResultMatchers.jsonPath(("$.id")).value(savedAuthor.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDtoB.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDtoB.getAge()));
        }

}
