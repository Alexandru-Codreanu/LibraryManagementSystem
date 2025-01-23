package com.project.librarymanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "books", schema = "librarydb")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 127)
    @Column(name = "AUTHOR", length = 127)
    private String author;

    @Size(max = 127)
    @Column(name = "PUBLISHER", length = 127)
    private String publisher;

    @Column(name = "PUBLISHED")
    private LocalDate published;

    @Size(max = 63)
    @Column(name = "GENRE", length = 63)
    private String genre;

    @NotNull
    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    public Book() {
    }

    public Book(String title, String description, String author, String publisher, LocalDate published, String genre, Integer stock) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.published = published;
        this.genre = genre;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}