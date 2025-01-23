package com.project.librarymanagementsystem.dao.implementations;

import com.project.librarymanagementsystem.entities.Book;
import com.project.librarymanagementsystem.entities.Lending;
import com.project.librarymanagementsystem.dao.interfaces.LendingDAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class LendingDAOImpl implements LendingDAO {

    @PersistenceContext(unitName = "mysql8PU")
    private EntityManager em;

    public LendingDAOImpl() {}

    @Override
    public List<Lending> getAllLendings() {
        return em.createQuery("SELECT l FROM Lending l", Lending.class)
                .getResultList();
    }

    @Override
    public Optional<Lending> getLendingById(Integer id) {
        return Optional.ofNullable(em.find(Lending.class, id));
    }

    @Transactional
    @Override
    public Lending createLending(Lending lending) {
        em.persist(lending);
        em.flush();
        return lending;
    }

    @Transactional
    @Override
    public Lending updateLending(Lending lending) {
        return em.merge(lending);
    }

    @Transactional
    @Override
    public void deleteLending(Integer id) {
        getLendingById(id).ifPresent(em::remove);
    }

    @Transactional
    @Override
    public List<Lending> getUserLendings(Integer id) {
        return em.createQuery("SELECT l FROM Lending l WHERE l.userid.id = :id", Lending.class)
                .setParameter("id",id)
                .getResultList();
    }

    @Transactional
    @Override
    public Optional<Book> getBooksByLendingId(Integer id) {
        Optional<Integer> bookId = Optional.ofNullable(em
                .createQuery("SELECT l.bookid from Lending l WHERE l.id =:id",Integer.class)
                .setParameter("id", id)
                .getSingleResult());

        return bookId.map(integer -> em.find(Book.class, integer));
    }
}