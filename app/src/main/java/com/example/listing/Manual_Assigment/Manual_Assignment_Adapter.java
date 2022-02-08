package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.R;
import com.example.listing.databinding.ManualAssigmentCardBinding;
import com.example.listing.models.Vehicle;

import java.util.List;

public class Manual_Assignment_Adapter extends RecyclerView.Adapter<Manual_Assignment_Adapter.ViewHolder> {
    List<Vehicle> vehicle;
    DriverDeleteButtonClicked deleteListener;
    Context context;

    public Manual_Assignment_Adapter(List<Vehicle> vehicle, DriverDeleteButtonClicked deleteListener,
                                     Context ctx) {
        this.vehicle = vehicle;
        this.deleteListener = deleteListener;
        this.context = ctx;
    }

    @NonNull
    @Override
    public Manual_Assignment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManualAssigmentCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.manual_assigment_card, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Manual_Assignment_Adapter.ViewHolder holder, int position) {
        Vehicle vehicleitem = vehicle.get(position);
        holder.bind(vehicleitem);
        if (vehicleitem.getLoaders().size() > 0) {

            ChosenDriverAdapter recyclerviewAdapter = new ChosenDriverAdapter(vehicleitem.getLoaders());
            holder.DriversRecycleView.setLayoutManager(new GridLayoutManager(context, 5));
            holder.DriversRecycleView.setAdapter(recyclerviewAdapter);
        }


    }

    @Override
    public int getItemCount() {
        if (vehicle != null)
            return vehicle.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ManualAssigmentCardBinding itemRowBinding;
        public RecyclerView DriversRecycleView;

        public ViewHolder(@NonNull ManualAssigmentCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            this.DriversRecycleView = itemRowBinding.Driverlist;
        }

        public void bind(Vehicle vehicle) {

            itemRowBinding.setDeleteListener(deleteListener);
            itemRowBinding.setItem(vehicle);
            itemRowBinding.setDeleteListener(deleteListener);
            itemRowBinding.executePendingBindings();
        }


    }
}
