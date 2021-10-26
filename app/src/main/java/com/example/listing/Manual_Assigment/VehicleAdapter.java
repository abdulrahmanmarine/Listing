package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.databinding.AssignSpinnerVehicleBinding;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private int selectedPosition = -1;
    ArrayList<Vehicle> vehicles;
    Context context;
    VehicleSelected vehicleSelected;

    public VehicleAdapter(ArrayList<Vehicle> vehicles, VehicleSelected vehicleSelected) {
        this.vehicles = vehicles;
        this.vehicleSelected=vehicleSelected;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignSpinnerVehicleBinding itemRowBinding;
        public RadioButton radioButton;
        public TextView vehicleName;

        public ViewHolder(@NonNull AssignSpinnerVehicleBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            vehicleName = itemRowBinding.getRoot().findViewById(R.id.vehicle_list_name);
        }

        public void bind(Vehicle vehicle) {
            itemRowBinding.setItem(vehicle);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();
            boolean laststat, load;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignSpinnerVehicleBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_spinner_vehicle, parent, false);
        context = parent.getContext();

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle);


        if(selectedPosition==position){
            holder.itemRowBinding.assignDriverCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
            holder.vehicleName.setTextColor(Color.WHITE);
        }
        else {
            holder.itemRowBinding.assignDriverCard.setBackgroundColor(Color.WHITE);
            holder.vehicleName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    vehicleSelected.VehicleSelected(vehicle);
                    selectedPosition = RecyclerView.NO_POSITION;
                    holder.itemRowBinding.assignDriverCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
                    holder.vehicleName.setTextColor(Color.WHITE);
                    notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public void filterList(ArrayList<Vehicle> vehicles){
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }



}
