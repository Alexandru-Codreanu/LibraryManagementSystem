package com.project.librarymanagementsystem.entities;

public enum Reasons {
    Late("Late"),
    Damaged("Damaged"),
    Lost("Lost");

    public final String reason;
    private Reasons(String reason) {
        this.reason = reason;
    }
}
