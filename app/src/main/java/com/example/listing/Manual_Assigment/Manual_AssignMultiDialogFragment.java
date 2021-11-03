package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.listing.Kotlin.pictureMode;
import com.example.listing.AddButtonClicked;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.Kotlin.Dispatcher;
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

    private Material materialParam;
    PlansDataModel model;
    DriverSelected driverSelected;
    RecyclerView loaderList;
    List<Vehicle> chosenVehicles;
    ImageView imageView;
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
        imageView=view.findViewById(R.id.assign_image);




         model.getVechiles();

        materialParam = (Material) getArguments().getSerializable(MATERIAL_2);


        Bitmap decodedByte = null;
        if(materialParam.getZuphrContents().length()> 100) {
            String img =materialParam.getZuphrContents().replace("data:image/jpeg;base64,","");
            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

        chosenVehicles  = (ArrayList<Vehicle>) materialParam.getVehicles();


        //setting adapter for loaders/drivers recycler

        //setting adapter for vehicle recycler
        model.MastervehiclesList.observe(getViewLifecycleOwner(),vehiclesList->{
            vehicleAdapter = new VehicleAdapter((ArrayList<Vehicle>) vehiclesList,this::VehicleSelected);
            vehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            vehicleList.setAdapter(vehicleAdapter);

        });


        chosenVehicleAdapter = new Manual_Assignment_Adapter(chosenVehicles,this::deleteButtonClicked,getContext());
        Choosenpair.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        Choosenpair.setAdapter(chosenVehicleAdapter);

        addBut.setOnClickListener(v -> {

            chosenVehicle.setLoaders(chosenDrivers);
            if(chosenVehicle.getLoaders().size()>0){
                chosenVehicles.add(chosenVehicle);
                 ArrayList<Vehicle> vehiclesList = (ArrayList<Vehicle>) model.MastervehiclesList.getValue();

                for(int i=0 ;i<vehiclesList.size();i++){
                    if(chosenVehicle.equals(vehiclesList.get(0))){
                        vehiclesList.remove(i);
                        model.MastervehiclesList.setValue(vehiclesList);

                        loaderAdapter = new LoaderAdapter((ArrayList<Driver>) model.MasterdriversList.getValue(),this::Driverselected);
                        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        loaderList.setAdapter(loaderAdapter);

                        break;
                    }
                }

                chosenVehicleAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getContext(),"Assign drivers to the vehicle first",Toast.LENGTH_SHORT).show();
            }

        });


        doneBut.setOnClickListener(v -> {

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
            List<Plan> plans=model.Plans.getValue();
            for(int i=0;i<model.Plans.getValue().size();i++){
                if(model.plan.getValue().getZuphrLpid().equals(model.Plans.getValue().get(i).getZuphrLpid())){
                    plans.set(i,model.plan.getValue());
                    model.Plans.setValue(plans);
                }
            }
            dismiss();
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
        loaderAdapter = new LoaderAdapter((ArrayList<Driver>) model.MasterdriversList.getValue(),this::Driverselected);
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



