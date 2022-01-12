package com.example.listing.AssignDialog_Configured;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.AddButtonClicked;
import com.example.listing.Manual_Assigment.ChosenVehicleCardAdapter;
import com.example.listing.R;
import com.example.listing.databinding.AssignCardBinding;
import com.example.listing.models.Material;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private List<Material> materials;
    private AddButtonClicked addListener;
    private ImageView materialImage;
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


        ChosenVehicleCardAdapter chosenVehicleCardAdapter = new ChosenVehicleCardAdapter((ArrayList<Vehicle>) material.getVehicles());
        holder.chosenVehicleList.setLayoutManager(new LinearLayoutManager(context));
        holder.chosenVehicleList.setAdapter(chosenVehicleCardAdapter);
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public void filterList(ArrayList<Material> filteredList) {
        materials = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public AssignCardBinding itemRowBinding;
        public RecyclerView chosenDriverList;
        public RecyclerView chosenVehicleList;
        public TextView assignStatus;
        boolean incomplete = true;
        public ViewHolder(AssignCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            chosenVehicleList=itemRowBinding.chosenVehiclesCard;
            materialImage = itemRowBinding.materialImgCard;
            assignStatus = itemRowBinding.assignstat;
        }


        public void bind(Material material) {
            itemRowBinding.setItem(material);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();

            if(material.getZuphrContents().length()> 100) {
                String img =material.getZuphrContents().replace("data:image/jpeg;base64,","");
                byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                itemRowBinding.materialImgCard.setImageBitmap(decodedByte);
            }

//            if(material.getZuphrLoada().getVehicle().size() == 0){
//                chosenVehicleList.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border));
//            }



//            if(material.getZuphrLoada().getStatus() == "LFMS"){
//                itemRowBinding.assignstat.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border));
//            }

            if(material.getVehicles().isEmpty()){
                itemRowBinding.assignstat.setText("Not Assigned");
                itemRowBinding.assignstat.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border));
            }else{
               itemRowBinding.assignstat.setText("Assigned");
                itemRowBinding.assignstat.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border));
                itemRowBinding.assignstat.setPadding(50, 0, 50, 0);
            }
        }
    }
}
