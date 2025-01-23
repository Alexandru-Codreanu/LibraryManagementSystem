package com.project.librarymanagementsystem.dao.implementations;

import com.project.librarymanagementsystem.entities.Book;
import com.project.librarymanagementsystem.dao.interfaces.BookDAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class BookDAOImpl implements BookDAO {

    @PersistenceContext(unitName = "mysql8PU")
    private EntityManager em;

    public BookDAOImpl() {}

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class)
                .getResultList();
    }

    @Override
    public Optional<Book> getBookById(Integer id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return Optional.ofNullable(em.find(Book.class, title));
    }

    @Override
    public Optional<Book> getBookByLendingId(Integer id) {
        Optional<Integer> bookId = Optional.ofNullable(em
                .createQuery("SELECT l.bookid from Lending l WHERE l.id =:id",Integer.class)
                .setParameter("id", id)
                .getSingleResult());

        if (bookId.isEmpty()) {
            return Optional.empty();
        }

        return getBookById(bookId.get());
    }

    @Transactional
    @Override
    public Book createBook(Book book) {
        em.persist(book);
        em.flush();
        return book;
    }

    @Transactional
    @Override
    public Book updateBook(int id, Book book) {
        return em.merge(book);
    }

    @Transactional
    @Override
    public void deleteBook(Integer id) {
        getBookById(id).ifPresent(em::remove);
    }
}