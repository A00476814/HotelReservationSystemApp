package com.mcda5550.hotelreservationsystem.model;

public class Guest {
    private String guestName;
    private String gender;

    public Guest(String name, String gender) {
        this.guestName = name;
        this.gender = gender;
    }

    public String getName() {
        return guestName;
    }

    public void setName(String name) {
        this.guestName = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
