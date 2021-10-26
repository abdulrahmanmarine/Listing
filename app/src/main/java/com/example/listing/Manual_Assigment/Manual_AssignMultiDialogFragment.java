package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.listing.Kotlin.pictureMode;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.R;
import com.example.listing.models.Driver;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Manual_AssignMultiDialogFragment extends DialogFragment
        implements DriverDeleteButtonClicked, DriverSelected,AdapterView.OnItemSelectedListener, VehicleSelected {

    private static int Mpostion;
    //material object
    private static final String MATERIAL_2 = "materialParam";
    private ArrayList<Driver> driversList = new ArrayList<>();

    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private Material materialParam;
    PlansDataModel model;
    DriverSelected driverSelected;
    RecyclerView loaderList;
    List<Vehicle> chosenVehicles;

    Vehicle chosenVehicle;

    private LoaderAdapter loaderAdapter;
    private VehicleAdapter vehicleAdapter;


    private Manual_Assignment_Adapter chosenVehicleAdapter;

    private ArrayList<Driver> chosenDrivers = new ArrayList<>();



    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        int dialogWidth = 1600; // specify a value here
        int dialogHeight = 1600; // specify a value here


        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        // ... other stuff you want to do in your onStart() method
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        positiveListener = (OnPositiveClickListener) context;
//        negativeListener = (OnNegativeClickListener) context;
    }

//    public Dialog onCreateDialog(){
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setTitle("My Title");
//        return dialog;
//    }

    public static Manual_AssignMultiDialogFragment newInstance(int position, Material materialParam){
        Manual_AssignMultiDialogFragment fragment = new Manual_AssignMultiDialogFragment();
        Bundle args = new Bundle();

        args.putSerializable(MATERIAL_2, materialParam);
        Mpostion=position;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assign_manual, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model=new ViewModelProvider(getActivity()).get(PlansDataModel.class);


        //add new pair button
        Button addBut = view.findViewById(R.id.add_btn), cancel_but = view.findViewById(R.id.dialog_cancel);
        //Loader recview
         loaderList = view.findViewById(R.id.loader_list);
        //vehicle recview
        RecyclerView vehicleList = view.findViewById(R.id.vehicle_list);
        //pair recview
        RecyclerView Choosenpair = view.findViewById(R.id.vehicle_chosen_list);
        //done button
        Button doneBut = view.findViewById(R.id.done_btn);
        //search edit text for loader
        EditText searchLoader = view.findViewById(R.id.search_loader);
        //search edir text for vehicle
        EditText searchVehicle = view.findViewById(R.id.search_vehicle);
        //Text view for material name
        TextView materialName = view.findViewById(R.id.assign_image_tv);


        Driver driver1 = new Driver("3", "Abdul", "Heavy Vehicle Driving",
                "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
        Driver driver2 = new Driver("2", "Ahmed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver3 = new Driver("2", "Ali", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver4 = new Driver("2", "Murada", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver5 = new Driver("2", "Yousef", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver6 = new Driver("2", "Mohammed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");

        Vehicle vehicle1 = new Vehicle("1","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle3 = new Vehicle("3","Medium", "Two Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle4 = new Vehicle("4","Medium", "Four Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle5 = new Vehicle("5","Medium", "Small Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle6 = new Vehicle("6","Medium", "Huge Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);

        driversList.add(driver1);
        driversList.add(driver2);
        driversList.add(driver3);
        driversList.add(driver4);
        driversList.add(driver5);
        driversList.add(driver6);

        vehiclesList.add(vehicle1);
        vehiclesList.add(vehicle2);
        vehiclesList.add(vehicle3);
        vehiclesList.add(vehicle4);
        vehiclesList.add(vehicle5);
        vehiclesList.add(vehicle6);


        materialParam = (Material) getArguments().getSerializable(MATERIAL_2);


        chosenVehicles  = (ArrayList<Vehicle>) materialParam.getVehicles();
        ArrayList<Driver> drivers = (ArrayList<Driver>) materialParam.getDrivers();


        //setting adapter for loaders/drivers recycler

        //setting adapter for vehicle recycler
        vehicleAdapter = new VehicleAdapter(vehiclesList ,this::VehicleSelected);
        vehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        vehicleList.setAdapter(vehicleAdapter);


        chosenVehicleAdapter = new Manual_Assignment_Adapter(chosenVehicles,this::deleteButtonClicked,getContext());
        Choosenpair.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        Choosenpair.setAdapter(chosenVehicleAdapter);

        addBut.setOnClickListener(v -> {

            chosenVehicle.setLoaders(chosenDrivers);
            if(chosenVehicle.getLoaders().size()>0){
                chosenVehicles.add(chosenVehicle);


                for(int i=0 ;i<vehiclesList.size();i++){
                    if(chosenVehicle.equals(vehiclesList.get(0))){
                        vehiclesList.remove(i);
                        break;
                    }
                }
                vehicleAdapter.notifyDataSetChanged();
                chosenVehicleAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getContext(),"Assign drivers to the vehicle first",Toast.LENGTH_SHORT).show();
            }

        });


        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Material Material= model.MatrialsList.getValue().get(Mpostion);
                Log.i("matposition", Mpostion+"");
                List<Material> list =model.MatrialsList.getValue();
                LoadAction loadAction=Material.getZuphrLoada();
                loadAction.setVehicle(chosenVehicles);
                Material.setZuphrLoada(loadAction);
                list.set(Mpostion,Material);
                Plan plan= model.plan.getValue();
                plan.setPlanToItems(list);
                model.plan.setValue(plan);
                dismiss();
            }
        });


    }


    @Override
    public void deleteButtonClicked(int pos) {
        chosenVehicles.remove(pos);
        chosenVehicleAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void Driverselected(Driver driver) {
        if(chosenVehicle!=null){
            chosenDrivers.add(driver);
            Toast.makeText(getContext(),"Loader:"+driver.getZuphrdrvrName()+" has been assigned to "+
                    chosenVehicle.getVehType(),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(),"Please select a vehicle first",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void VehicleSelected(Vehicle vehicle) {
        chosenDrivers.clear();
        this.chosenVehicle=vehicle;
        loaderAdapter = new LoaderAdapter(driversList,this::Driverselected);
        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loaderList.setAdapter(loaderAdapter);


    }


    public interface OnPositiveClickListener{
        void onPositiveClick(ArrayList<Driver> text, ArrayList<Vehicle> text2);
    }

    public interface OnNegativeClickListener{
        void onNegativeClick();
    }


}



