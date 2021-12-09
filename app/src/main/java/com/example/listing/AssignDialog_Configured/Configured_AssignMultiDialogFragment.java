package com.example.listing.AssignDialog_Configured;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.listing.Kotlin.pictureMode;
import com.example.listing.AddButtonClicked;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.VehicleDeleteButtonClicked;
import com.example.listing.Material.Dispatcher.DispatcherFragment;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.R;
import com.example.listing.models.Driver;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;
import com.example.listing.models.Vehicle;
import com.example.listing.notes.pictureMode;
import com.fasterxml.jackson.core.Base64Variants;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class Configured_AssignMultiDialogFragment extends DialogFragment{

    private static int Mpostion;

    //material object
    private static final String MATERIAL_2 = "materialParam";
    private ArrayList<Driver> driversList = new ArrayList<>();
    private ArrayList<Driver> driverList2 = new ArrayList<>();
    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private Material materialParam;
    PlansDataModel model;

    Context context = getContext();

    private ArrayList<Vehicle> chosenVehicles = new ArrayList<>();


    private ArrayList<Vehicle> configureVehicles = new ArrayList<>();
    private ConfiguredLoaderAdapter configuredLoaderAdapter;

    private ArrayList<Driver> chosenDrivers = new ArrayList<>();

    private ConfiguredChosenAdapter configuredChosenAdapter;


    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getDialog().getWindow().setLayout((5 * width)/7, (5 * height)/5);
        getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.dialogborder));

        // ... other stuff you want to do in your onStart() method
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    public static Configured_AssignMultiDialogFragment newInstance(int position, Material materialParam){
        Configured_AssignMultiDialogFragment fragment = new Configured_AssignMultiDialogFragment();
        Bundle args = new Bundle();

        args.putSerializable(MATERIAL_2, materialParam);
        Mpostion=position;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assign_configured_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model=new ViewModelProvider(getActivity()).get(PlansDataModel.class);

        AddButtonClicked addButtonClicked = new AddButtonClicked() {
            @Override
            public void addButtonClicked(int pos) {
                chosenVehicles.add(vehiclesList.get(pos));
                chosenDrivers.addAll(vehiclesList.get(pos).getLoaders());
                configuredChosenAdapter.notifyDataSetChanged();
            }
        };

        VehicleDeleteButtonClicked vehicleDeleteButtonClicked = new VehicleDeleteButtonClicked() {
            @Override
            public void deleteButtonClicked(int pos) {
                chosenVehicles.remove(pos);
                int newPos = pos;
                configuredChosenAdapter.notifyDataSetChanged();
                configuredChosenAdapter.notifyItemRemoved(newPos);
                configuredChosenAdapter.notifyItemRangeChanged(newPos, chosenVehicles.size());
            }
        };

        //done button
        Button doneBut = view.findViewById(R.id.complete_btn);
        //Close Button
        ImageView closeBut = view.findViewById(R.id.but_close);
        //Text view for material name
        TextView materialName = view.findViewById(R.id.material_tv);
        //Recyclerview for configured list
        RecyclerView configuredList = view.findViewById(R.id.configured_loader_list);
        //Chosen List
        RecyclerView configuredChosenList = view.findViewById(R.id.chosen_list);



        Driver driver1 = new Driver("3", " Abdulrahman alsalim", "Heavy Vehicle Driving",
                "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
        Driver driver2 = new Driver("2", "Ahmed Alghamdi", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver3 = new Driver("2", "Ali", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver4 = new Driver("2", "Murada Alyousef", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver5 = new Driver("2", "Yousef Almohammed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver6 = new Driver("2", "Mohammed Alahmad", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        driversList.add(driver1);
        driversList.add(driver2);
        driversList.add(driver3);
        driversList.add(driver4);
        driversList.add(driver5);
        driversList.add(driver6);

        driverList2.add(driver2);
        driverList2.add(driver1);



        Vehicle vehicle1 = new Vehicle("3","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driverList2);
        Vehicle vehicle3 = new Vehicle("2","Medium", "Two Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        Vehicle vehicle4 = new Vehicle("2","Medium", "Four Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        Vehicle vehicle5 = new Vehicle("2","Medium", "two wheel fork lift", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        Vehicle vehicle6 = new Vehicle("2","Medium", "Huge Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        vehiclesList.add(vehicle1);
        vehiclesList.add(vehicle2);
        vehiclesList.add(vehicle3);
        vehiclesList.add(vehicle4);
        vehiclesList.add(vehicle5);
        vehiclesList.add(vehicle6);


        materialParam = (Material) getArguments().getSerializable(MATERIAL_2);


        //setting adapter for chosen vehicle recycler
        configuredLoaderAdapter = new ConfiguredLoaderAdapter(vehiclesList, addButtonClicked, getContext());
        configuredList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        configuredList.setAdapter(configuredLoaderAdapter);

        materialName.setText(materialParam.getZuphrShortxt());




        chosenVehicles = (ArrayList<Vehicle>) materialParam.getVehicles();

        configuredChosenAdapter = new ConfiguredChosenAdapter(chosenVehicles, vehicleDeleteButtonClicked);
        configuredChosenList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        configuredChosenList.setAdapter(configuredChosenAdapter);

//FOR IMAGE
        ImageView pic = view.findViewById(R.id.material_image);
        Bitmap decodedByte = null;
        if(materialParam.getZuphrContents().length()> 100) {
            String img =materialParam.getZuphrContents().replace("data:image/jpeg;base64,","");
            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            pic.setImageBitmap(decodedByte);
        }

        //Click on image to enlarge
        Drawable finalImage = new BitmapDrawable(getResources(), decodedByte);
        pic.setOnClickListener(view1 -> {
                    AppCompatActivity activity = (AppCompatActivity) requireContext();
                    pictureMode myFragment = new pictureMode(finalImage);
                    activity.getSupportFragmentManager().beginTransaction().add(myFragment,"Picture").commit();
                }
        );



        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialParam.setDrivers(chosenDrivers);
                materialParam.setVehicles(chosenVehicles);

               Material Material= model.MatrialsList.getValue().get(Mpostion);
                List<Material> list =model.MatrialsList.getValue();
                LoadAction loadAction=Material.getZuphrLoada();
                loadAction.setVehicle(chosenVehicles);
                Material.setZuphrLoada(loadAction);
                list.set(Mpostion,Material);
                Plan plan= model.plan.getValue();
                plan.setPlanToItems(list);
                model.plan.setValue(plan);


                List<Plan> plans=model.Plans.getValue();
                for(int i=0;i<model.Plans.getValue().size();i++){
                    if(model.plan.getValue().getZuphrLpid().equals(model.Plans.getValue().get(i).getZuphrLpid())){
                        plans.set(i,model.plan.getValue());
                        model.Plans.setValue(plans);
                    }
                }


                dismiss();
            }
        });

        closeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}



