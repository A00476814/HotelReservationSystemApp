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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(HotelListData item);
    }

    public HotelListAdapter(Context context, List<HotelListData> hotelListData, OnItemClickListener listener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.hotelListData = hotelListData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hotel_list_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelListData hotel = hotelListData.get(position);
        holder.bind(hotel, listener);
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
            hotelLocation = itemView.findViewById(R.id.locationTextView);
            hotelRating = itemView.findViewById(R.id.ratingTextView);
        }

        public void bind(final HotelListData hotel, final OnItemClickListener listener) {
            hotelName.setText(hotel.getName());
            hotelPrice.setText(String.format(Locale.getDefault(), "$%.2f", hotel.getPrice()));
            hotelLocation.setText(hotel.getLocation());
            hotelRating.setText(String.format(Locale.getDefault(), "%.1f", hotel.getRating()));
            itemView.setOnClickListener(v -> listener.onItemClick(hotel));
        }
    }
}
