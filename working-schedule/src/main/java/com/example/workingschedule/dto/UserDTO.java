package com.example.workingschedule.dto;

public class UserDTO {

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id
                ;
    }
}

