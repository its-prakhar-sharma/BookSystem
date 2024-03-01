package com.prakhar.booksystem.controller;

import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.model.Publisher;
import com.prakhar.booksystem.service.BookService;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;


    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> bookList = createDummyBooks();

        ResponseEntity responseEntity = new ResponseEntity<>(bookList, HttpStatus.OK);


        when(bookService.getAllBooks()).thenReturn(responseEntity);
        ResponseEntity response = bookController.getAllBooks();
        // Assertions
        Assert.assertEquals(response, responseEntity);

        // Verify that bookService.getAllBooks() was called
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBook() {
        // Mocking the behavior of bookService.getBook(id)
        int bookId = 1;
        Book mockBook = createDummyBook();
        when(bookService.getBook(bookId)).thenReturn(new ResponseEntity<>(mockBook, HttpStatus.OK));

        // Testing the controller method
        ResponseEntity result = bookController.getBook(bookId);

        // Assertions
        assertEquals(new ResponseEntity<>(mockBook, HttpStatus.OK), result);

        // Verify that bookService.getBook(id) was called with the correct argument
        verify(bookService, times(1)).getBook(bookId);
    }

    @Test
    public void testAddBook() {
        // Mocking the behavior of bookService.addBook(book)
        Book mockBook = createDummyBook();
        when(bookService.addBook(any(Book.class))).thenReturn(new ResponseEntity<>(mockBook, HttpStatus.CREATED));

        // Testing the controller method
        ResponseEntity result = bookController.addBook(mockBook);

        // Assertions
        assertEquals(new ResponseEntity<>(mockBook, HttpStatus.CREATED), result);

        // Verify that bookService.addBook(book) was called with the correct argument
        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    public void testUpdateBook() {
        // Mocking the behavior of bookService.updateBook(id, updatedBook)
        int bookId = 1;
        Book mockUpdatedBook = createDummyBook();
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(new ResponseEntity<>(mockUpdatedBook,
                HttpStatus.OK));

        // Testing the controller method
        ResponseEntity result = bookController.updateBook(bookId, new Book());

        // Assertions
        assertEquals(new ResponseEntity<>(mockUpdatedBook, HttpStatus.OK), result);

        // Verify that bookService.updateBook(id, updatedBook) was called with the correct arguments
        verify(bookService, times(1)).updateBook(eq(bookId), any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        // Mocking the behavior of bookService.deleteBook(id)
        int bookId = 1;
        when(bookService.deleteBook(bookId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // Testing the controller method
        ResponseEntity result = bookController.deleteBook(bookId);

        // Assertions
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), result);

        // Verify that bookService.deleteBook(id) was called with the correct argument
        verify(bookService, times(1)).deleteBook(bookId);
    }

    Book createDummyBook(){
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

        Publisher publisher=new Publisher();
        publisher.setPublisherName("naya publisher");

        Book book = new Book();
        book.setAuthor(author);
        book.setBookName("Book Name");
        book.setCreatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        book.setId(1);
        book.setLibrary(library);
        book.setPublisher(publisher);
        return book;
    }

    List<Book> createDummyBooks() {
        List<Book> bookList = new ArrayList<>();


        Author author = new Author();
        author.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        author.setFirstName("Prakhar");
        author.setId(1);
        author.setLastName("Sharma");
        author.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());


        Library library = new Library();
        library.setBooks(new ArrayList<>());
        library.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        library.setId(1L);
        library.setLibraryName("Library Name");
        library.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        //1st Publisher
        Publisher publisher = new Publisher();
        publisher.setPublisherName("naya publisher");

        //2nd publisher
        Publisher publisher2 = new Publisher();
        publisher2.setPublisherName("dusra publisher");

        Book book = new Book();
        book.setAuthor(author);
        book.setBookName("First Book Name");
        book.setCreatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        book.setId(1);
        book.setLibrary(library);
        book.setPublisher(publisher);


        Book book2 = new Book();
        book2.setAuthor(author);
        book2.setBookName("Second number Book ");
        book2.setCreatedTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        book2.setId(1);
        book2.setLibrary(library);
        book2.setPublisher(publisher2);

        bookList.add(book);
        bookList.add(book2);

        return bookList;
    }


}
