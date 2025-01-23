package com.project.librarymanagementsystem.dao.implementations;

import com.project.librarymanagementsystem.entities.LendingFine;
import com.project.librarymanagementsystem.dao.interfaces.LendingFineDAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class LendingFineDAOImpl implements LendingFineDAO {

    @PersistenceContext(unitName = "mysql8PU")
    private EntityManager em;

    public LendingFineDAOImpl() {}

    @Override
    public List<LendingFine> getAllFines() {
        return em.createQuery("SELECT lf FROM LendingFine lf ORDER BY lf.id", LendingFine.class)
                .getResultList();
    }

    @Override
    public Optional<LendingFine> getFineById(Integer id) {
        return Optional.ofNullable(em.find(LendingFine.class, id));
    }

    @Transactional
    @Override
    public LendingFine createFine(LendingFine fine) {
        em.persist(fine);
        em.flush();
        return fine;
    }

    @Transactional
    @Override
    public LendingFine updateFine(LendingFine fine) {
        return em.merge(fine);
    }

    @Transactional
    @Override
    public void deleteFine(Integer id) {
        getFineById(id).ifPresent(em::remove);
    }
}