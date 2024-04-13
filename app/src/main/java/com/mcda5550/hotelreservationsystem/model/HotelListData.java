package com.mcda5550.hotelreservationsystem.model;

import com.google.gson.annotations.SerializedName;

public class HotelListData {

    @SerializedName("name")
    private String name;
    private String location;
    private int numberOfRooms;
    private double rating;

    @SerializedName("price")
    private double price;
    private String availability;

    public HotelListData(String name, String location, int numberOfRooms, double rating, double price, String availability) {
        this.name = name;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
        this.rating = rating;
        this.price = price;
        this.availability = availability;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
