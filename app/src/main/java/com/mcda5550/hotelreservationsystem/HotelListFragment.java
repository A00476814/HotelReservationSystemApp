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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcda5550.hotelreservationsystem.api.HotelApiService;
import com.mcda5550.hotelreservationsystem.api.RetrofitClient;
import com.mcda5550.hotelreservationsystem.model.HotelListData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Button;
import android.widget.Toast;

public class HotelListFragment extends Fragment implements HotelListAdapter.OnItemClickListener{

    TextView titleTextView;
    TextView descriptionTextView;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    Button nextButton;
    String nameOfGuest,checkInDate,checkOutDate, numberOfRoomsStr, totalNumberOfGuestsStr;



    private HotelListData selectedHotel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        titleTextView = view.findViewById(R.id.heading_text_view);
        descriptionTextView = view.findViewById(R.id.searchResultView);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.hotelsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        nextButton = view.findViewById(R.id.nextButton);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieving arguments passed from the previous fragment
         nameOfGuest = getArguments().getString("name of guest");
         checkInDate = getArguments().getString("check in date");
         checkOutDate = getArguments().getString("check out date");
        numberOfRoomsStr = getArguments().getString("number of rooms");
        totalNumberOfGuestsStr = getArguments().getString("number of guests");
        int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
        int numberOfGuests = Integer.parseInt(totalNumberOfGuestsStr);

        // Setting the description text
        descriptionTextView.setText(String.format("Welcome %s, displaying hotels where %d rooms are available for %d guests staying between dates %s to %s", nameOfGuest, numberOfRooms, numberOfGuests, checkInDate, checkOutDate));
        titleTextView.setText(R.string.hotelListLayoutHeading);

        // Fetch hotels from the API
        fetchHotels(checkInDate, checkOutDate, numberOfRooms);
        nextButton.setOnClickListener(v -> goToNextFragment());

    }

    private void fetchHotels(String checkInDate, String checkOutDate, int numberOfRooms) {
        progressBar.setVisibility(View.VISIBLE);
        HotelApiService apiService = RetrofitClient.getClient(); // Get the Retrofit API service
        Call<List<HotelListData>> call = apiService.getAvailableHotels(checkInDate, checkOutDate, numberOfRooms);

        call.enqueue(new Callback<List<HotelListData>>() {
            @Override
            public void onResponse(Call<List<HotelListData>> call, Response<List<HotelListData>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    setupRecyclerView(response.body());
                } else {
                    descriptionTextView.setText("No hotels found.");
                }
            }

            @Override
            public void onFailure(Call<List<HotelListData>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                descriptionTextView.setText("Error fetching hotels: " + t.getMessage());
            }
        });
    }

    private void setupRecyclerView(List<HotelListData> hotels) {
        HotelListAdapter adapter = new HotelListAdapter(getActivity(), hotels, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(HotelListData item) {
        this.selectedHotel = item;

    }

    private void goToNextFragment() {
        if (selectedHotel == null) {
            Toast.makeText(getContext(), "Please select a hotel first", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("nameOfGuest", getArguments().getString("name of guest"));
        bundle.putString("checkInDate", getArguments().getString("check in date"));
        bundle.putString("checkOutDate", getArguments().getString("check out date"));
        bundle.putString("numberOfRooms", getArguments().getString("number of rooms"));
        bundle.putString("totalNumberOfGuests", getArguments().getString("number of guests"));
        bundle.putLong("selectedHotelId", selectedHotel.getId());
        bundle.putString("selectedHotelName", selectedHotel.getName());


        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        BookingDetailsFragment bookingDetailsFragment = new BookingDetailsFragment();

        bookingDetailsFragment.setArguments(bundle);

        ft.replace(R.id.main_layout, bookingDetailsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
