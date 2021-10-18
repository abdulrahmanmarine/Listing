package com.example.listing.AssignDriver;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

public class Configured_AssignMultiDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static int Mpostion;
    private OnPositiveClickListener positiveListener;
    private OnNegativeClickListener negativeListener;

    //material object
    private static final String MATERIAL_2 = "materialParam";
    private ArrayList<Driver> driversList = new ArrayList<>();
    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private Material materialParam;
    PlansDataModel model;

    private LoaderAdapter loaderAdapter;
    private VehicleAdapter vehicleAdapter;

    private ArrayList<Vehicle> chosenVehicles = new ArrayList<>();
    private ChosenVehicleAdapter chosenVehicleAdapter;

    private ArrayList<Vehicle> configureVehicles = new ArrayList<>();
    private ConfiguredLoaderAdapter configuredLoaderAdapter;

    private ArrayList<Driver> chosenDrivers = new ArrayList<>();
    private ChosenDriverAdapter chosenDriverAdapter;

    private ChosenDriverCardAdapter chosenDriverCardAdapter;

    private ConfiguredChosenAdapter configuredChosenAdapter;


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
        onClickEvent(view);

        model=new ViewModelProvider(getActivity()).get(PlansDataModel.class);

        AddButtonClicked addButtonClicked = new AddButtonClicked() {
            @Override
            public void addButtonClicked(int pos) {
                chosenVehicles.add(configureVehicles.get(pos));
                chosenDrivers.addAll(configureVehicles.get(pos).getLoaders());
                configuredChosenAdapter.notifyDataSetChanged();
                Log.i("addtest", configuredLoaderAdapter.getSelectedItem().getVehType() +" ");
            }
        };

        VehicleDeleteButtonClicked vehicleDeleteButtonClicked2 = new VehicleDeleteButtonClicked() {
            @Override
            public void deleteButtonClicked(int pos) {
                chosenVehicles.remove(pos);
                int newPos = pos;
                configuredChosenAdapter.notifyDataSetChanged();
                configuredChosenAdapter.notifyItemRemoved(newPos);
                configuredChosenAdapter.notifyItemRangeChanged(newPos, chosenVehicles.size());
            }
        };


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
                chosenDriverAdapter.notifyItemRangeChanged(newPos, chosenDrivers.size());
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
        Button doneBut = view.findViewById(R.id.complete_btn);
//        //search edit text for loader
//        EditText searchLoader = view.findViewById(R.id.search_loader);
//        //search edir text for vehicle
//        EditText searchVehicle = view.findViewById(R.id.search_vehicle);
        //Text view for material name
        TextView materialName = view.findViewById(R.id.material_tv);
        //Recyclerview for configured list
        RecyclerView configuredList = view.findViewById(R.id.configured_loader_list);
        //textview for truck name
        TextView vehicleName = view.findViewById(R.id.vehicle_list_name);
        //Chosen List
        RecyclerView configuredChosenList = view.findViewById(R.id.chosen_list);



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



        driversList.add(driver1);
        driversList.add(driver2);
        driversList.add(driver3);
        driversList.add(driver4);
        driversList.add(driver5);
        driversList.add(driver6);

        Vehicle vehicle1 = new Vehicle("3","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", driversList);
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

        configureVehicles = vehiclesList;



        materialParam = (Material) getArguments().getSerializable(MATERIAL_2);


        //setting adapter for chosen vehicle recycler
        configuredLoaderAdapter = new ConfiguredLoaderAdapter(configureVehicles, addButtonClicked, getContext());
        configuredList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        configuredList.setAdapter(configuredLoaderAdapter);

        materialName.setText(materialParam.getZuphrLpid());




        ArrayList<Driver> drivers = (ArrayList<Driver>) materialParam.getDrivers();
        chosenVehicles = (ArrayList<Vehicle>) materialParam.getVehicles();
        chosenDrivers = (ArrayList<Driver>) materialParam.getDrivers();

        configuredChosenAdapter = new ConfiguredChosenAdapter(chosenVehicles, vehicleDeleteButtonClicked2);
        configuredChosenList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        configuredChosenList.setAdapter(configuredChosenAdapter);

/* WORKING PART

        //setting adapter for loaders/drivers recycler
        loaderAdapter = new LoaderAdapter(driversList);
        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loaderList.setAdapter(loaderAdapter);

        //setting adapter for vehicle recycler
        vehicleAdapter = new VehicleAdapter(vehiclesList);
        vehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        vehicleList.setAdapter(vehicleAdapter);




        chosenVehicleAdapter = new ChosenVehicleAdapter(chosenVehicles, vehicleDeleteButtonClicked);
        chosenVehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        chosenVehicleList.setAdapter(chosenVehicleAdapter);

        //setting adapter for chosen drivers recycler
        chosenDriverAdapter = new ChosenDriverAdapter(chosenDrivers, driverDeleteButtonClicked);
        chosenDriverList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        chosenDriverList.setAdapter(chosenDriverAdapter);
*/

/* UNCOMMENT LATER FOR PAIRS
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

/* WORKING PART
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loaderAdapter.getSelectedItem() != null){
                    chosenDrivers.add(loaderAdapter.getSelectedItem());
                    chosenDriverAdapter.notifyDataSetChanged();
                }

                if(vehicleAdapter.getSelectedItem() != null){
                    chosenVehicles.add(vehicleAdapter.getSelectedItem());
                    chosenVehicleAdapter.notifyDataSetChanged();
                }
            }
        });

*/
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
                materialParam.setDrivers(chosenDrivers);
                materialParam.setVehicles(chosenVehicles);

               Material Material= model.MatrialsList.getValue().get(Mpostion);
               Log.i("matposition", Mpostion+"");
                List<Material> list =model.MatrialsList.getValue();
                LoadAction loadAction=Material.getZuphrLoada();
                loadAction.setDriver(chosenDrivers);
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
        void onPositiveClick(ArrayList<Driver> text, ArrayList<Vehicle> text2);
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



