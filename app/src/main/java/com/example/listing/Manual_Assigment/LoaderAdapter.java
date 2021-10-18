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
import com.example.listing.databinding.AssignSpinnerItemBinding;
import com.example.listing.models.Driver;

import java.util.ArrayList;

public class LoaderAdapter extends RecyclerView.Adapter<LoaderAdapter.ViewHolder>{

    private int selectedPosition = 0;
    ArrayList<Driver> loaders;
    Context context;
    DriverSelected driverSelected;

    public LoaderAdapter(ArrayList<Driver> loaders,DriverSelected driverSelected) {
        this.loaders = loaders;
        this.driverSelected=driverSelected;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignSpinnerItemBinding itemRowBinding;
        public RadioButton radioButton;
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

        final Driver loader = loaders.get(position);
//        holder.loaderName.setText(loader.getZuphrdrvrName());

        if(selectedPosition==position){
            holder.itemRowBinding.assignDriverCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
            holder.loaderName.setTextColor(Color.WHITE);
        }

        else {
            holder.itemRowBinding.assignDriverCard.setBackgroundColor(Color.WHITE);
            holder.loaderName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkBlue));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == holder.getAdapterPosition()) {
                    driverSelected.Driverselected(driver);
                    selectedPosition = RecyclerView.NO_POSITION;
                    notifyDataSetChanged();
                    return;
                }
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
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

    //To send the loader to the fragment
    public Driver getSelectedItem(){
        if(selectedPosition != -1){
            //Toast.makeText(context, "SelectedItem: " + loaders.get(selectedPosition), Toast.LENGTH_LONG );
            Log.i("second radio test", loaders.get(selectedPosition).getZuphrdrvrName());
            return  loaders.get(selectedPosition);
        }
        return null;
    }

    public void uncheckRadio(int pos){

    }


}
