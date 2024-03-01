package com.prakhar.booksystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "book_history")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BookHistory extends Book {

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Integer historyId;

    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_timestamp")
    private LocalDateTime updateTimestamp;

}

