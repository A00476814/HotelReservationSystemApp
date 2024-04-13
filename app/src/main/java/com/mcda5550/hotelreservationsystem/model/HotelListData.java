package com.mcda5550.hotelreservationsystem.model;

public class HotelListData {

    private String hotelName;
    private String location;
    private int numberOfRooms;
    private double rating;
    private String price;
    private String availability;

    public HotelListData(String hotelName, String location, int numberOfRooms, double rating, String price, String availability) {
        this.hotelName = hotelName;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
        this.rating = rating;
        this.price = price;
        this.availability = availability;
    }

    // Getters and setters

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
