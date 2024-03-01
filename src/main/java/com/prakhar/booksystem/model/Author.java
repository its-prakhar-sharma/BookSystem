package com.prakhar.booksystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Author {
    @Id
    @GeneratedValue
    public Integer id;

    String firstName;
    String lastName;

    @CreationTimestamp
    public LocalDateTime created_at;

    @UpdateTimestamp
    public LocalDateTime updated_at;
}
