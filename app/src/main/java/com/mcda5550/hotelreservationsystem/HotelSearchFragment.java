package com.mcda5550.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HotelSearchFragment extends Fragment {

    // UI Components
    private EditText nameEditText, numberOfRoomsText, totalNumberOfGuestsEditText;
    private DatePicker checkInDatePicker, checkOutDatePicker;
    private Button searchButton;
    private TextView welcomeTitle, checkInDateLabel, checkOutDateLabel, nameLabel, numberOfRoomsLabel, totalNumberOfGuestsLabel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment and initialize UI components
        View view = inflater.inflate(R.layout.hotel_search_layout, container, false);

        // Setting static text from resources to TextViews
        welcomeTitle = view.findViewById(R.id.welcomeTitle);
        welcomeTitle.setText(R.string.welcomeMessage);

        checkInDateLabel = view.findViewById(R.id.checkInDateLabel);
        checkInDateLabel.setText(R.string.checkInDateLabel);

        checkOutDateLabel = view.findViewById(R.id.checkOutDateLabel);
        checkOutDateLabel.setText(R.string.checkOutDateLabel);

        nameLabel = view.findViewById(R.id.nameLabel);
        nameLabel.setText(R.string.nameLabel);

        numberOfRoomsLabel = view.findViewById(R.id.numberOfRoomsLabel);
        numberOfRoomsLabel.setText(R.string.numberOfRoomsLabel);

        totalNumberOfGuestsLabel = view.findViewById(R.id.totalNumberOfGuestsLabel);
        totalNumberOfGuestsLabel.setText(R.string.totalNumberOfGuestsLabel);

        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setText(R.string.searchButton);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views to their corresponding UI elements
        nameEditText = view.findViewById(R.id.nameEditText);
        numberOfRoomsText = view.findViewById(R.id.numberOfRoomsText);
        totalNumberOfGuestsEditText = view.findViewById(R.id.totalNumberOfGuestsEditText);
        checkInDatePicker = view.findViewById(R.id.checkInDatePicker);
        checkOutDatePicker = view.findViewById(R.id.checkOutDatePicker);

        // Setting onClick listener for the search button to initiate validation and search for hotels
        searchButton.setOnClickListener(v -> validateAndSearch());
    }

    private void validateAndSearch() {
        // Retrieving user input
        String name = nameEditText.getText().toString();
        String numberOfRoomsStr = numberOfRoomsText.getText().toString();
        String totalNumberOfGuestsStr = totalNumberOfGuestsEditText.getText().toString();

        // Converting string inputs to integer for comparison
        int numberOfRooms = numberOfRoomsStr.isEmpty() ? 0 : Integer.parseInt(numberOfRoomsStr);
        int totalNumberOfGuests = totalNumberOfGuestsStr.isEmpty() ? 0 : Integer.parseInt(totalNumberOfGuestsStr);

        // Preparing calendar objects for date comparison
        Calendar checkInDate = Calendar.getInstance();
        checkInDate.set(checkInDatePicker.getYear(), checkInDatePicker.getMonth(), checkInDatePicker.getDayOfMonth());
        Calendar checkOutDate = Calendar.getInstance();
        checkOutDate.set(checkOutDatePicker.getYear(), checkOutDatePicker.getMonth(), checkOutDatePicker.getDayOfMonth());
        Calendar today = Calendar.getInstance();

        // Constructing error message based on validation
        StringBuilder errorMessage = new StringBuilder();
        // Validation for name, dates, and guest/room numbers
        // Note: Validation Logic will be enhanced for final project
        if (!name.matches("[a-zA-Z ]+")) {
            errorMessage.append("Name should only contain alphabets.\n");
        }
        if (checkInDate.before(today)) {
            errorMessage.append("Check-in date cannot be before the current date.\n");
        }
        if (!checkOutDate.after(checkInDate)) {
            errorMessage.append("Check-out date must be at least one day after the check-in date.\n");
        }
        if ((double) totalNumberOfGuests / 2 > numberOfRooms) {
            errorMessage.append("Max guests allowed per room is 2. Increase number of rooms.\n");
        }

        // Showing error message if validation fails
        if (errorMessage.length() > 0) {
            Toast.makeText(getContext(), errorMessage.toString(), Toast.LENGTH_LONG).show();
        } else {
            // Prepare data bundle for the next fragment if inputs are valid
            Bundle bundle = new Bundle();
            bundle.putString("name of guest", name);
            // Formatting dates to string for passing to the next fragment
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String checkInDateString = dateFormat.format(checkInDate.getTime());
            bundle.putString("check in date", checkInDateString);
            String checkOutDateString = dateFormat.format(checkOutDate.getTime());
            bundle.putString("check out date", checkOutDateString);
            bundle.putString("number of guests", totalNumberOfGuestsStr);
            bundle.putString("number of rooms", numberOfRoomsStr);

            // Fragment transaction to switch to the HotelListFragment
            HotelListFragment hotelListFragment = new HotelListFragment();
            hotelListFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_layout, hotelListFragment);
            fragmentTransaction.remove(HotelSearchFragment.this);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
