package com.prakhar.booksystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

// Library.java
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libraryName;

    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Book> books;

    @CreationTimestamp
    public LocalDateTime created_at;

    @UpdateTimestamp
    public LocalDateTime updated_at;
}
