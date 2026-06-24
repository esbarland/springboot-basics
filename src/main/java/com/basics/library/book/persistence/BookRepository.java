package com.basics.library.book.persistence;

import com.basics.library.book.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findByIsbn(String isbn);

    @Query("SELECT b FROM BookEntity b WHERE " +
            "LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<BookEntity> search(@Param("search") String search);
}
