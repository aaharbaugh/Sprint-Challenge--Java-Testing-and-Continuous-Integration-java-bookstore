package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @Column(nullable = false)
    private String lname;

    @Column(nullable = false)
    private String fname;

    @ManyToMany
    @JoinTable(name = "wrote",
                joinColumns = {@JoinColumn(name = "authorid")},
                inverseJoinColumns = {@JoinColumn(name = "bookid")})
    @JsonIgnoreProperties("authors")
    private List<Book> books = new ArrayList<>();

    private String audit;

    public Author() {
    }

    public Author(String lastname, String firstname, List<Book> books, String audit) {
        this.lname = lastname;
        this.fname = firstname;
        this.books = books;
        this.audit = audit;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getLastname() {
        return lname;
    }

    public void setLastname(String lastname) {
        this.lname = lastname;
    }

    public String getFirstname() {
        return fname;
    }

    public void setFirstname(String firstname) {
        this.fname = firstname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String isAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }
}
