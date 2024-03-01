package com.prakhar.booksystem.controller;

import com.prakhar.booksystem.model.Book;
import com.prakhar.booksystem.model.Library;
import com.prakhar.booksystem.service.LibraryService;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LibraryControllerTest {

    @Mock
    private LibraryService libraryService;

    @InjectMocks
    private LibraryController libraryController;

    @Test
    public void testGetAllLibraries(){
        List<Library> mockLibraries = getDummyLibraries();
        when(libraryService.getAllLibraries()).thenReturn(mockLibraries);

        ResponseEntity result = libraryController.getAllLibraries();

        assertEquals(new ResponseEntity<>(mockLibraries, HttpStatus.OK), result);

        verify(libraryService, times(1)).getAllLibraries();
    }

    @Test
    public void testGetLibraryByNameFound() {
        String libraryName = "TestLibrary";
        Library mockLibrary = new Library();
        when(libraryService.getLibraryByName(libraryName)).thenReturn(mockLibrary);

        ResponseEntity<Library> result = libraryController.getLibraryByName(libraryName);

        assertEquals(new ResponseEntity<>(mockLibrary, HttpStatus.OK), result);

        verify(libraryService, times(1)).getLibraryByName(libraryName);
    }

    @Test
    public void testGetLibraryByNameNotFound() {
        String libraryName = "NonExistentLibrary";
        when(libraryService.getLibraryByName(libraryName)).thenReturn(null);

        ResponseEntity<Library> result = libraryController.getLibraryByName(libraryName);

        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), result);

        verify(libraryService, times(1)).getLibraryByName(libraryName);
    }

    @Test
    public void testCreateLibrary() {
        Library mockLibrary = new Library();
        when(libraryService.createLibrary(any(Library.class))).thenReturn(mockLibrary);

        ResponseEntity<Library> result = libraryController.createLibrary(mockLibrary);

        assertEquals(new ResponseEntity<>(mockLibrary, HttpStatus.CREATED), result);

        verify(libraryService, times(1)).createLibrary(any(Library.class));
    }

    @Test
    public void testUpdateLibrary() {
        Long libraryId = 1L;
        Library mockUpdatedLibrary = new Library();
        when(libraryService.updateLibrary(eq(libraryId), any(Library.class))).thenReturn(mockUpdatedLibrary);

        ResponseEntity<Library> result = libraryController.updateLibrary(libraryId, new Library());

        assertEquals(new ResponseEntity<>(mockUpdatedLibrary, HttpStatus.OK), result);

        verify(libraryService, times(1)).updateLibrary(eq(libraryId), any(Library.class));
    }

    @Test
    public void testDeleteLibraryFound() {
        Long libraryId = 1L;
        when(libraryService.deleteLibrary(libraryId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity result = libraryController.deleteLibrary(libraryId);

        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), result);

        verify(libraryService, times(1)).deleteLibrary(libraryId);
    }


    @Test
    public void testGetBooksInLibrary() {
        String libraryName = "TestLibrary";
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book());
        when(libraryService.getBooksInLibrary(libraryName)).thenReturn(mockBooks);

        ResponseEntity<List<Book>> result = libraryController.getBooksInLibrary(libraryName);

        assertEquals(new ResponseEntity<>(mockBooks, HttpStatus.OK), result);

        verify(libraryService, times(1)).getBooksInLibrary(libraryName);
    }

    List<Library> getDummyLibraries(){
        List<Library> libraries = new ArrayList<>();

        Library library = new Library();
        library.setBooks(new ArrayList<>());
        library.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        library.setId(1L);
        library.setLibraryName("Library 1 dummy");
        library.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        libraries.add(library);


        Library library1 = new Library();
        library1.setBooks(new ArrayList<>());
        library1.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        library1.setId(1L);
        library1.setLibraryName("Library 2 dummy");
        library1.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());

        libraries.add(library1);



        return libraries;
    }

}
