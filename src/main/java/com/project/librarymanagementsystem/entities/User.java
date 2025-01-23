package com.project.librarymanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "users", schema = "librarydb")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 127)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 127)
    private String name;

    @Size(max = 127)
    @NotNull
    @Column(name = "PASSWORD", nullable = false, length = 127)
    private String password;

    @NotNull
    @Lob
    @Column(name = "ROLE", nullable = false)
    private String role;

    @NotNull
    @Column(name = "IS_INACTIVE", nullable = false)
    private boolean is_inactive = false;

    public User() {
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getIsInactive() {
        return is_inactive;
    }

    public void setIsInactive(boolean is_inactive) {
        this.is_inactive = is_inactive;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User other)) {
            return false;
        }
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}