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
import com.minhtetthar.database.domain.dto.BookDto;
import com.minhtetthar.database.domain.entities.BookEntity;
import com.minhtetthar.database.services.BookService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

        @Autowired
        private BookService bookService;

        @Autowired
        private MockMvc mockMvc;

        private ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void testThatCreateBookSuccessfullyReturned201Created() throws Exception {
                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);

                String createBookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/books/" + bookDtoA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(createBookJson))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
                BookEntity bookEntityA = TestDataUtil.createBookTestA(null);
                // Create
                BookEntity createdBookEntityA = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);
                bookDtoA.setIsbn(createdBookEntityA.getIsbn());
                bookDtoA.setTitle("UPDATED TITLE");
                String bookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/books/" + bookDtoA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(bookJson))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                                                .value(createdBookEntityA.getIsbn()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                                .value("UPDATED TITLE"));
        }

        @Test
        public void testThatCreatBookReturnsCreatedBook() throws Exception {
                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);

                String createBookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/books/" + bookDtoA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(createBookJson))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDtoA.getIsbn()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDtoA.getTitle()));
        }

        @Test
        public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
                BookEntity bookEntityA = TestDataUtil.createBookTestA(null);
                // Create
                BookEntity createdBookEntityA = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);
                bookDtoA.setIsbn(createdBookEntityA.getIsbn());
                String bookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/books/" + bookDtoA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(bookJson))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatListBooksSuccessfullyReturned200Ok() throws Exception {

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatListBooksReturnsBooks() throws Exception {
                BookEntity testBookA = TestDataUtil.createBookTestA(null);
                bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("1234-5678-9012-3456-1"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Mockito for Dummies"));

        }

        @Test
        public void testThatGetBookSuccessfullyReturned200Ok() throws Exception {

                BookEntity testBookA = TestDataUtil.createBookTestA(null);
                BookEntity createdBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

                mockMvc.perform(
                                // MockMvcRequestBuilders.get("/books/" + createdBook.getIsbn())
                                MockMvcRequestBuilders.get("/books/" + createdBook.getIsbn())
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatGetBookSuccessfullyReturned404NotFoundWhenBookDoesNotExists() throws Exception {

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books/" + "not-extist-isbn")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatGetBookSuccessfullyReturnedBookWhenBookExists() throws Exception {

                BookEntity testBookA = TestDataUtil.createBookTestA(null);
                BookEntity createdBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books/" + createdBook.getIsbn()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(createdBook.getIsbn()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(createdBook.getTitle()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(createdBook.getAuthor()));
        }

        @Test
        public void testThatParticalUpdateBookReturnsHttpStatus200Ok() throws Exception {
                BookEntity bookEntityA = TestDataUtil.createBookTestA(null);
                // Create
                BookEntity createdBookEntityA = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);
                bookDtoA.setIsbn(createdBookEntityA.getIsbn());
                String bookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.patch("/books/" + bookEntityA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(bookJson))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatParticalUpdateBookReturnsUpdatedBook() throws Exception {
                BookEntity bookEntityA = TestDataUtil.createBookTestA(null);
                // Create
                BookEntity createdBookEntityA = bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

                BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);
                bookDtoA.setTitle("UPDATED TITLE");
                String bookJson = objectMapper.writeValueAsString(bookDtoA);

                mockMvc.perform(
                                MockMvcRequestBuilders.patch("/books/" + createdBookEntityA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(bookJson))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(createdBookEntityA.getIsbn()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED TITLE"));
        }

        @Test
        public void testThatDeleteBookReturns204NoContentWhenBookExists() throws Exception {
                BookEntity bookEntityA = TestDataUtil.createBookTestA(null);
                bookService.createUpdateBook(bookEntityA.getIsbn(), bookEntityA);

                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/books/" + bookEntityA.getIsbn())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        public void testThatDeleteBookReturns404NotFoundWhenBookDoesNotExist() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/books/" + "isbn-that-does-not-exist")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

}
