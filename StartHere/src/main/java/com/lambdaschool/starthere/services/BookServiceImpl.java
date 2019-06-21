package com.lambdaschool.starthere.services;


import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.Role;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookrepos;

    @Override
    public List<Book> findAll(Pageable pageable) {
        List<Book> list = new ArrayList<>();
        bookrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Book update(Book book, long id) {

        Book currentBook = bookrepos.findById(id);

        if(currentBook != null)
        {
            if(id == currentBook.getBookid())
            {
                if(book.getTitle() != null)
                {
                    currentBook.setTitle(book.getTitle());
                }

                if(book.getCopy() != null)
                {
                    currentBook.setCopy(book.getCopy());
                }

                if(book.getISBN() != null)
                {
                    currentBook.setISBN(book.getISBN());
                }
                return bookrepos.save(currentBook);
            } else
            {
                throw new EntityNotFoundException((Long.toString(id) + " Not Current Book"));
            }
        } else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (bookrepos.findById(id) != null)
        {
            bookrepos.deleteBookById(id);
            bookrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }


}
