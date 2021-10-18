package com.example.listing.Manual_Assigment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.databinding.ManualChoosenDriverListBinding;
import com.example.listing.models.Driver;


import java.util.List;

public class ChosenDriverAdapter extends RecyclerView.Adapter<ChosenDriverAdapter.ViewHolder>{
    List<Driver> drivers;

    public ChosenDriverAdapter(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ManualChoosenDriverListBinding itemRowBinding;

        public ViewHolder(@NonNull ManualChoosenDriverListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Driver driver) {
            itemRowBinding.setItem(driver);
            itemRowBinding.executePendingBindings();

        }
    }

    @NonNull
    @Override
    public ChosenDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManualChoosenDriverListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.manual_choosen_driver_list, parent, false);

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
