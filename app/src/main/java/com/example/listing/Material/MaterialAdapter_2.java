package com.example.listing.Material;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.LoadButtonClicked;
import com.example.listing.Material.Loader.LoaderFragment;
import com.example.listing.Plan.PlanAdapter_2;
import com.example.listing.PlanClickListener;
import com.example.listing.PrcButtonClicked;
import com.example.listing.R;
import com.example.listing.UnloadButtonClicked;
import com.example.listing.databinding.LoadItemCardBinding;
import com.example.listing.databinding.PlanCardBinding;
import com.example.listing.models.Material2;
import com.example.listing.models.Plan2;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter_2 extends RecyclerView.Adapter<MaterialAdapter_2.MaterialViewHolder>{
   LoadButtonClicked loadListener;
   UnloadButtonClicked unloadListener;
   PrcButtonClicked prcListener;
   public static List<Material2> materialList;
    Context contexts;

    public MaterialAdapter_2(ArrayList<Material2> materialList, LoadButtonClicked loadListener, UnloadButtonClicked unloadListener, PrcButtonClicked prcListener) {
        this.materialList = materialList;
        this.loadListener = loadListener;
        this.unloadListener  = unloadListener;
        this.prcListener = prcListener;

    }

    @NonNull
    @Override
    public MaterialAdapter_2.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LoadItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.load_item_card, parent, false);
        contexts = parent.getContext();

        return new MaterialAdapter_2.MaterialViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Material2 materials = materialList.get(position);
        holder.bind(materials);

        holder.itemRowBinding.setLoadClickListen(loadListener);
        holder.itemRowBinding.setUnloadClickListen(unloadListener);
        holder.itemRowBinding.setPrcClickListen(prcListener);
    }


    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public void filterList(ArrayList<Material2> materialList){
        this.materialList = materialList;
        notifyDataSetChanged();
    }



    public static class MaterialViewHolder extends RecyclerView.ViewHolder{
        public LoadItemCardBinding itemRowBinding;
        public MaterialViewHolder(LoadItemCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Material2 material){
            //itemRowBinding.setVariable(3, getAdapterPosition());
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.setMat(material);
            itemRowBinding.executePendingBindings();
        }
    }
}
