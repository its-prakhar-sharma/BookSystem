package com.prakhar.booksystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
public class Book {
    @Id
    @GeneratedValue
    public Integer id;

    public String bookName;

    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author author;

    @Embedded
    public Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonBackReference
    private Library library;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedTimestamp;


}
