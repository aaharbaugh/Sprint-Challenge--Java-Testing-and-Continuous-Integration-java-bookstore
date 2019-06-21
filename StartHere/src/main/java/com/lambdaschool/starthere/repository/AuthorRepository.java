package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Author;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO wrote(authorid, bookid) values (:authorid, :bookid)", nativeQuery = true)
    void postAuthorBook(long authorid, long bookid);
}
