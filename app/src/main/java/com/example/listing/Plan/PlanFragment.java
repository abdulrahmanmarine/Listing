package com.example.listing.Plan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.listing.DataViewModel.Flag;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.PlanClickListener;
import com.example.listing.R;
import com.example.listing.models.Plan2;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.


 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PlanClickListener listener;
    List<Plan2> plans = new ArrayList<>();

    int currentpos;


    // TODO: Rename and change types of parameters
    private ArrayList<Plan2> mParam1 = new ArrayList<>();
    private boolean mParam2;
    PlanAdapter_2 myadapter;
    RecyclerView  rv;
    PlansDataModel model;
    boolean first = true;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PlanClickListener){
            listener = (PlanClickListener) context;
        }else{
            throw new ClassCastException("activity does not implement fragment listener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(ArrayList<Plan2> param1, boolean param2) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PARAM1, param1);
        bundle.putBoolean(ARG_PARAM2, param2);
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
         Bundle bundle  = getArguments();
         if(bundle!=null){
             mParam1 = (ArrayList<Plan2>) getArguments().getSerializable(ARG_PARAM1);
             mParam2 = getArguments().getBoolean(ARG_PARAM2);

         }

        model =new ViewModelProvider(getActivity()).get(PlansDataModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragmentlist, container, false);

        rv = v.findViewById(R.id.recview);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        model.Plans.observe(getViewLifecycleOwner(), list -> {
            if(list!=null)
            {

                myadapter = new PlanAdapter_2(listener, (ArrayList<Plan2>) list);
                rv.setAdapter(myadapter);

                if (Flag.getInstance().getPlanFlag()){
                    runAnimationAgain();
                    Flag.getInstance().setPlanFlag(false);
                }


//                runAnimationAgain();

            }

        });







        EditText editText = (EditText) v.findViewById(R.id.searching);

        editText.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        return v;
    }

    private void filter(String text){
        ArrayList<Plan2> filteredList = new ArrayList<>();


        for(Plan2 req : mParam1){


//            if(req.getDestination().toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(req);
//            }


        }
      //  myadapter.filterList(filteredList);
    }

    public void runAnimationAgain() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.gridlayout_animation_from_bottom);

        rv.setLayoutAnimation(controller);
        myadapter.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    public void dataChanged(){ myadapter.notifyDataSetChanged(); }

    public interface LoaderFragmentClickListener {
        void LoaderFragmentInteraction(int pos);
    }



    public interface setDriverListener{
        void onDriverSetting(String text);
    }
}
