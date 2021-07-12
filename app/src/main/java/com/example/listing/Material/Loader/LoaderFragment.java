package com.example.listing.Material.Loader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listing.Material.Material;
import com.example.listing.Material.MaterialAdapter;
import com.example.listing.R;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.Plan.Plan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaderFragment extends Fragment {
    private int pPos = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    // TODO: Rename and change types of parameters
    List<Plan> plans;
    private String mParam2, mParam3, mParam4;
    private ArrayList<Material> mParam1 = new ArrayList<>();
    private MaterialAdapter materialAdapter;
    private static Context contexts;
    private static LoaderFragment fragment = null;
    private Boolean isLoad = true;
    ImageButton btnCapture;


    public LoaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * <p>
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment TextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoaderFragment newInstance(ArrayList<Material> param1, String param2, String param3, String param4) {
        LoaderFragment fragment = new LoaderFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);

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

//    public void changeLoading(boolean loadOrNot){
//        isLoad = loadOrNot;
//    }

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
            if (mat.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(mat);
            }
        }
        materialAdapter.filterList(filteredList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_text, container, false);

        EditText editText1 = (EditText) v.findViewById(R.id.search_material);

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


        btnCapture = (ImageButton) v.findViewById(R.id.camerabutton);

        //set recyclerview adapter
        RecyclerView rv = v.findViewById(R.id.textrecview);
        TextView planname_tv, vesselname_tv, dest_tv;

        planname_tv = v.findViewById(R.id.plan_tv);
        planname_tv.setText(mParam2);

        vesselname_tv = v.findViewById(R.id.vessel_tv2);
        vesselname_tv.setText(mParam3);

        dest_tv = v.findViewById(R.id.dest_tv);
        dest_tv.setText(mParam4);


        materialAdapter = new MaterialAdapter(mParam1);
////        myAdapter = detAdapter;

        //animation
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);
        rv.setLayoutAnimation(controller);
        materialAdapter.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();


        // For loading

        if (isLoad) {

            materialAdapter.setCameraListener(new MaterialAdapter.cameraClick() {
                @Override
                public void cameraButtonClick(int pos) {
                    Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cInt, 1);
                    pPos = pos;



                }

            });


            materialAdapter.setUnloadListener(new MaterialAdapter.unloadClick() {
                @Override
                public void unloadButtonClick(int pos) {
                    mParam1.get(pos).setLoaded(false);
                    mParam1.get(pos).setFound((true));
                    materialAdapter.notifyDataSetChanged();
                    //materialAdapter.notifyDataSetChanged();
                    //To update myadapter
                    notifDataChanged();
//                    ((MainActivity) getActivity()).sortBeer();
//                    ((MainActivity) getActivity()).dataChanged();
                    //myAdapter.notifyDataSetChanged();
                }
            });
            /*
            Load button listener
             */
            materialAdapter.setLoadListener(new MaterialAdapter.loadClick() {
                @Override
                public void loadButtonClicked(int pos) {

                    // mParam1.get(pos).setLoaded(true);
                    // mParam1.get(pos).setFound(true);
                    materialAdapter.notifyDataSetChanged();
                    //To update myadapter
                    notifDataChanged();
//                    ((MainActivity) getActivity()).dataChanged();
                }
            });

            /*
            Unload button listener
             */


            /*
            Found button listener
             */
            materialAdapter.setFoundListener(new MaterialAdapter.foundClick() {
                @Override
                public void foundButtonClick(int pos) {
                    mParam1.get(pos).setFound(false);
                    mParam1.get(pos).setLoaded(false);
                    materialAdapter.notifyDataSetChanged();
                    notifDataChanged();
//                    ((MainActivity) getActivity()).dataChanged();
                }
            });
        }

        rv.setAdapter(materialAdapter);
        GridLayoutManager grm = new GridLayoutManager(getActivity(), 2);
        grm.offsetChildrenHorizontal(1);
        rv.setLayoutManager(grm);

           /*
            Title animation
             */
//        TextView tv = v.findViewById(R.id.reqNum_txtfrag);
        ViewGroup vg = v.findViewById(R.id.cont);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            TransitionManager.beginDelayedTransition(vg, fade);
//            tv.setVisibility(View.INVISIBLE);
        }
        //
        //        animFadeIn = AnimationUtils.loadAnimation(contexts,
        //                R.anim.fade_in);
//        tv.setVisibility(View.INVISIBLE);
//        animFadeIn = AnimationUtils.loadAnimation(getActivity()  ,
//                R.anim.fade_in);
////        tv.startAnimation(animSlideDown);
//        tv.startAnimation(animFadeIn);
//        tv.setText(mParam2);


//        Button loadBut = v.findViewById(R.id.load_button);
//        loadBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mParam3.get()
//            }
//        });
        // detAdapter.notifyDataSetChanged();
        return v;
    }

    public void notifDataChanged() {
        FragmentManager fm = getFragmentManager();
        PlanFragment fragm = (PlanFragment) fm.findFragmentById(R.id.constraintLayout4);
        fragm.dataChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
       // Toast.makeText(contexts, "Entered ON Activity Results", Toast.LENGTH_SHORT).show();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //Toast.makeText(contexts, "Entered ON Activity Results OK!", Toast.LENGTH_SHORT).show();
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            mParam1.get(pPos).setBmpImage(bmp);
            materialAdapter.notifyDataSetChanged();


        }
    }




}

