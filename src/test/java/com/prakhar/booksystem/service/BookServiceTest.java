package com.prakhar.booksystem.service;


import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.model.Publisher;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import com.prakhar.booksystem.repository.AuthorRepository;
import com.prakhar.booksystem.repository.BookHistoryRepository;
import com.prakhar.booksystem.repository.BookRepository;
import com.prakhar.booksystem.repository.LibraryRepository;

import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {


        @Mock
        private BookRepository bookRepository;

        @Mock
        private AuthorRepository authorRepository;

        @Mock
        private BookHistoryRepository bookHistoryRepository;

        @Mock
        private LibraryRepository libraryRepository;

        @Mock
        private LibraryService libraryService;

        @Mock
        private AuthorService authorService;

        @InjectMocks
        private BookService bookService;

        @Test
        public void testGetAllBooks() {
            // Mocking the behavior of bookRepository.findAll()
            List<Book> mockBooks = createDummyBooks();
            when(bookRepository.findAll()).thenReturn(mockBooks);

            // Testing the service method
            ResponseEntity result = bookService.getAllBooks();

            // Assertions
            assertEquals(new ResponseEntity<>(mockBooks, HttpStatus.OK), result);

            // Verify that bookRepository.findAll() was called
            verify(bookRepository, times(1)).findAll();
        }

        @Test
        public void testGetBook() {
            // Mocking the behavior of bookRepository.findById(id)
            int bookId = 1;
            Book mockBook = createDummyBook();
            when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

            // Testing the service method
            ResponseEntity result = bookService.getBook(bookId);

            // Assertions
            assertEquals(new ResponseEntity<>(mockBook, HttpStatus.OK), result);

            // Verify that bookRepository.findById(id) was called with the correct argument
            verify(bookRepository, times(1)).findById(bookId);
        }

        @Test
        public void testAddBook() {
            // Mocking the behavior of various repository methods
            Book mockBook = createDummyBook();
            Author mockAuthor = mockBook.getAuthor();
            Library mockLibrary = mockBook.getLibrary();

            when(authorRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);
            when(authorRepository.save(any(Author.class))).thenReturn(mockAuthor);
            when(libraryRepository.findByLibraryName(anyString())).thenReturn(null);
            when(libraryRepository.save(any(Library.class))).thenReturn(mockLibrary);
            when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

            // Testing the service method
            ResponseEntity result = bookService.addBook(createDummyBook());

            // Assertions
            assertEquals(new ResponseEntity<>(mockBook, HttpStatus.CREATED), result);

            // Verify that necessary repository methods were called
            verify(authorRepository, times(1)).findByFirstNameAndLastName(anyString(), anyString());
            verify(authorRepository, times(1)).save(any(Author.class));
            verify(libraryRepository, times(1)).findByLibraryName(anyString());
            verify(libraryRepository, times(1)).save(any(Library.class));
            verify(bookRepository, times(1)).save(any(Book.class));
        }


    @Test
    public void testDeleteBook() {
        int bookId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        ResponseEntity<Void> result = bookService.deleteBook(bookId);

        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

//    @Test
//    public void testUpdateBook() throws Exception {
//        int bookId = 1;
//        //setting a mock updated book
//        Book mockUpdatedBook = createDummyBook();
//        mockUpdatedBook.setBookName("Updated book");
//        mockUpdatedBook.setId(null);
//        mockUpdatedBook.setAuthor(null);
//        mockUpdatedBook.setLibrary(null);
//
//        //old book record
//        Book oldBook = new Book();
//        oldBook.setId(1);
//        oldBook.setBookName("Old Book");
//
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(oldBook));
////        when(authorService.updateAuthor(eq(bookId), any(Author.class))).thenReturn(mockUpdatedBook.getAuthor());
//
//        ResponseEntity<Book> result = bookService.updateBook(bookId, mockUpdatedBook);
//
//        assertEquals(new ResponseEntity<>(mockUpdatedBook, HttpStatus.OK), result);
//
//        verify(bookRepository, times(1)).findById(bookId);
////        verify(authorService, times(1)).updateAuthor(eq(bookId), any(Book.class));
//    }



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
