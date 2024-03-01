package com.prakhar.booksystem.service;

import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testUpdateAuthor(){
        int authorId=1;

        Author author = new Author();
        author.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        author.setFirstName("Jane");
        author.setId(1);
        author.setLastName("Doe");
        author.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());


        Author mockUpdatedAuthor = new Author();
        mockUpdatedAuthor.setFirstName("Prakhar");
        mockUpdatedAuthor.setLastName("Sharma");

        Author expectedAuthor = new Author();
        expectedAuthor.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay());
        expectedAuthor.setFirstName("Prakhar");
        expectedAuthor.setId(1);
        expectedAuthor.setLastName("Sharma");
        expectedAuthor.setUpdated_at(LocalDate.of(1970, 1, 1).atStartOfDay());


        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(expectedAuthor);

        Author actualResponse = authorService.updateAuthor(authorId,mockUpdatedAuthor);

        Assert.assertEquals(expectedAuthor,actualResponse);

        verify(authorRepository,times(1)).findById(authorId);
        verify(authorRepository,times(1)).save(any(Author.class));

    }

    @Test(expected = RuntimeException.class)
    public void testUpdateAuthorWhenNull(){
        int authorId=1;
        Author mockUpdatedAuthor = new Author();
        mockUpdatedAuthor.setFirstName("Prakhar");
        mockUpdatedAuthor.setLastName("Sharma");

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        Author actualResponse = authorService.updateAuthor(authorId,mockUpdatedAuthor);

        Assert.assertTrue(true);
        verify(authorRepository,times(1)).findById(authorId);
    }



}
