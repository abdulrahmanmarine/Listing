package com.example.listing.Manual_Assigment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.DriverDeleteButtonClicked;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.R;
import com.example.listing.models.Driver;
import com.example.listing.models.Material;
import com.example.listing.models.MatrialDispatching;
import com.example.listing.models.Plan;
import com.example.listing.models.VehAssign;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import com.example.listing.Kotlin.pictureMode;

public class Manual_AssignMultiDialogFragment extends DialogFragment
        implements DriverDeleteButtonClicked, DriverSelected, AdapterView.OnItemSelectedListener, VehicleSelected {

    private static final String MATERIAL_2 = "materialParam";
    private static int Mpostion;
    PlansDataModel model;
    RecyclerView loaderList;
    List<Vehicle> chosenVehicles;
    ImageView imageView;
    Vehicle chosenVehicle;
    List<VehAssign> Vehassignment = new ArrayList<>();
    VehAssign Vehassign;
    private Material materialParam;
    private LoaderAdapter loaderAdapter;
    private VehicleAdapter vehicleAdapter;
    private MatrialDispatching MatrialDispatch;
    private Manual_Assignment_Adapter chosenVehicleAdapter;
    private ArrayList<Driver> chosenDrivers = new ArrayList<>();
    private ArrayList<Driver> notFoundDrivers = new ArrayList<>();


    public Manual_AssignMultiDialogFragment() {

    }

    public static Manual_AssignMultiDialogFragment newInstance(int position, Material materialParam) {
        Manual_AssignMultiDialogFragment fragment = new Manual_AssignMultiDialogFragment();
        Bundle args = new Bundle();

        args.putSerializable(MATERIAL_2, materialParam);
        Mpostion = position;
        fragment.setArguments(args);
        return fragment;
    }

    public static <Driver> List<Driver> removeDuplicates(List<Driver> oldList) {
        List<Driver> newList = new ArrayList<>();

        for (Driver element : oldList) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        return newList;
    }

    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null)
            return;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getDialog().getWindow().setLayout((5 * width) / 6, (5 * height) / 5);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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
        model = new ViewModelProvider(getActivity()).get(PlansDataModel.class);


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

        //Close Button
        ImageView closeBut = view.findViewById(R.id.but_close);

        //search edit text for loader
        EditText searchLoader = view.findViewById(R.id.search_loader);
        //search edir text for vehicle
        EditText searchVehicle = view.findViewById(R.id.search_vehicle);
        //Text view for material name
        TextView materialName = view.findViewById(R.id.material_tv);
        imageView = view.findViewById(R.id.material_image);


        materialParam = model.MatrialsList.getValue().get(Mpostion);


        Bitmap decodedByte;
        materialName.setText(materialParam.getZuphrShortxt());
        if (materialParam.getZuphrContents().length() > 100) {
            String img = materialParam.getZuphrContents().replace("data:image/jpeg;base64,", "");
            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

        chosenVehicles = materialParam.getVehicles();


        //setting adapter for loaders/drivers recycler

        //setting adapter for vehicle recycler
        model.MastervehiclesList.observe(getViewLifecycleOwner(), vehiclesList -> {
            vehicleAdapter = new VehicleAdapter((ArrayList<Vehicle>) vehiclesList, this::VehicleSelected);
            vehicleList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            vehicleList.setAdapter(vehicleAdapter);

        });


        chosenVehicleAdapter = new Manual_Assignment_Adapter(chosenVehicles, this::deleteButtonClicked, getContext());
        Choosenpair.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        Choosenpair.setAdapter(chosenVehicleAdapter);

        addBut.setOnClickListener(v -> {

            if (chosenVehicle.getLoaders().size() > 0) {

                ArrayList<Vehicle> vehiclesList = (ArrayList<Vehicle>) model.MastervehiclesList.getValue();
                Boolean found = null;

                if (chosenVehicles.contains(chosenVehicle)) {
                    int chosenVehIndex = chosenVehicles.indexOf(chosenVehicle);
                    removeDuplicates(chosenVehicle.getLoaders());
                    chosenVehicles.get(chosenVehIndex).getLoaders().removeAll((Collection<?>) chosenVehicle.getLoaders());
                    chosenVehicles.get(chosenVehIndex).getLoaders().addAll(chosenVehicle.getLoaders());

                } else {
                    chosenVehicles.add(chosenVehicle);
                }
                for (int j = 0; j < chosenVehicles.size(); j++) {

                    if (chosenVehicle.getVehType().equals(chosenVehicles.get(j).getVehType())) {
                        for (int c = 0; c < chosenVehicle.getLoaders().size(); c++) {
                            if (!chosenVehicles.get(j).getLoaders().contains(chosenVehicle.getLoaders().get(c))) {
                                notFoundDrivers.add(chosenVehicle.getLoaders().get(c));
                            }
                        }

                    }

                }
                for (int i = 0; i < vehiclesList.size(); i++) {
                    if (chosenVehicle.equals(vehiclesList.get(i))) {
                        vehiclesList.remove(i);
                        model.MastervehiclesList.setValue(vehiclesList);
                        loaderAdapter = new LoaderAdapter((ArrayList<Driver>) model.MasterdriversList.getValue(), this::Driverselected);
                        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        loaderList.setAdapter(loaderAdapter);
                        model.MastervehiclesList.setValue(vehiclesList);
                        chosenVehicleAdapter.notifyDataSetChanged();

                        break;
                    }
                }

                chosenVehicleAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(getContext(), "Assign drivers to the vehicle first", Toast.LENGTH_SHORT).show();
            }


        });


        doneBut.setOnClickListener(v -> {
            List<Material> list = model.MatrialsList.getValue();

            list.set(Mpostion, materialParam);
            Plan plan = model.plan.getValue();
            plan.setPlanToItems(list);
            model.plan.setValue(plan);
            List<Plan> plans = model.Plans.getValue();
            notifDataAddChanged();


            for (int i = 0; i < plan.getPlanToItems().get(Mpostion).getVehicles().size(); i++) {
                for (int j = 0; j < plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getLoaders().size(); j++) {
                    Vehassign = new VehAssign(
                            materialParam.getZuphrLpid(), materialParam.getZuphrMjahr(),
                            materialParam.getZuphrMblpo(), materialParam.getZuphrStgid(),
                            materialParam.getZuphrMatnr(), materialParam.getZuphrReqid(),
                            materialParam.getZuphrReqitm(), materialParam.getZuphrShortxt(),
                            materialParam.getZuphrDescrip(), materialParam.getZuphrOffshore(),
                            plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getLoaders().get(j).getZuphrDriverid(),
                            plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getLoaders().get(j).getZuphrdrvrName(),
                            plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getVehid(),
                            plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getVehType(),
                            "X", "", "", "");


                    Vehassignment.add(Vehassign);
                }

                Vehassign.setZuphrVehid(plan.getPlanToItems().get(Mpostion).getVehicles().get(i).getVehid());


            }

            MatrialDispatch = new MatrialDispatching(plan.getZuphrLpid(), "", Vehassignment);
            model.AssignValue(MatrialDispatch, this);
            dismiss();
        });

        closeBut.setOnClickListener(v -> dismiss());

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
        if (chosenVehicle != null) {
            List<Driver> loaders = new ArrayList<>();

            if (chosenVehicle.getLoaders() != null)
                loaders = chosenVehicle.getLoaders();

            loaders.add(driver);
            chosenVehicle.setLoaders(loaders);

        } else {
            Toast.makeText(getContext(), "Please select a vehicle first", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void VehicleSelected(Vehicle vehicle) {
        chosenDrivers.clear();
        chosenVehicle = vehicle;
        loaderAdapter = new LoaderAdapter((ArrayList<Driver>) model.MasterdriversList.getValue(), this::Driverselected);
        loaderList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loaderList.setAdapter(loaderAdapter);


    }

    public void dataChanged() {
        chosenVehicleAdapter.notifyDataSetChanged();
    }

    public void notifDataAddChanged() {
        FragmentManager fm = getFragmentManager();
        PlanFragment fragm = (PlanFragment) fm.findFragmentById(R.id.mainlayout);
        fragm.dataChanged();
    }


}
