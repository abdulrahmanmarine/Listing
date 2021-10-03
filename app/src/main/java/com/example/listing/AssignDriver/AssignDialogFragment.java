package com.example.listing.AssignDriver;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.listing.Material.Dispatcher.DispatcherFragment;
import com.example.listing.R;
import com.example.listing.Plan.PlanFragment;
import com.fasterxml.jackson.core.Base64Variants;

import java.io.ByteArrayInputStream;

public class AssignDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private OnPositiveClickListener positiveListener;
    private OnNegativeClickListener negativeListener;

    String driv, vehi, name;

    String simage;
    int posit;

    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        int dialogWidth = 800; // specify a value here
        int dialogHeight = 800; // specify a value here


        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        // ... other stuff you want to do in your onStart() method
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        positiveListener = (OnPositiveClickListener) context;
        negativeListener = (OnNegativeClickListener) context;
    }



    public static AssignDialogFragment newInstance(String name,String pic){
        AssignDialogFragment dial = new AssignDialogFragment();

        Bundle args = new Bundle();

        args.putString("name", name);
        args.putString("pic", pic);
        dial.setArguments(args);

        return dial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_spinner, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner vspin = view.findViewById(R.id.vehiclespinner);
        final Spinner dspin = view.findViewById(R.id.driverspinner);
        Button but = view.findViewById(R.id.dialogbut), cancel_but = view.findViewById(R.id.dialog_cancel);

        simage = getArguments().getString("pic");
        name = getArguments().getString("name");

        ImageView pic = view.findViewById(R.id.dialog_image);
        Drawable image = null;
        if(simage.length()> 100) {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64Variants.getDefaultVariant().decode(simage));
            image = BitmapDrawable.createFromStream(
                    stream, "");
            pic.setBackground(image);
        }


        Drawable finalImage = image;
        pic.setOnClickListener(view1 -> {
                    AppCompatActivity activity = (AppCompatActivity) requireContext();
                   // pictureMode myFragment = new pictureMode(finalImage);
                   // activity.getSupportFragmentManager().beginTransaction().add(myFragment,"Picture").commit();


                }
        );

        TextView nom = view.findViewById(R.id.image_name);
        nom.setText(name);


        final ArrayAdapter<CharSequence> driverAdapter = ArrayAdapter.createFromResource(dspin.getContext(), R.array.drivers, R.layout.support_simple_spinner_dropdown_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> vehicleAdapter = ArrayAdapter.createFromResource(vspin.getContext(), R.array.vehicels, R.layout.support_simple_spinner_dropdown_item);
        vehicleAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        getDialog().setTitle("Choose Vehicle and Driver");


        dspin.setAdapter(driverAdapter);
        vspin.setAdapter(vehicleAdapter);



        dspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driv = parent.getItemAtPosition(position).toString();
                dspin.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehi = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DispatcherFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.item_recycler)).dataChangedDer();
//                ((MainActivity) getActivity()).dataChanged();
                notifDataChanged();

                positiveListener.onPositiveClick(driv, vehi);
            }
        });

        cancel_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) getActivity()).dataChanged();
                notifDataChanged();
                dismiss();
                //negativeListener.onNegativeClick();
            }
        });
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


