package com.project.librarymanagementsystem.dao.interfaces;

import com.project.librarymanagementsystem.entities.User;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface UserDAO {
    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    Optional<User> getUserByName(String name);
    User createUser(User user);
    void updateUser(int id, User user);
    void deleteUser(Integer id);
}
