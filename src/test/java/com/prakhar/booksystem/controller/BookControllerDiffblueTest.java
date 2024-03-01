package com.prakhar.booksystem.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.model.Publisher;
import com.prakhar.booksystem.service.BookService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerDiffblueTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    /**
     * Method under test: {@link BookController#getAllBooks()}
     */
    @Test
    void testGetAllBooks() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookController#getBook(int)}
     */
    @Test
    void testGetBook() throws Exception {
        // Arrange
        when(bookService.getBook(anyInt())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookController#addBook(Book)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBook() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.prakhar.booksystem.model.Book["author"]->com.prakhar.booksystem.model.Author["created_at"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Author author = new Author();
        author.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        author.setFirstName("Jane");
        author.setId(1);
        author.setLastName("Doe");
        author.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        Library library = new Library();
        library.setBooks(new ArrayList<>());
        library.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        library.setId(1L);
        library.setLibraryName("Library Name");
        library.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        Book book = new Book();
        book.setAuthor(author);
        book.setBookName("Book Name");
        book.setCreatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        book.setId(1);
        book.setLibrary(library);
        book.setPublisher(new Publisher());
        book.setUpdatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        String content = (new ObjectMapper()).writeValueAsString(book);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book").contentType(MediaType.APPLICATION_JSON).content(content);

        // Act
        MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);
    }

    /**
     * Method under test: {@link BookController#deleteBook(int)}
     */
    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        when(bookService.deleteBook(anyInt())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/book/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookController#updateBook(int, Book)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateBook() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.prakhar.booksystem.model.Book["author"]->com.prakhar.booksystem.model.Author["created_at"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Author author = new Author();
        author.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        author.setFirstName("Jane");
        author.setId(1);
        author.setLastName("Doe");
        author.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        Library library = new Library();
        library.setBooks(new ArrayList<>());
        library.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        library.setId(1L);
        library.setLibraryName("Library Name");
        library.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        Book book = new Book();
        book.setAuthor(author);
        book.setBookName("Book Name");
        book.setCreatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        book.setId(1);
        book.setLibrary(library);
        book.setPublisher(new Publisher());
        book.setUpdatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        String content = (new ObjectMapper()).writeValueAsString(book);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(content);

        // Act
        MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);
    }
}
