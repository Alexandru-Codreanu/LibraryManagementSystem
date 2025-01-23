package com.project.librarymanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "lendings", schema = "librarydb", indexes = {
        @Index(name = "USERID", columnList = "USERID"),
        @Index(name = "BOOKID", columnList = "BOOKID")
})
public class Lending implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USERID", nullable = false)
    private com.project.librarymanagementsystem.entities.User userid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BOOKID", nullable = false)
    private Book bookid;

    @NotNull
    @Column(name = "BORROW_DATE", nullable = false)
    private LocalDate borrowDate;

    @NotNull
    @Column(name = "RETURN_DATE", nullable = false)
    private LocalDate returnDate;

    @NotNull
    @Lob
    @Column(name = "STATUS", nullable = false)
    private String status;

    public Lending() {
    }

    public Lending(User userid, Book bookid, LocalDate borrowDate, LocalDate returnDate, String status) {
        this.userid = userid;
        this.bookid = bookid;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.project.librarymanagementsystem.entities.User getUserid() {
        return userid;
    }

    public void setUserid(com.project.librarymanagementsystem.entities.User userid) {
        this.userid = userid;
    }

    public Book getBookid() {
        return bookid;
    }

    public void setBookid(Book bookid) {
        this.bookid = bookid;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id.toString() + "|" + userid.getName() + " : " + bookid.getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Lending other)) {
            return false;
        }
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}