package com.prakhar.booksystem.controller;

import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// LibraryController.java
@RestController
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping
    public ResponseEntity getAllLibraries() {
        List<Library> libraries = libraryService.getAllLibraries();
        return ResponseEntity.ok(libraries);
    }

    @GetMapping("/{libraryName}")
    public ResponseEntity<Library> getLibraryByName(@PathVariable String libraryName) {
        Library library = libraryService.getLibraryByName(libraryName);
        if (library != null) {
            return ResponseEntity.ok(library);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        Library savedLibrary = libraryService.createLibrary(library);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLibrary);
    }

    @PutMapping("/{libraryId}")
    public ResponseEntity<Library> updateLibrary(@PathVariable Long libraryId, @RequestBody Library updatedLibrary) {
        Library updated = libraryService.updateLibrary(libraryId, updatedLibrary);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{libraryId}")
    public ResponseEntity deleteLibrary(@PathVariable Long libraryId) {

        return libraryService.deleteLibrary(libraryId);
    }

    @GetMapping("/{libraryName}/books")
    public ResponseEntity<List<Book>> getBooksInLibrary(@PathVariable String libraryName) {
        List<Book> books = libraryService.getBooksInLibrary(libraryName);
        return ResponseEntity.ok(books);
    }
}
