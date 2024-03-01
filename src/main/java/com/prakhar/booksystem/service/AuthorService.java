package com.prakhar.booksystem.service;


import com.prakhar.booksystem.model.Author;
import com.prakhar.booksystem.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author updateAuthor(Integer authorId, Author updatedAuthor) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if (!optionalAuthor.isEmpty()) {
            Author existingAuthor = optionalAuthor.get();

            // Update only specific fields
            existingAuthor.setFirstName(updatedAuthor.getFirstName());
            existingAuthor.setLastName(updatedAuthor.getLastName());


            return  authorRepository.save(existingAuthor);
        } else {
            throw new RuntimeException("Author not found with id: " + authorId);
        }
    }


}
