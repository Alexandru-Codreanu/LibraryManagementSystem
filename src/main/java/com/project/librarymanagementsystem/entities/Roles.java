package com.project.librarymanagementsystem.entities;

public enum Roles {
    Normal("Normal"),
    Librarian("Librarian"),
    Administrator("Administrator");

    public final String role;
    private Roles(String role) {
        this.role = role;
    }
}
