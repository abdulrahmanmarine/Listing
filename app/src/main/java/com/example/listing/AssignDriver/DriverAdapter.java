package com.example.listing.AssignDriver;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.Material.Material;
import com.example.listing.Plan.PlanAdapter;
import com.example.listing.R;
import com.fasterxml.jackson.core.Base64Variants;

import java.io.ByteArrayInputStream;
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

        holder.bind(material);

    }


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
            textQuan = (TextView) itemView.findViewById(R.id.status_tv);
            addButton = (ImageView) itemView.findViewById(R.id.add_button);
            materialImage  = (ImageView) itemView.findViewById(R.id.material_img_card);
            locButton = (ImageButton) itemView.findViewById(R.id.locbut);
            textPlanNum = (TextView) itemView.findViewById(R.id.material_name);



            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addListener.addButtonClick(getAdapterPosition());
                    Toast tst =  Toast.makeText(contexts, textDriver.getText(), Toast.LENGTH_LONG);
                    tst.show();
                }
            });

            locButton.setOnClickListener(view ->{
                Uri location = Uri.parse("geo:0,0?q=27.776278,48.875420");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                contexts.startActivity(mapIntent);
            }
                    );
        }


        /*
        LOAD material view holder
         */


        void bind(Material material){
            this.material = material;

        /*
        for ASSIGN status
         */

                textDriver.setText(material.getDriver());
                textVehicle.setText(material.getVehicle());

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
            Drawable image = null;
            if(material.getMaterial().length() > 100) {
                ByteArrayInputStream stream = new ByteArrayInputStream(Base64Variants.getDefaultVariant().decode(material.getMaterial()));
                image = BitmapDrawable.createFromStream(
                        stream, "");
                materialImage.setBackground(image);
            }
            else
                materialImage.setImageBitmap(material.getBmpImage());


            Drawable finalImage = image;
            materialImage.setOnClickListener(view -> {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        //pictureMode myFragment = new pictureMode(finalImage);
                        //activity.getSupportFragmentManager().beginTransaction().add(myFragment,"Picture").commit();


                    }
            );
            materialName.setText(material.getName());
            textQuan.setText(material.getQuan());
            materialImage.setImageResource(material.getPic());


        }

    }


}


