package com.example.listing.AssignDriver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.databinding.AssignConfiguredListItemBinding;
import com.example.listing.databinding.AssignConfiguredLoaderNameBinding;
import com.example.listing.models.Driver;

import java.util.List;

public class ConfiguredLoaderListAdapter extends RecyclerView.Adapter<ConfiguredLoaderListAdapter.ViewHolder> {
    List<Driver> loaders;

    public ConfiguredLoaderListAdapter(List<Driver> loaders) {
        this.loaders = loaders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignConfiguredLoaderNameBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_configured_loader_name, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver loader = loaders.get(position);
        holder.bind(loader);
    }

    @Override
    public int getItemCount() {
        return loaders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loaderName;
        AssignConfiguredLoaderNameBinding itemRowBinding;
        public ViewHolder(AssignConfiguredLoaderNameBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            loaderName = itemRowBinding.loaderNameList;
        }

        public void bind(Driver loader) {
            itemRowBinding.setItem(loader);
        }
    }


}
