package com.example.listing.Material.Loader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.example.listing.CameraButtonClicked;
import com.example.listing.DataViewModel.Flag;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.FoundButtonClicked;
import com.example.listing.LoadButtonClicked;
import com.example.listing.Material.Material;
import com.example.listing.Material.MaterialAdapter;

import com.example.listing.Material.MaterialAdapter_2;
import com.example.listing.PrcButtonClicked;
import com.example.listing.R;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.Plan.Plan;
import com.example.listing.UnloadButtonClicked;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Material2;
import com.example.listing.models.Plan2;
import com.example.listing.notes.RedesignedNotesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaderFragment extends Fragment  {
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
    //private ArrayList<Material> mParam1 = new ArrayList<>();
    private ArrayList<Material2> mParam1 = new ArrayList<>();
    private MaterialAdapter_2 materialAdapter;
    private static Context contexts;
    private static LoaderFragment fragment = null;
    private Boolean isLoad = true;
    ImageButton btnCapture;
    PlansDataModel model;



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
    public static LoaderFragment newInstance(ArrayList<Material2> param1, String param2, String param3, String param4) {
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
            mParam1 = (ArrayList<Material2>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    private void filter2(String text) {
//        ArrayList<Material2> filteredList = new ArrayList<>();
//
//        for (Material2 mat : mParam1) {
//            if (mat.getZuphrShortxt().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(mat);
//            }
//        }
//        materialAdapter.filterList(filteredList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        model=new ViewModelProvider(getActivity()).get(PlansDataModel.class);

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


        LoadButtonClicked loadListener = new LoadButtonClicked() {
            @Override
            public void loadButtonClick(int pos) {
                Material2 material2= model.MatrialsList.getValue().get(pos);
                List<Material2> list = model.MatrialsList.getValue();
                LoadAction loadAction= material2.getZuphrLoada();
                loadAction.setStatus("loaded");
                list.set(pos,material2);
                Plan2 plan= model.plan.getValue();
                plan.setPlanToItems(list);

                model.plan.setValue(plan);
            }
        };

        UnloadButtonClicked unloadListener = new UnloadButtonClicked() {
            @Override
            public void unloadButtonClicked(int pos) {
                Material2 material2= model.MatrialsList.getValue().get(pos);
                List<Material2> list = model.MatrialsList.getValue();
                LoadAction loadAction= material2.getZuphrLoada();
                loadAction.setStatus("unloaded");
                list.set(pos,material2);
                Plan2 plan= model.plan.getValue();
                plan.setPlanToItems(list);
                model.plan.setValue(plan);
            }
        };

        PrcButtonClicked prcListener = new PrcButtonClicked() {
            @Override
            public void PrcButtonClicked(int pos) {
                Material2 material2= model.MatrialsList.getValue().get(pos);
                List<Material2> list = model.MatrialsList.getValue();
                LoadAction loadAction= material2.getZuphrLoada();
                loadAction.setStatus("processing");
                list.set(pos,material2);
                Plan2 plan= model.plan.getValue();
                plan.setPlanToItems(list);
                model.plan.setValue(plan);


            }
        };

  FoundButtonClicked foundListener = new FoundButtonClicked() {
      @Override
      public void foundButtonClicked(int pos) {
          Material2 material2= model.MatrialsList.getValue().get(pos);
          List<Material2> list = model.MatrialsList.getValue();
          LoadAction loadAction= material2.getZuphrLoada();
          loadAction.setStatus("not found");
          list.set(pos,material2);
          Plan2 plan= model.plan.getValue();
          plan.setPlanToItems(list);
          model.plan.setValue(plan);

      }
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




        materialAdapter = new MaterialAdapter_2(mParam1, loadListener, unloadListener, prcListener, foundListener);
        rv.setAdapter(materialAdapter);
////        myAdapter = detAdapter;

        //animation
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);
        rv.setLayoutAnimation(controller);
        materialAdapter.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();

        GridLayoutManager grm = new GridLayoutManager(getActivity(), 2);
        grm.offsetChildrenHorizontal(1);
        rv.setLayoutManager(grm);

        ViewGroup vg = v.findViewById(R.id.cont);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            TransitionManager.beginDelayedTransition(vg, fade);
        }
        return v;
    }

}