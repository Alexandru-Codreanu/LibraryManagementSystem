package com.project.librarymanagementsystem.dao.implementations;

import com.project.librarymanagementsystem.dao.interfaces.UserDAO;
import com.project.librarymanagementsystem.utils.SHA512Hashing;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "mysql8PU")
    private EntityManager em;

    public UserDAOImpl() {}

    @Override
    public List<com.project.librarymanagementsystem.entities.User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id", com.project.librarymanagementsystem.entities.User.class).getResultList();
    }

    @Override
    public Optional<com.project.librarymanagementsystem.entities.User> getUserById(Integer id) {
        return Optional.ofNullable(em.find(com.project.librarymanagementsystem.entities.User.class, id));
    }

    @Override
    public Optional<com.project.librarymanagementsystem.entities.User> getUserByName(String name) {
        return Optional.ofNullable(em.createQuery("SELECT u FROM User u WHERE u.name = :name", com.project.librarymanagementsystem.entities.User.class)
                .setParameter("name", name)
                .getSingleResult());
    }

    @Transactional
    @Override
    public com.project.librarymanagementsystem.entities.User createUser(com.project.librarymanagementsystem.entities.User user) {
        try {
            user.setPassword(SHA512Hashing.hash(user.getPassword()));
        }
        catch (Exception _) {}
        em.persist(user);
        em.flush();
        return user;
    }

    @Transactional
    @Override
    public void updateUser(int id, com.project.librarymanagementsystem.entities.User user) {
        com.project.librarymanagementsystem.entities.User existingUser = em.find(com.project.librarymanagementsystem.entities.User.class, id);
        existingUser.setName(user.getName());
        try{
            existingUser.setPassword(SHA512Hashing.hash(user.getPassword()));
        }
        catch(Exception _){
        }
        existingUser.setRole(user.getRole());
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        com.project.librarymanagementsystem.entities.User existingUser = em.find(com.project.librarymanagementsystem.entities.User.class, id);
        existingUser.setIsInactive(true);
    }
}
