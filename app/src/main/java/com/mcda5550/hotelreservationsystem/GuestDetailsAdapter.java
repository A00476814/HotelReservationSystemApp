package com.mcda5550.hotelreservationsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcda5550.hotelreservationsystem.model.Guest;

import java.util.List;

public class GuestDetailsAdapter extends RecyclerView.Adapter<GuestDetailsAdapter.ViewHolder> {
    private List<Guest> guestDetails;

    public GuestDetailsAdapter(List<Guest> guestDetails) {
        this.guestDetails = guestDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Guest guestDetail = guestDetails.get(position);
        holder.guestNameInput.setText(guestDetail.getName());
        updateRadioButtons(holder, guestDetail.getGender());

        // Set listeners to update the guest details as they are edited
        holder.guestNameInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                guestDetails.get(holder.getAdapterPosition()).setName(((EditText) v).getText().toString());
            }
        });

        holder.genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioMale) {
                guestDetails.get(holder.getAdapterPosition()).setGender("Male");
            } else if (checkedId == R.id.radioFemale) {
                guestDetails.get(holder.getAdapterPosition()).setGender("Female");
            }
        });
    }

    private void updateRadioButtons(ViewHolder holder, String gender) {
        if ("Male".equals(gender)) {
            holder.genderRadioGroup.check(R.id.radioMale);
        } else if ("Female".equals(gender)) {
            holder.genderRadioGroup.check(R.id.radioFemale);
        }
    }

    @Override
    public int getItemCount() {
        return guestDetails.size();
    }

    public List<Guest> getGuests() {
        return guestDetails;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText guestNameInput;
        RadioGroup genderRadioGroup;
        RadioButton radioMale, radioFemale;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guestNameInput = itemView.findViewById(R.id.guestNameInput);
            genderRadioGroup = itemView.findViewById(R.id.genderRadioGroup);
            radioMale = itemView.findViewById(R.id.radioMale);
            radioFemale = itemView.findViewById(R.id.radioFemale);
        }
    }
}
