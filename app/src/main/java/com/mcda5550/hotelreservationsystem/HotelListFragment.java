package com.mcda5550.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcda5550.hotelreservationsystem.model.HotelListData;

import java.util.ArrayList;

public class HotelListFragment extends Fragment {

    // UI component declarations
    TextView titleTextView;
    TextView descriptionTextView;
    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        titleTextView = view.findViewById(R.id.heading_text_view);
        descriptionTextView = view.findViewById(R.id.searchResultView);
        progressBar = view.findViewById(R.id.progress_bar);

        // Retrieving arguments passed from the previous fragment
        String nameOfGuest = getArguments().getString("name of guest");
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");
        String numberOfRooms = getArguments().getString("number of rooms");

        // Setting the description text with the received arguments
        descriptionTextView.setText("Welcome " + nameOfGuest + ", displaying hotels where " + numberOfRooms + " rooms are available for " + numberOfGuests + " guests staying between dates " + checkInDate + " to " + checkOutDate);
        titleTextView.setText(R.string.hotelListLayoutHeading); // Setting the title from resources for localization

        // Setting up the RecyclerView to display the list of hotels
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // Initializing RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.hotelsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Setting the adapter with the list of hotels
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), initHotelListData());
        recyclerView.setAdapter(hotelListAdapter);
    }

    public ArrayList<HotelListData> initHotelListData() {
        // Creating a list of sample hotel data for demonstration purposes
        ArrayList<HotelListData> list = new ArrayList<>();

        // Adding sample data to the list
        list.add(new HotelListData("Halifax Regional Hotel", "$2000", "true"));
        list.add(new HotelListData("Hotel Pearl", "$500", "false"));
        list.add(new HotelListData("Hotel Amano", "$800", "true"));
        list.add(new HotelListData("San Jones", "$250", "false"));
        list.add(new HotelListData("Vista Grande", "$1500", "true"));
        list.add(new HotelListData("Ocean View Inn", "$950", "true"));
        list.add(new HotelListData("Mountain Retreat", "$600", "false"));
        list.add(new HotelListData("City Center Lodge", "$300", "true"));
        list.add(new HotelListData("The Grand Palace", "$2200", "true"));
        list.add(new HotelListData("Sunny Side B&B", "$450", "false"));
        list.add(new HotelListData("Cozy Cottage", "$750", "true"));
        list.add(new HotelListData("Highland Suite", "$670", "false"));
        list.add(new HotelListData("Seaside Escape", "$990", "true"));
        list.add(new HotelListData("Riverside", "$310", "true"));
        list.add(new HotelListData("Evergreen Villa", "$1250", "false"));
        list.add(new HotelListData("Pinecrest Chalet", "$540", "true"));
        list.add(new HotelListData("Silver Mountain", "$850", "true"));
        list.add(new HotelListData("Desert Mirage", "$430", "false"));
        list.add(new HotelListData("Cliffside Inn", "$1100", "true"));
        list.add(new HotelListData("Lakeshore Hotel", "$760", "true"));

        return list;
    }
}
