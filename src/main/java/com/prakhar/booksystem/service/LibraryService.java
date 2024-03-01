package com.prakhar.booksystem.service;

import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;


    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }


    public Library getLibraryByName(String libraryName) {
        return libraryRepository.findByLibraryName(libraryName);
    }


    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public Library updateLibrary(Long libraryId, Library updatedLibrary) {
        Optional<Library> existingLibraryOptional = libraryRepository.findById(libraryId);

        if (existingLibraryOptional.isPresent()) {
            Library existingLibrary = existingLibraryOptional.get();
            existingLibrary.setLibraryName(updatedLibrary.getLibraryName());
            return libraryRepository.save(existingLibrary);
        } else {
            throw new EntityNotFoundException("Library not found with id: " + libraryId);
        }
    }

    public ResponseEntity deleteLibrary(Long libraryId) {
        try {
            if (libraryRepository.existsById(libraryId)) {
                libraryRepository.deleteById(libraryId);
                return ResponseEntity.noContent().build();
            } else {
                throw new EntityNotFoundException("Library not found with id: " + libraryId);
            }
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


    public List<Book> getBooksInLibrary(String libraryName) {
        Library library = libraryRepository.findByLibraryName(libraryName);
        if (library != null && library.getBooks() != null) {
            return library.getBooks();
        } else {
            throw new EntityNotFoundException("Library not found with name: " + libraryName);
        }
    }
}
