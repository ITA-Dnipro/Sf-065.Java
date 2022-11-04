package com.officemap.entity;

public enum StructureType {
    COUNTRY("Country"),
    CITY("City"),
    BUILDING("Building"),
    FLOOR("Floor"),
    ROOM("Room"),
    DESK("Desk");

    private String type;
    StructureType(String type) {
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
