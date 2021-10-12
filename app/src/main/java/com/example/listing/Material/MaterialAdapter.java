package com.example.listing.Material;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.FoundButtonClicked;
import com.example.listing.LoadButtonClicked;
import com.example.listing.PrcButtonClicked;
import com.example.listing.R;
import com.example.listing.UnloadButtonClicked;
import com.example.listing.databinding.LoadItemCardBinding;
import com.example.listing.models.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder>{
   LoadButtonClicked loadListener;
   UnloadButtonClicked unloadListener;
   PrcButtonClicked prcListener;
   FoundButtonClicked foundListener;
   public static List<Material> materialList;
    Context contexts;

    public MaterialAdapter(ArrayList<Material> materialList, LoadButtonClicked loadListener, UnloadButtonClicked unloadListener, PrcButtonClicked prcListener
     , FoundButtonClicked foundListener) {
        this.materialList = materialList;
        this.loadListener = loadListener;
        this.unloadListener  = unloadListener;
        this.prcListener = prcListener;
        this.foundListener = foundListener;

    }

    @NonNull
    @Override
    public MaterialAdapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LoadItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.load_item_card, parent, false);
        contexts = parent.getContext();

        return new MaterialAdapter.MaterialViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Material materials = materialList.get(position);
        holder.bind(materials);

        holder.itemRowBinding.setLoadClickListen(loadListener);
        holder.itemRowBinding.setUnloadClickListen(unloadListener);
        holder.itemRowBinding.setPrcClickListen(prcListener);
        holder.itemRowBinding.setFoundClickListen(foundListener);
    }


    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public void filterList(ArrayList<Material> materialList){
        this.materialList = materialList;
        notifyDataSetChanged();
    }



    public static class MaterialViewHolder extends RecyclerView.ViewHolder{
        public LoadItemCardBinding itemRowBinding;
        private Context contexts = itemView.getContext();

        public MaterialViewHolder(LoadItemCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Material material){
            //itemRowBinding.setVariable(3, getAdapterPosition());
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.setMat(material);
            itemRowBinding.executePendingBindings();
            Log.i("uppercase" ,material.getZuphrLoada().getStatus().toUpperCase() + "");
            if(material.getZuphrLoada().getStatus() == "loaded"){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
            }else if (material.getZuphrLoada().getStatus()== "unloaded"){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }else if (material.getZuphrLoada().getStatus() == "not found"){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }else if (material.getZuphrLoada().getStatus() == "processing"){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.yellow_border));
            }
        }
    }
}
