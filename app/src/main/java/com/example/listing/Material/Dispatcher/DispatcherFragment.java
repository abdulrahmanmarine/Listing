package com.example.listing.Material.Dispatcher;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.AddButtonClicked;
import com.example.listing.AssignDialog_Configured.DriverAdapter;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.Kotlin.Dispatcher;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.R;
import com.example.listing.models.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispatcherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispatcherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static Bundle mBundleRecView;
    private static DispatcherFragment fragment = null;
    RecyclerView rv;
    Parcelable state;
    PlansDataModel model;
    ImageButton btnCapture;
    // TODO: Rename and change types of parameters
    private String mParam2, mParam3, mParam4;
    private List<Material> mParam1 = new ArrayList<>();
    private DriverAdapter driverAdapter;
    private Boolean isLoad = true;


    public DispatcherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * <p>
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DispatcherFragment newInstance(ArrayList<Material> param1, String param2, String param3, String param4) {
        DispatcherFragment fragment = new DispatcherFragment();
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
        driverAdapter.notifyDataSetChanged();
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

        model = new ViewModelProvider(getActivity()).get(PlansDataModel.class);
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
        driverAdapter.filterList(filteredList);
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


        model.getdrivers(this);
        model.getVechiles(this);

        Log.i("destnation",model.plan.getValue().getZuphrOffshore()+"");

        model.MatrialsList.observe(getViewLifecycleOwner(), materialList -> {
            String x = null;
            AddButtonClicked addListener = pos -> {

                ((Dispatcher) getActivity()).showAssignDialog(pos, materialList.get(pos));
                driverAdapter.notifyDataSetChanged();
                notifDataAddChanged();

            };

            driverAdapter = new DriverAdapter(materialList, addListener, getContext());


            //animation
            final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);
            rv.setLayoutAnimation(controller);
            driverAdapter.notifyDataSetChanged();
            rv.scheduleLayoutAnimation();
            rv.setAdapter(driverAdapter);


//            driverAdapter.updateChosen();
        });


//        //FOR ASSIGN

//            driverAdapter.setAddListener(new DriverAdapter.addClick() {
//                @Override
//                public void addButtonClick(int pos) {
//                    ((Dispatcher) getActivity()).showAssignDialog(pos);
//
//                    driverAdapter.notifyDataSetChanged();
//                    notifDataAddChanged();
//                }
//            });


        // For loading
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

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

    public void notifDataAddChanged() {
        FragmentManager fm = getFragmentManager();
        PlanFragment fragm = (PlanFragment) fm.findFragmentById(R.id.mainlayout);
        fragm.dataChanged();
    }


}