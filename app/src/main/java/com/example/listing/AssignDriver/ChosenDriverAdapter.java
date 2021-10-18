package com.example.listing.AssignDriver;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.VehicleDeleteButtonClicked;
import com.example.listing.R;
import com.example.listing.databinding.ChosenDriverItemBinding;
import com.example.listing.models.Driver;


import java.util.List;

public class ChosenDriverAdapter extends RecyclerView.Adapter<ChosenDriverAdapter.ViewHolder>{
    List<Driver> drivers;
    DriverDeleteButtonClicked deleteListener;

    public ChosenDriverAdapter(List<Driver> drivers, DriverDeleteButtonClicked deleteListener) {
        this.drivers = drivers;
        this.deleteListener = deleteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChosenDriverItemBinding itemRowBinding;
        public RadioButton radioButton;
        public TextView loaderName;

        public ViewHolder(@NonNull ChosenDriverItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Driver driver) {
            itemRowBinding.setDeleteListener(deleteListener);
            itemRowBinding.setItem(driver);
            itemRowBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ChosenDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChosenDriverItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chosen_driver_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenDriverAdapter.ViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.bind(driver);

    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }
}
