package com.mcda5550.hotelreservationsystem;

import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcda5550.hotelreservationsystem.api.HotelApiService;
import com.mcda5550.hotelreservationsystem.api.RetrofitClient;
import com.mcda5550.hotelreservationsystem.model.Booking;
import com.mcda5550.hotelreservationsystem.model.Guest;
import com.mcda5550.hotelreservationsystem.model.Hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsFragment extends Fragment {

    private TextView textPrimaryGuest;
    private TextView textNumberOfRooms;
    private TextView textCheckInDate;
    private TextView textCheckOutDate;
    private TextView textHotelName;
    private RecyclerView recyclerViewGuestDetails;
    private Button bookButton;

    private GuestDetailsAdapter adapter;
    private List<Guest> guestDetailsList;
    private Long hotelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_fragment, container, false);
        initializeViews(view);
        setupRecyclerView();
        return view;
    }

    private void initializeViews(View view) {
        textPrimaryGuest = view.findViewById(R.id.textPrimaryGuest);
        textNumberOfRooms = view.findViewById(R.id.textNumberOfRooms);
        textCheckInDate = view.findViewById(R.id.textCheckInDate);
        textCheckOutDate = view.findViewById(R.id.textCheckOutDate);
        textHotelName = view.findViewById(R.id.textHotelName);
        recyclerViewGuestDetails = view.findViewById(R.id.recyclerViewGuestDetails);
        bookButton = view.findViewById(R.id.bookButton);

        bookButton.setOnClickListener(v -> {
            Log.d("BookingDetailsFragment", "Book button pressed");
            performBooking();
        });
    }

    private void setupRecyclerView() {
        recyclerViewGuestDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        guestDetailsList = new ArrayList<>();
        adapter = new GuestDetailsAdapter(guestDetailsList);
        recyclerViewGuestDetails.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayBookingDetails();
    }

    private void displayBookingDetails() {
        if (getArguments() != null) {
            String nameOfGuest = getArguments().getString("nameOfGuest");
            String numberOfRooms = getArguments().getString("numberOfRooms");
            String checkInDate = getArguments().getString("checkInDate");
            String checkOutDate = getArguments().getString("checkOutDate");
            String hotelName = getArguments().getString("selectedHotelName");
            int numberOfGuests = Integer.parseInt(getArguments().getString("totalNumberOfGuests"));

            textPrimaryGuest.setText("Primary Guest: " + nameOfGuest);
            textNumberOfRooms.setText("Number of Rooms: " + numberOfRooms);
            textCheckInDate.setText("Check-In Date: " + checkInDate);
            textCheckOutDate.setText("Check-Out Date: " + checkOutDate);
            textHotelName.setText("The Hotel Name: " + hotelName);

            initializeGuestDetails(numberOfGuests);
        }
    }

    private void initializeGuestDetails(int numberOfGuests) {
        guestDetailsList.clear();
        for (int i = 0; i < numberOfGuests; i++) {
            guestDetailsList.add(new Guest("", "")); // Initialize with empty details
        }
        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
    }

    private void performBooking() {
        HotelApiService apiService = RetrofitClient.getClient();

        // Setup the date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        Hotel hotel = new Hotel(getArguments().getLong("selectedHotelId"));

        // Retrieve the details and parse dates
        String email = "email@example.com"; // Placeholder or retrieve from user input/context
        LocalDate checkIn = LocalDate.parse(textCheckInDate.getText().toString().split(": ")[1], formatter);
        LocalDate checkOut = LocalDate.parse(textCheckOutDate.getText().toString().split(": ")[1], formatter);
        int numberOfRooms = Integer.parseInt(textNumberOfRooms.getText().toString().split(": ")[1]);

        // Retrieve guest details from the adapter
        List<Guest> guests =  adapter.getGuests();


        Booking newBooking = new Booking();
        newBooking.setHotel(hotel);
        newBooking.setCheckIn(checkIn);
        newBooking.setCheckOut(checkOut);
        newBooking.setEmail(email);
        newBooking.setNumberOfRooms(numberOfRooms);
        // Make the API call
        Call<Booking> call = apiService.createBooking(newBooking);
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful()) {

                    Booking bookingResponse = response.body();

                } else {

                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {

            }
        });
    }
}
