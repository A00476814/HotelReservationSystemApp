package com.mcda5550.hotelreservationsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private Button searchButton, clearButton, loadButton;
    private TextView welcomeTitle, checkInDateLabel, checkOutDateLabel, nameLabel, numberOfRoomsLabel, totalNumberOfGuestsLabel;
    private CheckBox savePreferencesCheck;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotel_search_layout, container, false);

        sharedPreferences = getActivity().getSharedPreferences("HotelPreferences", Context.MODE_PRIVATE);

        // Initialize UI components
        welcomeTitle = view.findViewById(R.id.welcomeTitle);
        checkInDateLabel = view.findViewById(R.id.checkInDateLabel);
        checkOutDateLabel = view.findViewById(R.id.checkOutDateLabel);
        nameLabel = view.findViewById(R.id.nameLabel);
        numberOfRoomsLabel = view.findViewById(R.id.numberOfRoomsLabel);
        totalNumberOfGuestsLabel = view.findViewById(R.id.totalNumberOfGuestsLabel);
        savePreferencesCheck = view.findViewById(R.id.savePreferencesCheck);

        searchButton = view.findViewById(R.id.searchButton);
        clearButton = view.findViewById(R.id.clearButton);
        loadButton = view.findViewById(R.id.loadButton);

        // Setting static text from resources
        welcomeTitle.setText(R.string.welcomeMessage);
        checkInDateLabel.setText(R.string.checkInDateLabel);
        checkOutDateLabel.setText(R.string.checkOutDateLabel);
        nameLabel.setText(R.string.nameLabel);
        numberOfRoomsLabel.setText(R.string.numberOfRoomsLabel);
        totalNumberOfGuestsLabel.setText(R.string.totalNumberOfGuestsLabel);
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

        // Set onClick listeners
        searchButton.setOnClickListener(v -> validateAndSearch());
        clearButton.setOnClickListener(v -> clearFields());
        loadButton.setOnClickListener(v -> loadPreferences());
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", nameEditText.getText().toString());
        editor.putInt("NumberOfRooms", Integer.parseInt(numberOfRoomsText.getText().toString()));
        editor.putInt("NumberOfGuests", Integer.parseInt(totalNumberOfGuestsEditText.getText().toString()));
        editor.apply();
        Toast.makeText(getContext(), "Preferences saved!", Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        nameEditText.setText("");
        numberOfRoomsText.setText("");
        totalNumberOfGuestsEditText.setText("");
        Toast.makeText(getContext(), "Fields cleared!", Toast.LENGTH_SHORT).show();
    }

    private void loadPreferences() {
        nameEditText.setText(sharedPreferences.getString("Name", ""));
        numberOfRoomsText.setText(String.valueOf(sharedPreferences.getInt("NumberOfRooms", 0)));
        totalNumberOfGuestsEditText.setText(String.valueOf(sharedPreferences.getInt("NumberOfGuests", 0)));
        Toast.makeText(getContext(), "Preferences loaded!", Toast.LENGTH_SHORT).show();
    }

    private void validateAndSearch() {
        if (savePreferencesCheck.isChecked()) {
            savePreferences();
        }

        String name = nameEditText.getText().toString();
        String numberOfRoomsStr = numberOfRoomsText.getText().toString();
        String totalNumberOfGuestsStr = totalNumberOfGuestsEditText.getText().toString();

        int numberOfRooms = 0;
        int totalNumberOfGuests = 0;

        try {
            numberOfRooms = Integer.parseInt(numberOfRoomsStr.isEmpty() ? "0" : numberOfRoomsStr);
            totalNumberOfGuests = Integer.parseInt(totalNumberOfGuestsStr.isEmpty() ? "0" : totalNumberOfGuestsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid numbers for rooms and guests.", Toast.LENGTH_LONG).show();
            return;
        }

        Calendar checkInDate = Calendar.getInstance();
        checkInDate.set(checkInDatePicker.getYear(), checkInDatePicker.getMonth(), checkInDatePicker.getDayOfMonth());
        Calendar checkOutDate = Calendar.getInstance();
        checkOutDate.set(checkOutDatePicker.getYear(), checkOutDatePicker.getMonth(), checkOutDatePicker.getDayOfMonth());
        Calendar today = Calendar.getInstance();

        StringBuilder errorMessage = new StringBuilder();
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

        if (errorMessage.length() > 0) {
            Toast.makeText(getContext(), errorMessage.toString(), Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("name of guest", name);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            bundle.putString("check in date", dateFormat.format(checkInDate.getTime()));
            bundle.putString("check out date", dateFormat.format(checkOutDate.getTime()));
            bundle.putString("number of guests", totalNumberOfGuestsStr);
            bundle.putString("number of rooms", numberOfRoomsStr);

            HotelListFragment hotelListFragment = new HotelListFragment();
            hotelListFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_layout, hotelListFragment);
            fragmentTransaction.remove(this);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
