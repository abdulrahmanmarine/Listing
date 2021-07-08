package com.example.listing.AssignDriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.Material.Material;
import com.example.listing.Plan.PlanAdapter;
import com.example.listing.R;

import java.util.List;



public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MaterialViewHolder> {


    public interface addClick{
        public void addButtonClick(int pos);
    }

    public static List<Material> materialList;
    addClick addListener;
    private static Context contexts;
    public static Boolean isLoad = true;
    PlanAdapter planAdapter;

    public DriverAdapter(List<Material> materialList){
        this.materialList = materialList;
    }

    public void setAddListener(addClick addListener){
        this.addListener = addListener;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*
        FOR ASSIGN
         */
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_card, parent, false);
        MaterialViewHolder request = new MaterialViewHolder(card, addListener);


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
        ImageView loadButton, unloadButton, addButton, foundButton;
        Material material;
        ImageView materialImage;
        ImageButton camerabut, locButton;

        /*
        ASSIGN material view holder
         */

        public MaterialViewHolder(@NonNull View itemView, final addClick addListener){
            super(itemView);

            textStatus = (TextView) itemView.findViewById(R.id.assignstat);
            materialName = (TextView) itemView.findViewById(R.id.material_name);
            textQuan = (TextView) itemView.findViewById(R.id.quan_tv);
            addButton = (ImageView) itemView.findViewById(R.id.add_button);
            materialImage  = (ImageView) itemView.findViewById(R.id.material_img_card);
            locButton = (ImageButton) itemView.findViewById(R.id.locbut);
            textPlanNum = (TextView) itemView.findViewById(R.id.material_name);
            textVehicle = (TextView) itemView.findViewById(R.id.vehicle_tv);
            textDriver = (TextView) itemView.findViewById(R.id.driver_tv);


            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addListener.addButtonClick(getAdapterPosition());
                    Toast tst =  Toast.makeText(contexts, textDriver.getText(), Toast.LENGTH_LONG);
                    tst.show();
                }
            });
        }


        /*
        LOAD material view holder
         */


        void bind(Material material){
            this.material = material;

        /*
        for ASSIGN status
         */

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


