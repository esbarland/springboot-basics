package com.basics.library.book.persistence;

import com.basics.library.book.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findByNameAndPages(String name, Integer pages);
}
