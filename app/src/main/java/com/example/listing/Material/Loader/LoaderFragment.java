package com.example.listing.Material.Loader;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.CameraButtonClicked;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.FoundButtonClicked;
import com.example.listing.LoadButtonClicked;
import com.example.listing.Material.MaterialAdapter;
import com.example.listing.NoteButtonClicked;
import com.example.listing.PrcButtonClicked;
import com.example.listing.R;
import com.example.listing.UnloadButtonClicked;
import com.example.listing.models.Material;
import com.example.listing.models.MatrialDispatching;
import com.example.listing.models.Plan;
import com.example.listing.models.VechAssignLoader;
import com.example.listing.notes.RedesignedNotesFragment;
import com.fasterxml.jackson.core.Base64Variants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaderFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = -1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static RedesignedNotesFragment notesFragment = null;
    ImageButton btnCapture;
    PlansDataModel model;
    List<VechAssignLoader> Vehassignment = new ArrayList<>();
    VechAssignLoader Vehassign;
    // TODO: Rename and change types of parameters
    private String mParam2, mParam3, mParam4;
    private ArrayList<Material> mParam1 = new ArrayList<>();
    private MaterialAdapter materialAdapter;
    private MatrialDispatching MatrialDispatch;


    public LoaderFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static LoaderFragment newInstance(ArrayList<Material> param1, String param2, String param3, String param4) {
        LoaderFragment fragment = new LoaderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);

        return fragment;
    }

    public void dataChangedDer() {
        materialAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (ArrayList<Material>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);

        }
    }

    private void filter2(String text) {
        ArrayList<Material> filteredList = new ArrayList<>();

        for (Material mat : mParam1) {
            String planName = mat.getZuphrShortxt().toLowerCase();

            Boolean contains = Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(planName).find();
            if (contains) {
                filteredList.add(mat);
            }
        }
        materialAdapter.filterList(filteredList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && requestCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String picString = Base64Variants.getDefaultVariant().encode(byteArray);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        model = new ViewModelProvider(getActivity()).get(PlansDataModel.class);

        View v = inflater.inflate(R.layout.fragment_text, container, false);

        EditText editText1 = v.findViewById(R.id.search_material);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter2(s.toString());
            }
        });


        btnCapture = v.findViewById(R.id.camerabutton);

        //set recyclerview adapter
        RecyclerView rv = v.findViewById(R.id.textrecview);
        TextView planname_tv, vesselname_tv, dest_tv;

        planname_tv = v.findViewById(R.id.plan_tv);
        planname_tv.setText(mParam2);

        vesselname_tv = v.findViewById(R.id.vessel_tv2);
        vesselname_tv.setText(mParam3);

        dest_tv = v.findViewById(R.id.dest_tv);
        dest_tv.setText(mParam4);

        CameraButtonClicked cameraListener = pos -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {

            }
        };
        LoadButtonClicked loadListener = pos -> {

            typemethod(pos, "X", "", "", "");

        };
        UnloadButtonClicked unloadListener = pos -> {
            typemethod(pos, "", "X", "", "");
        };
        FoundButtonClicked foundListener = pos -> {
            typemethod(pos, "", "", "X", "");
        };
        PrcButtonClicked prcListener = pos -> {
            typemethod(pos, "", "", "", "X");

        };

        NoteButtonClicked noteListener = pos -> {


            FragmentManager fragm = getActivity().getSupportFragmentManager();
            materialAdapter.notifyDataSetChanged();
            SharedPreferences preferences = requireActivity().getSharedPreferences(getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
            String Mode = preferences.getString(getResources().getString(R.string.UserMode), "");
            notesFragment = new RedesignedNotesFragment(model.MatrialsList.getValue().get(pos),
                    model.Plans.getValue().get(pos).getZuphrLpid(), "0", Calendar.getInstance().get(Calendar.YEAR) + "",
                    requireActivity().getApplication(), Mode);

            notesFragment.show(fragm, "Note");


        };

//        CameraButtonClicked cameraListener = new MaterialAdapter.cameraClick() {
//            @Override
//            public void cameraButtonClick(int pos) {
//                FragmentManager fm = getChildFragmentManager();
//                fm.beginTransaction().add(new NotesFragment(Integer.parseInt(mParam2.replace("Plan# ","")),mParam1.get(pos).getItemID()),"Comments")
//                        .commit();
//                pPos = pos;
//            }
//
//        };


        materialAdapter = new MaterialAdapter(mParam1, loadListener, unloadListener, prcListener, foundListener, noteListener, cameraListener);
        rv.setAdapter(materialAdapter);


        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);
        // rv.setLayoutAnimation(controller);
        materialAdapter.notifyDataSetChanged();
        // rv.scheduleLayoutAnimation();

        GridLayoutManager grm = new GridLayoutManager(getActivity(), 2);

        grm.offsetChildrenHorizontal(1);
        rv.setLayoutManager(grm);

        return v;
    }

    public void typemethod(int Mpostion, String load, String UNload, String Nfound, String Proc) {


        Material materialParam = model.MatrialsList.getValue().get(Mpostion);
        if (materialParam.getVehAssignList() != null) {
            if (materialParam.getVehAssignList().size() > 0) {

                //get the matarial & plan object and lists

                Vehassignment = new ArrayList<>();

                //go through the matrial vech assign
                for (int i = 0; i < materialParam.getVehAssignList().size(); i++) {

                    Vehassign = new VechAssignLoader(
                            materialParam.getZuphrLpid(), materialParam.getZuphrMjahr(),
                            materialParam.getZuphrMblpo(), materialParam.getVehAssignList().get(i).getZuphrDriverid(),
                            materialParam.getVehAssignList().get(i).getZuphrVehid(),
                            load, UNload, Nfound, Proc, false);

                    Vehassign.AddtoDB(false);
                    materialParam.getVehAssignList().set(i, Vehassign);
                    Vehassignment.add(Vehassign);

                }


//////////////////////////////////////////////////////////////////////////////////////

                //Go through plan items list to populate all VechAssign list for over all update


                //Create object and posting

                Boolean flaglooded = true;
                for (int i = 0; i < Vehassignment.size(); i++) {


                    model.AssignValueLoader(Vehassignment.get(i), this);

                    if (!Vehassignment.get(i).getZuphrLoad().toLowerCase().contains("x") && flaglooded) {
                        flaglooded = false;
                    }
                }

                Plan plan = model.plan.getValue();

                materialParam.setComplete(flaglooded);


                plan.getPlanToItems().set(Mpostion, materialParam);

                List<Plan> planslist = model.Plans.getValue();

                model.plan.setValue(plan);
                for (int i = 0; i < planslist.size(); i++) {
                    if (planslist.get(i).getZuphrLpid().equalsIgnoreCase(plan.getZuphrLpid())) {
                        planslist.set(i, model.plan.getValue());
                    }
                }

                model.Plans.setValue(planslist);


            } else
                Toast.makeText(getContext(), "Not assigend for you", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(getContext(), "Not assigend for you", Toast.LENGTH_SHORT).show();


    }


}

