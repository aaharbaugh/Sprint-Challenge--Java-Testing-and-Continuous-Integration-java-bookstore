package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.services.AuthorService;
import com.lambdaschool.starthere.services.BookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ApiOperation(value = "returns all books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(
            @PageableDefault(page = 0,
                    size = 5)
                    Pageable pageable)
    {
        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DATA')")
    @ApiOperation(value = "updates a specific book by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book id", dataType = "integer", paramType = "query",
                    value = "ID of book you want to update")})
    @PutMapping(value = "data/books/{id}")
    public ResponseEntity<?> updateBook(HttpServletRequest request, @RequestBody Book updateBook, @PathVariable long id)
    {
        bookService.update(updateBook, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DATA')")
    @ApiOperation(value = "delete book by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookid", dataType = "integer", paramType = "query",
                    value = "id of book you want to delete"),})
    @DeleteMapping(value = "data/books/{id}")
    public ResponseEntity<?> deleteBookById(HttpServletRequest request, @PathVariable long id)
    {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DATA')")
    @ApiOperation(value = "updates author/book table for author credit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorid", dataType = "integer", paramType = "query",
                    value = "Id of the author you wish to give credit"),
            @ApiImplicitParam(name = "bookid", dataType = "integer", paramType = "query",
                    value = "Id of the book you wish to give credit"),})
    @PostMapping(value = "data/books/authors/{authorid}/{bookid}")
    public ResponseEntity<?> postAuthorBook(HttpServletRequest request, @PathVariable long authorid, @PathVariable long bookid)
    {
        authorService.postAuthorBook(authorid, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
