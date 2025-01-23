package com.project.librarymanagementsystem.beans;

import com.project.librarymanagementsystem.entities.Book;
import com.project.librarymanagementsystem.dao.interfaces.BookDAO;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Named("booksBean")
@SessionScoped
public class BooksBean implements Serializable {

    @EJB
    private BookDAO bookDAO;

    @Inject
    private HomeBean homeBean;

    private Optional<Integer> id = Optional.empty();

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String author;

    @NotEmpty
    private String publisher;

    @NotEmpty
    private String publishedDate;

    @NotEmpty
    private String genre;

    private Integer stock = 0;

    public List<Book> getBooks() {
        return bookDAO.getAllBooks();
    }

    public void createBook() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate publishedDate = LocalDate.parse(this.publishedDate, formatter);
            if(id.isPresent()) {
                Optional<Book> book = bookDAO.getBookById(id.get());
                if(book.isEmpty()) {
                    return;
                }
                book.get().setTitle(title);
                book.get().setDescription(description);
                book.get().setAuthor(author);
                book.get().setGenre(genre);
                book.get().setStock(stock);
                book.get().setPublisher(publisher);
                book.get().setPublished(publishedDate);

                bookDAO.updateBook(book.get().getId(), book.get());
                clean();
                homeBean.goToBooks();
                return;
            }

            bookDAO.createBook(new Book(title, description, author, publisher, publishedDate, genre, stock));
            clean();
            homeBean.goToBooks();
        }
        catch (Exception e) {
            homeBean.goToBooks();
            clean();
        }
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
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

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Optional.ofNullable(id);
    }

    public void clean()
    {
        title = "";
        description = "";
        author = "";
        genre = "";
        stock = 0;
        publisher = "";
        this.publishedDate = "";
        id = Optional.empty();
    }
}
