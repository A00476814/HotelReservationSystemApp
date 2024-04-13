package com.mcda5550.hotelreservationsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcda5550.hotelreservationsystem.model.HotelListData;

import java.util.List;
import java.util.Locale;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {
    private List<HotelListData> hotelListData;
    private LayoutInflater layoutInflater;

    HotelListAdapter(Context context, List<HotelListData> hotelListData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.hotelListData = hotelListData;
    }
    @NonNull
    @Override
    public HotelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hotel_list_recycler_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelListData hotel = hotelListData.get(position);
        holder.hotelName.setText(hotel.getName());

        // Format the double price value as a string with two decimal places
        String formattedPrice = String.format(Locale.getDefault(), "$%.2f", hotel.getPrice());
        holder.hotelPrice.setText(formattedPrice);

        holder.hotelLocation.setText(hotel.getLocation());
        holder.hotelRating.setText(String.format(Locale.getDefault(), "%.1f", hotel.getRating()));
    }
    @Override
    public int getItemCount() {
        return hotelListData != null ? hotelListData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelPrice, hotelLocation, hotelRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotelNameTextView);
            hotelPrice = itemView.findViewById(R.id.hotelPriceTextView);
            hotelLocation = itemView.findViewById(R.id.locationTextView); // Add this ID to your layout
            hotelRating = itemView.findViewById(R.id.ratingTextView); // Add this ID to your layout
        }
    }

}
