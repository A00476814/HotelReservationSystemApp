package com.mcda5550.hotelreservationsystem.model;


public class Hotel {
    private Long id;  // Matches int type, sufficient for ID reference

    public Hotel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
