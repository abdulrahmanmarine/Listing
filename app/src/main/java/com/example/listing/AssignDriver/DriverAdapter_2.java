package com.example.listing.AssignDriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.AddButtonClicked;
import com.example.listing.Plan.PlanAdapter_2;
import com.example.listing.R;
import com.example.listing.databinding.AssignCardBinding;
import com.example.listing.databinding.AssignSpinnerBinding;
import com.example.listing.databinding.PlanCardBinding;
import com.example.listing.models.Driver;
import com.example.listing.models.Material2;
import com.example.listing.models.Plan2;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter_2 extends RecyclerView.Adapter<DriverAdapter_2.ViewHolder> {
    private List<Material2> materials;
    private AddButtonClicked addListener;


    public DriverAdapter_2(List<Material2> materials, AddButtonClicked addListener) {
        this.materials = materials;
        this.addListener = addListener;
    }

    public DriverAdapter_2(ArrayList<Driver> mParam3) {
    }


    @NonNull
    @Override
    public DriverAdapter_2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_card, parent, false);

        return new DriverAdapter_2.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter_2.ViewHolder holder, int position) {
        Material2 material = materials.get(position);
        holder.bind(material);
        holder.itemRowBinding.setAddButtonListen(addListener);
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignCardBinding itemRowBinding;
        boolean incomplete = true;
        public ViewHolder(AssignCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Material2 material) {
            itemRowBinding.setItem(material);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();
        }
    }
}
