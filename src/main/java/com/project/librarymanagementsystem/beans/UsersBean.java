package com.project.librarymanagementsystem.beans;

import com.project.librarymanagementsystem.entities.User;
import com.project.librarymanagementsystem.dao.interfaces.UserDAO;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named("usersBean")
@SessionScoped
public class UsersBean implements Serializable {

    @EJB
    private UserDAO userDAO;

    @Inject
    private HomeBean homeBean;

    private Optional<Integer> id = Optional.empty();

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String role;

    public List<User> getUsers() {
        return userDAO.getAllUsers()
                .stream()
                .filter(user -> !user.getIsInactive() && !homeBean.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public void createUser() throws IOException {
        if(id.isPresent()) {
            Optional<User> existingUser = userDAO.getUserById(id.get());
            if(existingUser.isEmpty()) {
                return;
            }

            existingUser.get().setName(username);
            existingUser.get().setPassword(password);
            existingUser.get().setRole(role);

            userDAO.updateUser(id.get(),existingUser.get());

            clean();
            homeBean.goToUsers();
            return;
        }

        User user = new User(username, password, role);
        userDAO.createUser(user);

        clean();
        homeBean.goToUsers();
    }

    public void deleteUser(User user) {
        userDAO.deleteUser(user.getId());
    }

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void clean()
    {
        username = "";
        password = "";
        role = "";
        id = Optional.empty();
    }
}
