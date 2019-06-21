package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.repository.AuthorRepository;
import com.lambdaschool.starthere.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "authorService")
public class AuthorServiceImpl implements AuthorService{


    @Autowired
    AuthorRepository authorrepos;

    @Autowired
    BookRepository bookrepos;

    @Override
    public List<Author> findAll(Pageable pageable) {
        List<Author> list = new ArrayList<>();
        authorrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void postAuthorBook(long authorid, long bookid) {
        if(authorrepos.findById(authorid).isPresent())
        {
            if(bookrepos.findById(bookid) != null)
            {
                authorrepos.postAuthorBook(authorid, bookid);
            } else
            {
                throw new EntityNotFoundException(Long.toString(bookid));
            }
        } else
        {
            throw new EntityNotFoundException(Long.toString(authorid));
        }
    }
}
