package com.example.listing.AssignDialog_Configured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.AddButtonClicked;
import com.example.listing.R;
import com.example.listing.databinding.AssignConfiguredListItemBinding;
import com.example.listing.models.Vehicle;

import java.util.List;



public class ConfiguredLoaderAdapter extends RecyclerView.Adapter<ConfiguredLoaderAdapter.ViewHolder> {
    private List<Vehicle> vehicles;
    private AddButtonClicked addListener;
    private Context context;
    private int selectedPosition = 0;


    public ConfiguredLoaderAdapter(List<Vehicle> vehicles, AddButtonClicked addListener, Context context) {
        this.vehicles = vehicles;
        this.addListener = addListener;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignConfiguredListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_configured_list_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle);
        selectedPosition = holder.getAdapterPosition();

        ConfiguredLoaderListAdapter configuredLoaderListAdapter = new ConfiguredLoaderListAdapter(vehicle.getLoaders());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.itemRowBinding.configuredLoaderList.setLayoutManager(linearLayoutManager);
        holder.itemRowBinding.configuredLoaderList.setAdapter(configuredLoaderListAdapter);
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public Vehicle getSelectedItem(){
            notifyDataSetChanged();
            return vehicles.get(selectedPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public AssignConfiguredListItemBinding itemRowBinding;
        public RecyclerView configuredList;
        public RecyclerView loaderList;
        public TextView vehicleName;

        public ViewHolder(@NonNull AssignConfiguredListItemBinding
                                  itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            configuredList = itemRowBinding.configuredLoaderList;
            vehicleName = itemRowBinding.configuredVehicleTv;
        }

        public void bind(Vehicle vehicle) {
            itemRowBinding.setItem(vehicle);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.setAddListener(addListener);
            itemRowBinding.executePendingBindings();
            boolean laststat, load;
        }

    }


}
