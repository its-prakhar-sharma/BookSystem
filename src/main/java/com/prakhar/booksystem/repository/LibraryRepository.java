package com.prakhar.booksystem.repository;

import com.prakhar.booksystem.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    Library findByLibraryName(String libraryName);
}
