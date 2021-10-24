package com.example.listing.AssignDialog_Configured;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.VehicleDeleteButtonClicked;
import com.example.listing.R;
import com.example.listing.databinding.ChosenVehicleItemBinding;
import com.example.listing.models.Vehicle;

import java.util.List;

public class ChosenVehicleAdapter extends RecyclerView.Adapter<ChosenVehicleAdapter.ViewHolder>{
    List<Vehicle> vehicles;
    VehicleDeleteButtonClicked deleteListener;

    public ChosenVehicleAdapter(List<Vehicle> vehicles, VehicleDeleteButtonClicked deleteListener) {
        this.vehicles = vehicles;
        this.deleteListener =deleteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChosenVehicleItemBinding itemRowBinding;
        public Button deleteButton;
        public TextView loaderName;

        public ViewHolder(@NonNull ChosenVehicleItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            deleteButton = itemRowBinding.getRoot().findViewById(R.id.dlt_button);
        }

        public void bind(Vehicle vehicle) {
            itemRowBinding.setItem(vehicle);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.setDeleteListener(deleteListener);
            itemRowBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ChosenVehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChosenVehicleItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chosen_vehicle_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenVehicleAdapter.ViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle);
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}
