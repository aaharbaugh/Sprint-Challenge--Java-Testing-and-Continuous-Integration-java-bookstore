package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE from wrote where bookid = :bookid", nativeQuery = true)
    void deleteBookById(long bookid);

    Book findById(long id);
}
