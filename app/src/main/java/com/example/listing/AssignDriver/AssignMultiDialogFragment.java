package com.example.listing.AssignDriver;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.listing.Kotlin.pictureMode;
import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.VehicleDeleteButtonClicked;
import com.example.listing.Material.Dispatcher.DispatcherFragment;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.R;
import com.example.listing.models.Driver;
import com.example.listing.models.Material2;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;

public class AssignMultiDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private OnPositiveClickListener positiveListener;
    private OnNegativeClickListener negativeListener;



    //material object
    private static final String MATERIAL_2 = "materialParam";
    private ArrayList<Driver> driversList = new ArrayList<>();
    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private Material2 materialParam;

    private LoaderAdapter loaderAdapter;
    private VehicleAdapter vehicleAdapter;

    private ArrayList<Vehicle> chosenVehicles = new ArrayList<>();
    private ChosenVehicleAdapter chosenVehicleAdapter;

    private ArrayList<Driver> chosenDrivers = new ArrayList<>();
    private ChosenDriverAdapter chosenDriverAdapter;


    String simage;


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

    public static AssignMultiDialogFragment newInstance(Material2 materialParam){
        AssignMultiDialogFragment fragment = new AssignMultiDialogFragment();
        Bundle args = new Bundle();

        args.putSerializable(MATERIAL_2, materialParam);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assign_spinner, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClickEvent(view);

        VehicleDeleteButtonClicked vehicleDeleteButtonClicked = new VehicleDeleteButtonClicked() {
            @Override
            public void deleteButtonClicked(int pos) {
                chosenVehicles.remove(pos);
                int newPos = pos;
                chosenVehicleAdapter.notifyDataSetChanged();
                chosenVehicleAdapter.notifyItemRemoved(newPos);
                chosenVehicleAdapter.notifyItemRangeChanged(newPos, chosenVehicles.size());
            }
        };

        DriverDeleteButtonClicked driverDeleteButtonClicked = new DriverDeleteButtonClicked() {
            @Override
            public void deleteButtonClicked(int pos) {
                chosenDrivers.remove(pos);
                int newPos = pos;
                chosenDriverAdapter.notifyDataSetChanged();
                chosenDriverAdapter.notifyItemRemoved(newPos);
                chosenDriverAdapter.notifyItemRangeChanged(newPos, chosenVehicles.size());
            }
        };



        //add new pair button
        Button addBut = view.findViewById(R.id.add_btn), cancel_but = view.findViewById(R.id.dialog_cancel);
        //Loader recview
        RecyclerView loaderList = view.findViewById(R.id.loader_list);
        //vehicle recview
        RecyclerView vehicleList = view.findViewById(R.id.vehicle_list);
        //pair recview
        RecyclerView chosenVehicleList = view.findViewById(R.id.vehicle_chosen_list);
        //chosen drivers list
        RecyclerView chosenDriverList = view.findViewById(R.id.driver_chosen_list);
        //done button
        Button doneBut = view.findViewById(R.id.done_btn);
        //search edit text for loader
        EditText searchLoader = view.findViewById(R.id.search_loader);
        //search edir text for vehicle
        EditText searchVehicle = view.findViewById(R.id.search_vehicle);
        //Text view for material name
        TextView materialName = view.findViewById(R.id.assign_image_tv);

        RadioButton radioButton = view.findViewById(R.id.radioButton);



        Driver driver1 = new Driver("3", "Abdul", "Heavy Vehicle Driving",
                "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
        Driver driver2 = new Driver("2", "Ahmed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");

        Vehicle vehicle1 = new Vehicle("3","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456");
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456");

        driversList.add(driver1);
        driversList.add(driver2);
        vehiclesList.add(vehicle1);
        vehiclesList.add(vehicle2);


        materialParam = (Material2) getArguments().getSerializable(MATERIAL_2);
        Log.i("Materialparamtest", ""+materialParam.getDrivers().get(0).getZuphrdrvrName());

        ArrayList<Driver> drivers = (ArrayList<Driver>) materialParam.getDrivers();
        chosenVehicles = (ArrayList<Vehicle>) materialParam.getVehicles();


        //setting adapter for loaders/drivers recycler
        loaderAdapter = new LoaderAdapter(driversList);
        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loaderList.setAdapter(loaderAdapter);

//        //setting adapter for vehicle recycler
        vehicleAdapter = new VehicleAdapter(vehiclesList);
        vehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        vehicleList.setAdapter(vehicleAdapter);

        chosenVehicleAdapter = new ChosenVehicleAdapter(chosenVehicles, vehicleDeleteButtonClicked);
        chosenVehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        chosenVehicleList.setAdapter(chosenVehicleAdapter);

        chosenDriverAdapter = new ChosenDriverAdapter(chosenDrivers, driverDeleteButtonClicked);
        chosenDriverList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        chosenDriverList.setAdapter(chosenDriverAdapter);


/* UNCOMMENT LATER
        //setting adapter for pair recycler
        pairAdapter = new PairAdapter(mParam5);
        pairList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        pairList.setAdapter(pairAdapter);

        pairAdapter.setDeleteListener(new PairAdapter.deleteClick() {
            @Override
            public void DeleteButtonClicked(int pos) {
                mParam5.remove(pos);
                int newPos = pos;
                pairAdapter.notifyDataSetChanged();
                pairAdapter.notifyItemRemoved(newPos);
                pairAdapter.notifyItemRangeChanged(newPos, mParam5.size());
            }
        });
*/
        searchLoader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterLoader(s.toString());
            }
        });

        searchVehicle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filterVehicle(s.toString());
            }
        });

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chosenDrivers.add(loaderAdapter.getSelectedItem());
                chosenDriverAdapter.notifyDataSetChanged();

                chosenVehicles.add(vehicleAdapter.getSelectedItem());
                chosenVehicleAdapter.notifyDataSetChanged();
            }
        });


//FOR IMAGE

/*
        ImageView pic = view.findViewById(R.id.assign_image);
        Drawable image = null;
        if(mParam2.length()> 100) {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64Variants.getDefaultVariant().decode(mParam2));
            image = BitmapDrawable.createFromStream(
                    stream, "");
            pic.setBackground(image);
        }


        Drawable finalImage = image;
        pic.setOnClickListener(view1 -> {
                    AppCompatActivity activity = (AppCompatActivity) requireContext();
                    pictureMode myFragment = new pictureMode(finalImage);
                    activity.getSupportFragmentManager().beginTransaction().add(myFragment,"Picture").commit();
                }
        );
*/
        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                ((DispatcherFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.item_recycler)).dataChangedDer();
////                ((MainActivity) getActivity()).dataChanged();
//                notifDataChanged();
//
//                //TODO change to be list of drivers and list of vehicles to send data from fragment to main activity
//                positiveListener.onPositiveClick(driv, vehi);
            }
        });




//        cancel_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ((MainActivity) getActivity()).dataChanged();
//                notifDataChanged();
//                dismiss();
//                //negativeListener.onNegativeClick();
//            }
//        });
    }


    private void filterVehicle(String text) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();

        for(Vehicle vehicle : vehiclesList){
            if(vehicle.getVehType().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(vehicle);
            }
        }
        vehicleAdapter.filterList(filteredList);
    }

    private void filterLoader(String text){
        ArrayList<Driver> filteredList = new ArrayList<>();

        for (Driver load : driversList) {
            if (load.getZuphrdrvrName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(load);
            }
        }
        loaderAdapter.filterList(filteredList);
    }

    private void onClickEvent(View view) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnPositiveClickListener{
        void onPositiveClick(String text, String text2);
    }

    public interface OnNegativeClickListener{
        void onNegativeClick();
    }

    public void notifDataChanged(){
        FragmentManager fm = getFragmentManager();
        FragmentManager fm2 = getFragmentManager();

        PlanFragment fragm = (PlanFragment) fm.findFragmentById(R.id.constraintLayout4);
        DispatcherFragment fragm2 = (DispatcherFragment) fm2.findFragmentById(R.id.item_recycler);
        fragm.dataChanged();
        fragm2.dataChangedDer();
    }

}



