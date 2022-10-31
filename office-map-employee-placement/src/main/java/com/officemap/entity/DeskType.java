package com.officemap.entity;

public enum DeskType {
    AVAILABLE("Available"),
    OCCUPIED("Occupied");

    private String status;

    DeskType(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
