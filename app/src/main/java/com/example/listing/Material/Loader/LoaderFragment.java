package com.example.listing.Material.Loader;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.listing.CameraButtonClicked;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.FoundButtonClicked;
import com.example.listing.LoadButtonClicked;

import com.example.listing.Material.MaterialAdapter;
import com.example.listing.NoteButtonClicked;
import com.example.listing.PrcButtonClicked;
import com.example.listing.R;
import com.example.listing.UnloadButtonClicked;
import com.example.listing.Utils.Loginsession;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;
import com.example.listing.models.VehAssign;
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
public class LoaderFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    static final int REQUEST_IMAGE_CAPTURE = -1;


    // TODO: Rename and change types of parameters
    private String mParam2, mParam3, mParam4;
    private ArrayList<Material> mParam1 = new ArrayList<>();
    private MaterialAdapter materialAdapter;
    private static RedesignedNotesFragment notesFragment= null;
    ImageButton btnCapture;
    PlansDataModel model;



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

        for(Material mat : mParam1){
            String planName = mat.getZuphrShortxt().toLowerCase();

            Boolean contains = Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(planName).find();
            if(contains){
                filteredList.add(mat);
            }
        }
        materialAdapter.filterList(filteredList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE&& requestCode == Activity.RESULT_OK){
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

        model=new ViewModelProvider(getActivity()).get(PlansDataModel.class);

        View v = inflater.inflate(R.layout.fragment_text, container, false);

        EditText editText1 =v.findViewById(R.id.search_material);

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




        btnCapture =v.findViewById(R.id.camerabutton);

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
            try{
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE );
            }catch (ActivityNotFoundException e){

            }
        };
        LoadButtonClicked loadListener = pos -> {
            Material Material= model.MatrialsList.getValue().get(pos);
            VehAssign vehAssign=new VehAssign(Material.getZuphrLpid(),Material.getZuphrMjahr(),
                    Material.getZuphrMblpo(),Material.getZuphrStgid(),Material.getZuphrMatnr(),
                    Material.getZuphrReqid(),Material.getZuphrReqitm(),Material.getZuphrShortxt(),
                    Material.getZuphrDescrip(),Material.getZuphrOffshore(),Loginsession.getInstance().getUser().UserId,
                    "","X","","","");
            Material.getVehAssignList().clear();
            for(int i=0;i<Material.getVehAssignList().size();i++){

                if(Material.getVehAssignList().get(i).getZuphrDriverid().equalsIgnoreCase(Loginsession.getInstance().getUser().UserId)){
                 vehAssign=Material.getVehAssignList().get(i);
                    break;
                }

            }


            model.AssignValueLoader(vehAssign);
            Material.getVehAssignList().add(vehAssign);
            boolean FLAG=true;
            for(int i=0;i<Material.getVehAssignList().size();i++){
                if(!Material.getVehAssignList().get(i).getZuphrLoad().equalsIgnoreCase("x")) {
                    FLAG = false;
                }
            }
            Material.setComplete(FLAG);
            List<Material> list = model.MatrialsList.getValue();
            list.set(pos,Material);
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


        };
        UnloadButtonClicked unloadListener = pos -> {
            Material Material= model.MatrialsList.getValue().get(pos);
            VehAssign vehAssign=new VehAssign(Material.getZuphrLpid(),Material.getZuphrMjahr(),
                    Material.getZuphrMblpo(),Material.getZuphrStgid(),Material.getZuphrMatnr(),
                    Material.getZuphrReqid(),Material.getZuphrReqitm(),Material.getZuphrShortxt(),
                    Material.getZuphrDescrip(),Material.getZuphrOffshore(),Loginsession.getInstance().getUser().UserId,
                    "","","x","","");
            Material.getVehAssignList().clear();
            for(int i=0;i<Material.getVehAssignList().size();i++){

                if(Material.getVehAssignList().get(i).getZuphrDriverid().equalsIgnoreCase(Loginsession.getInstance().getUser().UserId)){
                    vehAssign=Material.getVehAssignList().get(i);
                    break;
                }

            }
            model.AssignValueLoader(vehAssign);
            Material.getVehAssignList().add(vehAssign);
            boolean FLAG=true;
            for(int i=0;i<Material.getVehAssignList().size();i++){
                if(!Material.getVehAssignList().get(i).getZuphrLoad().equalsIgnoreCase("x")) {
                    FLAG = false;
                }
            }
            Material.setComplete(FLAG);
            List<Material> list = model.MatrialsList.getValue();
            list.set(pos,Material);
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

        };
        FoundButtonClicked foundListener = pos -> {
            Material Material= model.MatrialsList.getValue().get(pos);
            VehAssign vehAssign=new VehAssign(Material.getZuphrLpid(),Material.getZuphrMjahr(),
                    Material.getZuphrMblpo(),Material.getZuphrStgid(),Material.getZuphrMatnr(),
                    Material.getZuphrReqid(),Material.getZuphrReqitm(),Material.getZuphrShortxt(),
                    Material.getZuphrDescrip(),Material.getZuphrOffshore(),Loginsession.getInstance().getUser().UserId,
                    "","","","X","");
            Material.getVehAssignList().clear();
            for(int i=0;i<Material.getVehAssignList().size();i++){

                if(Material.getVehAssignList().get(i).getZuphrDriverid().equalsIgnoreCase(Loginsession.getInstance().getUser().UserId)){
                    vehAssign=Material.getVehAssignList().get(i);
                    break;
                }

            }
            model.AssignValueLoader(vehAssign);
            Material.getVehAssignList().add(vehAssign);
            boolean FLAG=true;
            for(int i=0;i<Material.getVehAssignList().size();i++){
                if(!Material.getVehAssignList().get(i).getZuphrLoad().equalsIgnoreCase("x")) {
                    FLAG = false;
                }
            }
            Material.setComplete(FLAG);
            List<Material> list = model.MatrialsList.getValue();
            list.set(pos,Material);
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

        };
        PrcButtonClicked prcListener = pos -> {
            Material Material= model.MatrialsList.getValue().get(pos);
            VehAssign vehAssign=new VehAssign(Material.getZuphrLpid(),Material.getZuphrMjahr(),
                    Material.getZuphrMblpo(),Material.getZuphrStgid(),Material.getZuphrMatnr(),
                    Material.getZuphrReqid(),Material.getZuphrReqitm(),Material.getZuphrShortxt(),
                    Material.getZuphrDescrip(),Material.getZuphrOffshore(),Loginsession.getInstance().getUser().UserId,
                    "","","","","x");
            Material.getVehAssignList().clear();
            for(int i=0;i<Material.getVehAssignList().size();i++){

                if(Material.getVehAssignList().get(i).getZuphrDriverid().equalsIgnoreCase(Loginsession.getInstance().getUser().UserId)){
                    vehAssign=Material.getVehAssignList().get(i);
                    break;
                }

            }
            model.AssignValueLoader(vehAssign);
            Material.getVehAssignList().add(vehAssign);
            boolean FLAG=true;
            for(int i=0;i<Material.getVehAssignList().size();i++){
                if(!Material.getVehAssignList().get(i).getZuphrLoad().equalsIgnoreCase("x")) {
                    FLAG = false;
                }
            }
            Material.setComplete(FLAG);
            List<Material> list = model.MatrialsList.getValue();
            list.set(pos,Material);
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


        };

  NoteButtonClicked noteListener = pos -> {


      FragmentManager fragm = getActivity().getSupportFragmentManager();
      materialAdapter.notifyDataSetChanged();
      SharedPreferences preferences = requireActivity().getSharedPreferences(getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
      String Mode = preferences.getString(getResources().getString(R.string.UserMode), "");

      Log.i("notes",model.MatrialsList.getValue().get(pos).getNotes().size()+"");
      notesFragment = new RedesignedNotesFragment( model.MatrialsList.getValue().get(pos),
              model.Plans.getValue().get(pos).getZuphrLpid(), "0", Calendar.getInstance().get(Calendar.YEAR) + "",
              requireActivity().getApplication(),Mode);

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
            ViewGroup vg = v.findViewById(R.id.cont);

//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                Fade fade = new Fade();
//                TransitionManager.beginDelayedTransition(vg, fade);
//
//        }

        //animation
//        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation);
//        rv.setLayoutAnimation(controller);
//        materialAdapter.notifyDataSetChanged();
//        rv.scheduleLayoutAnimation();
//
//        GridLayoutManager grm = new GridLayoutManager(getActivity(), 2);
//        grm.offsetChildrenHorizontal(1);
//        rv.setLayoutManager(grm);
//
//        ViewGroup vg = v.findViewById(R.id.cont);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            Fade fade = new Fade();
//            TransitionManager.beginDelayedTransition(vg, fade);
//        }
        return v;
    }

}

