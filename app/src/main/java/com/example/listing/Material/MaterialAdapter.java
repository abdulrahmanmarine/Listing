package com.example.listing.Material;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.CameraButtonClicked;
import com.example.listing.FoundButtonClicked;
import com.example.listing.LoadButtonClicked;
import com.example.listing.NoteButtonClicked;
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
   NoteButtonClicked noteListener;
   CameraButtonClicked cameraListener;
   AppCompatActivity act;
   public static List<Material> materialList;
    Context contexts;

    public MaterialAdapter(ArrayList<Material> materialList, LoadButtonClicked loadListener, UnloadButtonClicked unloadListener, PrcButtonClicked prcListener
            , FoundButtonClicked foundListener, NoteButtonClicked noteListener, CameraButtonClicked cameraListener) {
        this.materialList = materialList;
        this.loadListener = loadListener;
        this.unloadListener  = unloadListener;
        this.prcListener = prcListener;
        this.foundListener = foundListener;
        this.noteListener = noteListener;
        this.cameraListener = cameraListener;
    }

    @NonNull
    @Override
    public MaterialAdapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LoadItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.load_item_card, parent, false);
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
        holder.itemRowBinding.setNoteClickListen(noteListener);
        holder.itemRowBinding.setCameraButtonCLicked(cameraListener);
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
        public ImageView materialImage;
        private Context contexts = itemView.getContext();

        public MaterialViewHolder(LoadItemCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            materialImage = itemRowBinding.materialImgCard;
        }

        public void bind(Material material){
            //itemRowBinding.setVariable(3, getAdapterPosition());
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.setMat(material);
            itemRowBinding.executePendingBindings();
            Log.i("uppercase" ,material.getZuphrLoada().getStatus().toUpperCase() + "");
            if(material.getZuphrLoada().getStatus().equalsIgnoreCase("loaded")  || material.getZuphrLoada().getStatus() == "LFMS"){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
            }else if (material.getZuphrLoada().getStatus().equalsIgnoreCase("unloaded")){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }else if (material.getZuphrLoada().getStatus().equalsIgnoreCase("not found")){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }else if (material.getZuphrLoada().getStatus().equalsIgnoreCase("processing")){
                itemRowBinding.statusTv.setBackground(ContextCompat.getDrawable(contexts, R.drawable.yellow_border));
            }

            if(material.getZuphrContents().length()> 100) {
                String img =material.getZuphrContents().replace("data:image/jpeg;base64,","");
                byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                itemRowBinding.materialImgCard.setImageBitmap(decodedByte);
            }
        }
    }
}
