package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.R;
import com.example.listing.databinding.AssignSpinnerItemBinding;
import com.example.listing.models.Driver;

import java.util.ArrayList;

public class LoaderAdapter extends RecyclerView.Adapter<LoaderAdapter.ViewHolder>{

   ArrayList<Driver> loaders;

    ArrayList<Driver> SelectedLoaders= new ArrayList<>();
    Context context;
    DriverSelected driverSelected;

    public LoaderAdapter(ArrayList<Driver> loaders,DriverSelected driverSelected) {
        this.loaders = loaders;
        this.driverSelected=driverSelected;
    }

    public ArrayList<Driver> getSelectedLoaders() {
        return SelectedLoaders;
    }

    public void setSelectedLoaders(ArrayList<Driver> selectedLoaders) {
        SelectedLoaders = selectedLoaders;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignSpinnerItemBinding itemRowBinding;
        public TextView loaderName;

        public ViewHolder(@NonNull AssignSpinnerItemBinding itemRowBinding){
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            loaderName = itemRowBinding.getRoot().findViewById(R.id.vehicle_list_name);
        }

        public void bind(Driver driver) {
            itemRowBinding.setItem(driver);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();
            boolean laststat, load;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssignSpinnerItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.assign_spinner_item, parent, false);
        context = parent.getContext();

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Driver driver = loaders.get(position);
        holder.bind(driver);

        for(int i=0 ;i<SelectedLoaders.size();i++){

            if(SelectedLoaders.get(i).getZuphrDriverid().contentEquals(driver.getZuphrDriverid())){
                holder.itemRowBinding.assignDriverCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
                holder.loaderName.setTextColor(Color.WHITE);
                break;
            }
            else {
                holder.itemRowBinding.assignDriverCard.setBackgroundColor(Color.WHITE);
                holder.loaderName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
            }

        }

        holder.itemView.setOnClickListener(v -> {
                driverSelected.Driverselected(driver);
                SelectedLoaders.add(driver);
                notifyDataSetChanged();
            return;

        });



    }


    @Override
    public int getItemCount() {
        return loaders.size();
    }

    public void filterList(ArrayList<Driver> loaders){
        this.loaders = loaders;
        notifyDataSetChanged();
    }



    public void uncheckRadio(int pos){

    }


}
