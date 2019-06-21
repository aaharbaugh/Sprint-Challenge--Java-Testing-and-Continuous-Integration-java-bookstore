package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.services.AuthorService;
import com.lambdaschool.starthere.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AuthorController {


    @Autowired
    private AuthorService authorService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "returns all Authors", response = Author.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(
            @PageableDefault(page = 0,
                    size = 5)
                    Pageable pageable)
    {

        List<Author> myAuthors = authorService.findAll(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }

}