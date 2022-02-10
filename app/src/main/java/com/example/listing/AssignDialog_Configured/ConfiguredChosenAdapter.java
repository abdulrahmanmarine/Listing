package com.example.listing.AssignDialog_Configured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.VehicleDeleteButtonClicked;
import com.example.listing.databinding.AssignConfiguredChosenItemBinding;
import com.example.listing.models.Vehicle;

import java.util.List;

public class ConfiguredChosenAdapter extends RecyclerView.Adapter<ConfiguredChosenAdapter.ViewHolder> {
    VehicleDeleteButtonClicked deleteListener;
    private List<Vehicle> vehicles;
    private Context context;

    public ConfiguredChosenAdapter(List<Vehicle> vehicles, VehicleDeleteButtonClicked deleteListener) {
        this.vehicles = vehicles;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ConfiguredChosenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignConfiguredChosenItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_configured_chosen_item, parent, false);
        return new ConfiguredChosenAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfiguredChosenAdapter.ViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle);

        ConfiguredLoaderListAdapter configuredLoaderListAdapter = new ConfiguredLoaderListAdapter(vehicle.getLoaders());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.itemRowBinding.configuredChosenList.setLayoutManager(linearLayoutManager);
        holder.itemRowBinding.configuredChosenList.setAdapter(configuredLoaderListAdapter);
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignConfiguredChosenItemBinding itemRowBinding;
        public RecyclerView configuredList;
        public TextView vehicleName;


        public ViewHolder(@NonNull AssignConfiguredChosenItemBinding
                                  itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            configuredList = itemRowBinding.configuredChosenList;
            vehicleName = itemRowBinding.configuredChosenVehicleTv;
        }

        public void bind(Vehicle vehicle) {
            itemRowBinding.setDeleteListener(deleteListener);
            itemRowBinding.setItem(vehicle);
            itemRowBinding.executePendingBindings();

        }

    }
}
