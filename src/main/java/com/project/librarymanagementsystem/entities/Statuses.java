package com.project.librarymanagementsystem.entities;

public enum Statuses {
    Lent("Lent"),
    Pending("Pending"),
    Returned("Returned"),
    Fined("Fined"),
    PaymentSent("Payment Sent"),
    Payed("Payed");

    public final String status;
    private Statuses(String value) {
        this.status = value;
    }
}
