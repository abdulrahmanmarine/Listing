package com.example.listing.AssignDriver;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.AddButtonClicked;
import com.example.listing.R;
import com.example.listing.databinding.AssignCardBinding;
import com.example.listing.models.Driver;
import com.example.listing.models.Material;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private List<Material> materials;
    private AddButtonClicked addListener;
    private  Context context;



    public DriverAdapter(List<Material> materials, AddButtonClicked addListener, Context context) {
        this.materials = materials;
        this.addListener = addListener;
        this.context= context;
    }


    @NonNull
    @Override
    public DriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_card, parent, false);

        return new DriverAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.ViewHolder holder, int position) {
        Material material = materials.get(position);
        holder.bind(material);
        holder.itemRowBinding.setAddButtonListen(addListener);

        Log.i("datalist",material.getZuphrLoada().getDriver().size()+"");
        ChosenDriverCardAdapter chosenDriverCardAdapter = new ChosenDriverCardAdapter((ArrayList<Driver>) material.getZuphrLoada().getDriver());
        holder.chosenDriverList.setLayoutManager(new LinearLayoutManager(context));
        holder.chosenDriverList.setAdapter(chosenDriverCardAdapter);


        ChosenVehicleCardAdapter chosenVehicleCardAdapter = new ChosenVehicleCardAdapter((ArrayList<Vehicle>) material.getZuphrLoada().getVehicle());
        holder.chosenVehicleList.setLayoutManager(new LinearLayoutManager(context));
        holder.chosenVehicleList.setAdapter(chosenVehicleCardAdapter);
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignCardBinding itemRowBinding;
        public RecyclerView chosenDriverList;
        public RecyclerView chosenVehicleList;
        boolean incomplete = true;
        public ViewHolder(AssignCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            chosenVehicleList=itemRowBinding.chosenVehiclesCard;
            chosenDriverList = itemRowBinding.chosenDriversCard;
        }

        public void bind(Material material) {
            itemRowBinding.setItem(material);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();
        }
    }
}
