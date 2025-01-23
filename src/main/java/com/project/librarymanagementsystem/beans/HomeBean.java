package com.project.librarymanagementsystem.beans;

import com.project.librarymanagementsystem.entities.Book;
import com.project.librarymanagementsystem.entities.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@Named("homeBean")
@SessionScoped
public class HomeBean implements Serializable {

    @Inject
    private FacesContext facesContext;

    @Inject
    private BooksBean booksBean;

    @Inject
    private UsersBean usersBean;

    private Optional<User> user;

    public User getUser() {
        return user.orElse(null);
    }

    public boolean isLoggedIn() {
        return user != null && user.isPresent();
    }

    public void setUser(User user) {
        this.user = Optional.of(user);
    }

    public void goToBooks() throws IOException {
        booksBean.clean();
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/books.xhtml");
    }

    public void goToBookForm() throws IOException {
        booksBean.clean();
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/book_form.xhtml");
    }

    public void goToBookForm(Book book) throws IOException {
        booksBean.setTitle(book.getTitle());
        booksBean.setAuthor(book.getAuthor());
        booksBean.setGenre(book.getGenre());
        booksBean.setDescription(book.getDescription());
        booksBean.setPublisher(book.getPublisher());
        booksBean.setPublishedDate(book.getPublished().toString());
        booksBean.setStock(book.getStock());
        booksBean.setId(book.getId());
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/book_form.xhtml");
    }

    public void goToLendings() throws IOException {
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/lendings.xhtml");
    }

    public void goToLendingFineForm() throws IOException {
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/lending_form.xhtml");
    }

    public void goToUsers() throws IOException {
        usersBean.clean();
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/users.xhtml");
    }

    public void goToUsersForm() throws IOException {
        usersBean.clean();
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/user_form.xhtml");
    }

    public void goToUsersForm(User existingUser) throws IOException {
        usersBean.setUsername(existingUser.getName());
        usersBean.setPassword(existingUser.getPassword());
        usersBean.setRole(existingUser.getRole());
        usersBean.setId(Optional.ofNullable(existingUser.getId()));
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/user_form.xhtml");
    }

    public void goToHome() throws IOException {
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/home.xhtml");
    }

    public void goToLogin() throws IOException {
        user = Optional.empty();
        facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath());
    }
}
