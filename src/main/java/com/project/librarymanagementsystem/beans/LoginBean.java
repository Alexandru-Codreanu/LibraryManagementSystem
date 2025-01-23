package com.project.librarymanagementsystem.beans;

import com.project.librarymanagementsystem.entities.Roles;
import com.project.librarymanagementsystem.entities.User;
import com.project.librarymanagementsystem.dao.interfaces.UserDAO;
import com.project.librarymanagementsystem.utils.SHA512Hashing;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UserDAO userDAO;

    @Inject
    private HomeBean homeBean;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Inject
    private FacesContext facesContext;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() throws IOException
    {
        Optional<User> loginUser = userDAO.getUserByName(username);

        if(loginUser.isEmpty())
        {
            commonLoginError();
            return;
        }

        try {
            if (!SHA512Hashing.verifyPassword(password, loginUser.get().getPassword())) {
                commonLoginError();
                return;
            }
        }
        catch (Exception _) {
            return;
        }

        homeBean.setUser(loginUser.get());
        username = "";
        password = "";
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/home.xhtml");
    }

    public void register() throws IOException {
        Optional<User> registerUser = userDAO.getUserByName(username);

        if(registerUser.isPresent())
        {
            commonRegisterError();
            return;
        }

        User newUser = userDAO.createUser(new User(password, username, Roles.Normal.role));

        if (newUser == null)
        {
            rareRegisterError();
            return;
        }

        homeBean.setUser(newUser);
        username = "";
        password = "";
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/home.xhtml");

    }

    private void commonLoginError() {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "User not found or password incorrect."));
    }

    private void commonRegisterError() {
        facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed", "User already exists."));
    }

    private void rareRegisterError() {
        facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed", "Something went wrong"));
    }
}
