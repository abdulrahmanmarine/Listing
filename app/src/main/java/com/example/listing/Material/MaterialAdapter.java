package com.example.listing.Material;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.Plan.PlanAdapter;
import com.example.listing.R;

import java.util.List;



public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {

    public interface loadClick{
        public void loadButtonClicked(int pos);
    }

    public interface  unloadClick{
        public void unloadButtonClick(int pos);
    }

    public interface foundClick{
        public void foundButtonClick(int pos);
    }

public interface addClick{
        public void addButtonClick(int pos);
    }

    public interface cameraClick{
        public void cameraButtonClick(int pos);
    }

    public static List<Material> materialList;
    loadClick loadListener;
    unloadClick unloadListener;
    addClick addListener;
    foundClick foundListener;
    cameraClick cameraListener;
    private static Context contexts;
    public static Boolean isLoad = true;
    PlanAdapter planAdapter;

    public MaterialAdapter(List<Material> materialList){
        this.materialList = materialList;
    }

    public void setLoadListener(loadClick loadListener) {
        this.loadListener = loadListener;
    }

    public void setUnloadListener(unloadClick unloadListener) {
        this.unloadListener = unloadListener;
    }

    public void setFoundListener(foundClick foundListener){
        this.foundListener = foundListener;
    }

    public void setAddListener(addClick addListener){
        this.addListener = addListener;
    }

    public void setCameraListener(cameraClick cameraListener){
        this.cameraListener = cameraListener;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*
        For Load card
         */

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_item_card, parent, false);
        MaterialViewHolder request = new MaterialViewHolder(card, loadListener, unloadListener, foundListener, cameraListener);


        /*
        FOR ASSIGN
         */
//        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_card, parent, false);
//        MaterialViewHolder request = new MaterialViewHolder(card, addListener);


        contexts = parent.getContext();

        return request;
    }
    public void filterList(List<Material> materialList){
        this.materialList = materialList;
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        final Material material = materialList.get(position);
//        notifyDataSetChanged();
        holder.bind(material);

//        notifyDataSetChanged();
    }

//    private void setAnimation(CardView viewToAnimate, int position){
//        Animation animation = animationUtils.loadAnimation(mContext)
//    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder{
        //private final ImageButton imgv;
        TextView materialName, textDriver, textVehicle, textStatus, textQuan, textPlanNum;
        Button loadButton, unloadButton, addButton, foundButton;
        Material material;
        ImageView materialImage;
        ImageView camerabut, locButton;

        /*
        ASSIGN material view holder
         */

//        public MaterialViewHolder(@NonNull View itemView, final addClick addListener){
//            super(itemView);
//
//            textStatus = (TextView) itemView.findViewById(R.id.assignstat);
//            materialName = (TextView) itemView.findViewById(R.id.material_name);
//            textQuan = (TextView) itemView.findViewById(R.id.quan_tv);
//            addButton = (Button) itemView.findViewById(R.id.add_button);
//            materialImage  = (ImageView) itemView.findViewById(R.id.material_img_card);
//            locButton = (ImageButton) itemView.findViewById(R.id.locbut);
//            textPlanNum = (TextView) itemView.findViewById(R.id.material_name);
//            textVehicle = (TextView) itemView.findViewById(R.id.vehicle_tv);
//            textDriver = (TextView) itemView.findViewById(R.id.driver_tv);
//
//
//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addListener.addButtonClick(getAdapterPosition());
//                }
//            });
//        }


        /*
        LOAD material view holder
         */
        public MaterialViewHolder(@NonNull View itemView, final loadClick loadListener, final unloadClick unloadListener, final foundClick foundListener, final cameraClick cameraListener) {

            super(itemView);

                materialName = (TextView) itemView.findViewById(R.id.material_name);
                materialImage = (ImageView) itemView.findViewById(R.id.material_img_card);
                textQuan = (TextView) itemView.findViewById(R.id.quan_tv);
                textStatus = (TextView) itemView.findViewById(R.id.loadstat);
                loadButton = (Button) itemView.findViewById(R.id.load_button);
                unloadButton = (Button) itemView.findViewById(R.id.unload_button);
                foundButton = (Button) itemView.findViewById(R.id.found_button);
                locButton = (ImageView) itemView.findViewById(R.id.location_btn);
                camerabut = (ImageView) itemView.findViewById(R.id.camera_btn);





        /*
        Set load onclick listener
         */
                loadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadListener.loadButtonClicked(getAdapterPosition());
                        Log.i("click","load button clicked");


                    }
                });
        /*
        Set Unload onclick listener
         */
                unloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unloadListener.unloadButtonClick(getAdapterPosition());
                    }
                });
        /*
        Set found onclick listener
         */
                foundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foundListener.foundButtonClick(getAdapterPosition());
                    }
                });

                camerabut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraListener.cameraButtonClick(getAdapterPosition());
                    }
                });
        }

        void bind(Material material){
            this.material = material;


        /*
        FOR LOADING Setting the status text for loading (for loading).
         */
        if(isLoad)
            if(material.getLoaded()){
                textStatus.setText("Loaded");
                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
                }else if(!material.getLoaded() && material.getFound()){
                textStatus.setText("Not loaded");
                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }
//            else if(!material.getLoaded()){
//                textStatus.setText("Not Loaded");
//                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
//            }
            else if (!material.getFound()){
                textStatus.setText("Not Found");
                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
            }

        /*
        for ASSIGN status
         */
        if(!isLoad) {
//            materialName.setText(material.getName());
            textDriver.setText(material.getDriver());
            textVehicle.setText(material.getVehicle());
//                        textQuan.setText(material.getQuan());
            //materialName.setText(material.getName());

            if(material.getDriver().isEmpty() && material.getVehicle().isEmpty()){
                textStatus.setText("Not Assigned");
                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));

            }else{
                textStatus.setText("Assigned");
                textStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
            }

        }
        /*
        For both
         */

            materialName.setText(material.getName());
            textQuan.setText(material.getQuan());
            materialImage.setImageResource(material.getPic());
//            if(material.getLoaded()){
//                text3.setText("Loaded");
//                text3.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
//            }else{
//                text3.setText("Not loaded");
//                text3.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
//            }


        }

    }


}


