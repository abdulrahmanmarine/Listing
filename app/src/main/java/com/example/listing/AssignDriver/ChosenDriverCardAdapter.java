package com.example.listing.AssignDriver;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.databinding.AssignSpinnerItemBinding;
import com.example.listing.databinding.ChosenDriverCardItemBinding;
import com.example.listing.models.Driver;

import java.util.ArrayList;
import java.util.List;

public class ChosenDriverCardAdapter extends RecyclerView.Adapter<ChosenDriverCardAdapter.ViewHolder>  {
    ArrayList<Driver> loaders;

    public ChosenDriverCardAdapter(ArrayList<Driver> loaders) {
        this.loaders = loaders;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChosenDriverCardItemBinding itemRowBinding;
        public TextView chosenDriverName;

        public ViewHolder(@NonNull ChosenDriverCardItemBinding itemRowBinding){
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            chosenDriverName = itemRowBinding.getRoot().findViewById(R.id.chosen_driver_card_tv);
        }

        public void bind(Driver driver) {
            itemRowBinding.setChosenDriver(driver);
            itemRowBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ChosenDriverCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChosenDriverCardItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chosen_driver_card_item, parent, false);

        return new ChosenDriverCardAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenDriverCardAdapter.ViewHolder holder, int position) {
        Driver driver = loaders.get(position);
        holder.bind(driver);
    }

    @Override
    public int getItemCount() {
        return loaders.size();
    }
}
