package com.prakhar.booksystem.repository;

import com.prakhar.booksystem.model.BookHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {
}
