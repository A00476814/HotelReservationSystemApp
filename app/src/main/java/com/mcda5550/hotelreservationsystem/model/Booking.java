package com.mcda5550.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.List;

public class Booking {

    private String bookingId;
    private Hotel hotel;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String email;
    private int numberOfRooms;
    private List<Guest> guestsList;

    // Constructors
    public Booking() {
    }

    public Booking(String bookingId, Hotel hotel, LocalDate checkIn, LocalDate checkOut, String email, int numberOfRooms, List<Guest> guestsList) {
        this.bookingId = bookingId;
        this.hotel = hotel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.email = email;
        this.numberOfRooms = numberOfRooms;
        this.guestsList = guestsList;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Guest> getGuestsList() {
        return guestsList;
    }

    public void setGuestsList(List<Guest> guestsList) {
        this.guestsList = guestsList;
    }

    // Nested Guest class
    public static class Guest {

        private Long id;
        private String guestName;
        private String gender;

        // Constructors
        public Guest() {
        }

        public Guest(Long id, String guestName, String gender) {
            this.id = id;
            this.guestName = guestName;
            this.gender = gender;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
