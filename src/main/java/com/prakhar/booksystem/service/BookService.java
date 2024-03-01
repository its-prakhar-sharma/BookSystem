package com.prakhar.booksystem.service;

import com.prakhar.booksystem.controller.BookController;
import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.BookHistory;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.repository.AuthorRepository;
import com.prakhar.booksystem.repository.BookHistoryRepository;
import com.prakhar.booksystem.repository.BookRepository;
import com.prakhar.booksystem.repository.LibraryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookHistoryRepository bookHistoryRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    @Autowired
    AuthorService authorService;

    static {
        logger = LoggerFactory.getLogger(BookController.class);
    }


    public ResponseEntity getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty())
                return ResponseEntity.ok("Books not found");

            return ResponseEntity.ok(books);
        } catch (Exception e) {
            logger.error("Error retrieving books.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity getBook(int id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            return optionalBook.map(book -> ResponseEntity.ok(book)).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (Exception e) {
            logger.error("Error getting a book.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity addBook(Book book) {
        // Check if the publisher and library are provided
        if (book.getPublisher() == null || book.getLibrary() == null || book.getAuthor() == null) {
            return ResponseEntity.badRequest().body("Author, Publisher and Library are mandatory for creating a book.");
        }

        Author existingAuthor = authorRepository.findByFirstNameAndLastName(book.getAuthor().getFirstName(),
                book.getAuthor().getLastName());

        if (existingAuthor != null) {
            // Author already exists, use the existing author
            book.setAuthor(existingAuthor);
        } else {
            // Author doesn't exist, save the new author
            try {
                Author newAuthor = authorRepository.save(book.getAuthor());
                book.setAuthor(newAuthor);
            } catch (Exception e) {
                logger.error("Error updating a book at time of saving an author.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Library existingLibrary = libraryRepository.findByLibraryName(book.getLibrary().getLibraryName());
        if (existingLibrary != null) {
            // Library already exists, use the existing library
            book.setLibrary(existingLibrary);
        } else {
            // Library doesn't exist, save the new library
            try {
                Library newLibrary = libraryRepository.save(book.getLibrary());
                book.setLibrary(newLibrary);
            } catch (Exception e) {
                logger.error("Error adding a Library.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }


        try {
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (Exception e) {
            logger.error("Error adding a book.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity updateBook(int id, Book updatedBook) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isPresent()) {
                Book existingBook = optionalBook.get();

                // Update only specific fields which was sent
                if (updatedBook.getBookName() != null) {
                    existingBook.setBookName(updatedBook.getBookName());
                }

                if (updatedBook.getPublisher() != null) {
                    existingBook.setPublisher(updatedBook.getPublisher());
                }

                if (updatedBook.getAuthor() != null) {
                    if (updatedBook.getAuthor().getId() == null) {
                        return ResponseEntity.badRequest().body("Author id is required in case you want to update it.");
                    } else {
                        authorService.updateAuthor(updatedBook.getAuthor().getId(), updatedBook.getAuthor());
                        existingBook.setPublisher(updatedBook.getPublisher());
                    }
                }
                if (updatedBook.getLibrary() != null) {
                    if (updatedBook.getLibrary().getId() == null) {
                        return ResponseEntity.badRequest().body("Library id is required in case you want to update it");
                    } else {
                        Library library = libraryService.updateLibrary(updatedBook.getLibrary().getId(),
                                updatedBook.getLibrary());
                        existingBook.setLibrary(library);
                    }
                }
                // Save a historical version before updating
                saveBookHistory(optionalBook.get(), "Admin");
                Book savedBook = bookRepository.save(existingBook);

                return ResponseEntity.ok(savedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating a book.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void saveBookHistory(Book book, String updatedBy) {
        BookHistory bookHistory = new BookHistory();
        BeanUtils.copyProperties(book, bookHistory); // Copy properties from Book to BookHistory
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map(book, bookHistory);
        bookHistory.setUpdatedBy(updatedBy);
        bookHistory.setUpdateTimestamp(LocalDateTime.now());
        bookHistory.setId(null);

        bookHistoryRepository.save(bookHistory);
    }

    public ResponseEntity deleteBook(int id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isPresent()) {
                bookRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("Error deleting a book.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
