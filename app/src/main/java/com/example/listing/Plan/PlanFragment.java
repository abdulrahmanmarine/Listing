package com.example.listing.Plan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;

import com.example.listing.PlanClickListener;
import com.example.listing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.


 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LoaderFragmentClickListener listener;
    List<Plan> plans = new ArrayList<>();
    int currentpos;


    // TODO: Rename and change types of parameters
    private ArrayList<Plan> mParam1 = new ArrayList<>();
    PlanAdapter myadapter;
    RecyclerView  rv;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LoaderFragmentClickListener){
            listener = (LoaderFragmentClickListener) context;
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

    public static PlanFragment newInstance(ArrayList<Plan> param1){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PARAM1, param1);
        //bundle.putString(ARG_PARAM2, param2);
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static ListFragment newInstance(String param1, String param2) {
//        ListFragment fragment = new ListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
         Bundle bundle  = getArguments();
         if(bundle!=null){
             mParam1 = (ArrayList<Plan>) getArguments().getSerializable(ARG_PARAM1);
            //param2 = bundle.getString(ARG_PARAM2);

         }


//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragmentlist, container, false);

        rv = v.findViewById(R.id.recview);
        myadapter = new PlanAdapter((PlanClickListener) listener, mParam1);

        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rv.setAdapter(myadapter);
        //rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
//        ((MainActivity) getActivity()).runAnimationAgain();
        runAnimationAgain();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.gridlayout_animation_from_bottom);

//        myadapter.notifyDataSetChanged();
//        rv.setLayoutAnimation(controller);
//        myadapter.notifyDataSetChanged();
//        rv.scheduleLayoutAnimation();

//        myadapter.setOnItemClickListener(new MyAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(Request request, int pos) {
//                Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT);
//            }
//        });

        EditText editText = (EditText) v.findViewById(R.id.searching);




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


//        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.swing_up_left);
//        v.startAnimation(animation);
        //RecyclerView rv = v.findViewById(R.id.recview);



//        requests.add(new Request("incomplete", "Drilling Replacement", "ADC-12", "2", "19.09.2018", "12:00", R.drawable.blue, "Details1", "Request 1", "d1"));
//        requests.add(new Request("complete", "Something Else", "111-SU", "3", "05.10.2018", "15:00", R.drawable.drillbitr, "Details2", "Request 2", "d2"));
//        requests.add(new Request("complete", "Another Request", "ADC-12", "1", "20.01.2019", "01:00", R.drawable.grey, "Details3", "Request 3", "d3"));
//        requests.add(new Request("incomplete", "Maintenance", "ADC-17", "1", "17.12.2019", "12:00", R.drawable.item2, "Details4", "Request 4", null));
//        requests.add(new Request("complete", "Pipe Replacement", "111-SU", "3", "05.10.2018", "19:00", R.drawable.white, "Details5", "Request 5", null));


//        MyAdapter myadapter = new MyAdapter(requests, new ItemClickListener() {
//            @Override
//            public void onItemClick(Request request) {
//                listener.onFragmentInteraction(request);
//
//            }
//        });

//        rv.setAdapter(myadapter);
//        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return v;
    }

    private void filter(String text){
        ArrayList<Plan> filteredList = new ArrayList<>();
//        myadapter = new MyAdapter((ItemClickListener) this, mParam1);
//        myadapter = new MyAdapter(mParam1);

        for(Plan req : mParam1){
            if(req.getDestination().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(req);
            }
        }
        myadapter.filterList(filteredList);
    }




    public interface LoaderFragmentClickListener {
        void LoaderFragmentInteraction(Plan plan);
    }

    public void runAnimationAgain() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.gridlayout_animation_from_bottom);

        rv.setLayoutAnimation(controller);
        myadapter.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    public void dataChanged(){ myadapter.notifyDataSetChanged(); }



    public interface setDriverListener{
        void onDriverSetting(String text);
    }
}
