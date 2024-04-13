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

import com.mcda5550.hotelreservationsystem.api.HotelApiService;
import com.mcda5550.hotelreservationsystem.api.RetrofitClient;
import com.mcda5550.hotelreservationsystem.model.HotelListData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListFragment extends Fragment implements HotelListAdapter.OnItemClickListener{

    TextView titleTextView;
    TextView descriptionTextView;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        titleTextView = view.findViewById(R.id.heading_text_view);
        descriptionTextView = view.findViewById(R.id.searchResultView);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.hotelsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieving arguments passed from the previous fragment
        String nameOfGuest = getArguments().getString("name of guest");
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        int numberOfRooms = Integer.parseInt(getArguments().getString("number of rooms"));
        int numberOfGuests = Integer.parseInt(getArguments().getString("number of guests"));

        // Setting the description text
        descriptionTextView.setText(String.format("Welcome %s, displaying hotels where %d rooms are available for %d guests staying between dates %s to %s", nameOfGuest, numberOfRooms, numberOfGuests, checkInDate, checkOutDate));
        titleTextView.setText(R.string.hotelListLayoutHeading);

        // Fetch hotels from the API
        fetchHotels(checkInDate, checkOutDate, numberOfRooms);
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
        Bundle bundle = new Bundle();
        bundle.putString("name", item.getName());
        bundle.putDouble("price", item.getPrice());
    }
}
