package com.project.librarymanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "lending_fines", schema = "librarydb", indexes = {
        @Index(name = "LENDING_ID", columnList = "LENDING_ID")
})
public class LendingFine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LENDING_ID", nullable = false)
    private com.project.librarymanagementsystem.entities.Lending lending;

    @NotNull
    @Column(name = "AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Lob
    @Column(name = "REASON")
    private String reason;

    public LendingFine() {
    }

    public LendingFine(Lending lending, BigDecimal amount, String reason) {
        this.lending = lending;
        this.amount = amount;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.project.librarymanagementsystem.entities.Lending getLending() {
        return lending;
    }

    public void setLending(com.project.librarymanagementsystem.entities.Lending lending) {
        this.lending = lending;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}