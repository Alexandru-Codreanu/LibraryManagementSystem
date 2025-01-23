package com.project.librarymanagementsystem.dao.interfaces;

import com.project.librarymanagementsystem.entities.Book;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface BookDAO {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Integer id);
    Optional<Book> getBookByTitle(String title);
    Optional<Book> getBookByLendingId(Integer id);
    Book createBook(Book book);
    Book updateBook(int id, Book book);
    void deleteBook(Integer id);
}
