package com.example.listing;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listing.AssignDriver.AssignDialogFragment;
import com.example.listing.Material.Loader.LoaderFragment;
import com.example.listing.Material.Material;
import com.example.listing.Material.MaterialAdapter;
import com.example.listing.Material.Dispatcher.DispatcherFragment;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.Plan.PlanAdapter;
import com.example.listing.Plan.Plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PlanFragment.LoaderFragmentClickListener, AssignDialogFragment.OnPositiveClickListener, AssignDialogFragment.OnNegativeClickListener, PlanClickListener, NetworkResponseListener {

    Button mAssign;
    TextView tv_username;
    ImageButton backBut;
    Plan lastClickedReq;
    PlanAdapter myadapter, planAdapterInstance;
    MaterialAdapter detadapter;
    ArrayList<Plan> plans, networkResp = new ArrayList<>();
    //    List<Material> materials, materials2, materials3, materials4, materials5;
    //CustomGridRecyclerView rv;
    RecyclerView rv;
    DialogFragment dialog;
    int materialpos;
    RecyclerView rv2;
    int po;
    String orgCode, userName;
    JSONArray stageRes;
    private Parcelable savedRecyclerLayoutState;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButton();

        try {
            createLists();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        actionBarSettings();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    public void setButton() {
        Intent intent = getIntent();
        orgCode = intent.getStringExtra("profile");
        userName = intent.getStringExtra("nameof");
        backBut = findViewById(R.id.back);
        tv_username = findViewById(R.id.tv_username);
        tv_username.setText("User: " + userName);
        tv_username.setPaintFlags(tv_username.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = MainActivity.this;
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                ctx.startActivity(LoginIntent);
            }
        });

    }


    //So that main can send the data from list to text fragment
    @Override
    public void LoaderFragmentInteraction(Plan plan) {
        LoaderFragment textfragment = LoaderFragment.newInstance((ArrayList<Material>) plan.getMaterials(), plan.getReq_name(), plan.getVessel_num(), plan.getDestination());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmenttext, textfragment);
        ft.commit();
    }


    public void DispatcherFragmentInteraction(Plan plan) {
        DispatcherFragment dispatcherFragment = DispatcherFragment.newInstance((ArrayList<Material>) plan.getMaterials(), plan.getReq_name(), plan.getVessel_num(), plan.getDestination());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmenttext, dispatcherFragment);
        ft.commit();
    }

    public void displayData() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv.setLayoutManager(new LinearLayoutManager(this));
        }
    }


    public void buildRecycler(ArrayList<Plan> lst) {
        PlanFragment planFragment = PlanFragment.newInstance((lst));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentmain, planFragment);
        ft.commit();
    }

    public void showAssignDialog(int matpos) {
        materialpos = matpos;
        FragmentManager fra = getSupportFragmentManager();
//      dialog = AssignDialogFragment.newInstance(requests.get(po).getMaterials().get(matpos).getPic(), requests.get(po).getMaterials().get(matpos).getName());
        dialog = AssignDialogFragment.newInstance(plans.get(po).getMaterials().get(matpos).getName());
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        dialog.show(fra, "assign");
    }


    @Override
    public void onPositiveClick(String text, String text2) {
        plans.get(po).getMaterials().get(materialpos).setDriver(text);
        plans.get(po).getMaterials().get(materialpos).setVehicle(text2);
        dialog.dismiss();
    }

    @Override
    public void onNegativeClick() {
        //  dialog.dismiss();
    }

    public void runAnimationAgain() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.gridlayout_animation_from_bottom);

        rv.setLayoutAnimation(controller);
        myadapter.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    public void actionBarSettings() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg2));
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        imageView.setImageResource(R.drawable.aramcologo);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
    }

    /*
    CLICK ON A PLAN
     */
    @Override
    public void onItemClick(Plan plan, int pos) {
        boolean load;

        //Test variable
        orgCode = "30015860";

        Toast tst = Toast.makeText(getBaseContext(), orgCode, Toast.LENGTH_LONG);

        //orgnisation code > loader
        if (orgCode.equals("30015860")) {
            LoaderFragmentInteraction(plan);
        }

        //organisation code > dispatcher
        else if (orgCode.equals("30018820")) {
            DispatcherFragmentInteraction(plan);
        }

        po = pos;
        lastClickedReq = plan;
        //myadapter.notifyDataSetChanged();
    }


    @Override
    public void successData(ArrayList<Plan> data) throws JSONException {
        buildRecycler(data);
        plans = data;
    }

    @Override
    public void failedData() {
        Log.d("Failed", "failed");

    }


    public void createLists() throws JSONException {
        //  MaterialAsyncTask tasker = new MaterialAsyncTask(MainActivity.this, MainActivity.this);
        // tasker.execute();

        ArrayList<Plan> reqs = new ArrayList<>();
        ArrayList<Material> mats = new ArrayList<>();

        for (int i = 0; i < 1/*stageRes.length()*/; i++) {
            //JSONObject res = stageRes.getJSONObject(i);
            //String stat = res.getString("ZuphrStatus");
            String stat = "stat";
            //String rq_name = res.getString("ZuphrLpid");
            String rq_name = "rq_name";
            //String time = res.getString("ZuphrLptime");
            String time = "15:55";
            //String date = res.getString("ZuphrLpdate");
            String date ="29/05/2021";
            //String vessel = res.getString("ZuphrVessel");
            String vessel = "vessel";
            //String driver = "";
            String driver = "driver";
            //JSONArray mater = res.getJSONArray("results");
            for (int j = 0; j < 1/*mater.length()*/; j++) {
                //JSONObject mat = mater.getJSONObject(j);
                //String mater_name = mat.getString("ZuphrShorttxt");
                String mater_name = "mater_name";
                //String mater_quan = mat.getString("quan");
                String mater_quan = "mater_quan";
                //String mater_driver = "";
                String mater_driver = "mater_driver";
                //String mater_vehicle = "";
                String mater_vehicle = "mater_vehicle";
                mats.add(new Material(mater_name, mater_quan, true, mater_driver, mater_vehicle, false));
            }
            reqs.add(new Plan(stat, rq_name, time, date, vessel, driver, mats));
            NetworkResponseListener networkResponseListener = MainActivity.this;
            networkResponseListener.successData(reqs);


        }
    }
}

//        ArrayList<Request> reqs = null;
//        ArrayList<Material> mats = null;
//
//        for(int i = 0 ; i < stageRes.length() ; i++){
//            JSONObject res = stageRes.getJSONObject(i);
//            String stat = res.getString("ZuphrStatus");
//            String rq_name = res.getString("ZuphrLpid");
//            String time = res.getString("ZuphrLptime");
//            String date = res.getString("ZuphrLpdate");
//            String vessel = res.getString("ZuphrVessel");
//            String driver ="";
//            JSONArray mater = res.getJSONArray("results");
//            for(int j = 0 ; j < mater.length() ; j++){
//                JSONObject mat= mater.getJSONObject(j);
//                String mater_name = mat.getString("ZuphrShorttxt");
//                String mater_quan = mat.getString("quan");
//                String mater_driver = "";
//                String mater_vehicle = "";
//                mats.add(new Material(mater_name, mater_quan, false, mater_driver, mater_vehicle, false));
//            }
//            reqs.add(new Request(stat, rq_name,time, date,vessel,driver,mats));
//            requests = reqs;
//
//
//        }

//    public void onStageInteraction(String staging){
//        TextFragment textfragment = TextFragment.newInstance((ArrayList<Material>) request.getMaterials(), request.getReq_name(), request.getVessel_num(), request.getDestination());
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragmenttext, textfragment);
//        ft.commit();
//    }

//    public void sortBeer() {
//
//        Collections.sort(requests, new Comparator<Request>() {
//            @Override
//            public int compare(Request o1, Request o2) {
//                //return o1.getReq_num() > o2.getReq_num() ?-1 :(o1.getStatus() <  o2.getStatus()) ? 1 : 0);
//                return o1.getStatus().compareToIgnoreCase(o2.getStatus());
//            }
//        });
//
//    }
//
//
//    public void assignButton(int matpos){
//        mAssign = findViewById(R.id.add_button);
//        materialpos = matpos;
////        mAssign = findViewById(R.id.btn_assign);
//        mAssign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAssignDialog(6);
//            }
//        });
//    }
//    public void buildTextRecycler(){
//        rv2 = findViewById(R.id.textrecview);
//        detadapter = new DetAdapter(requests.get(po).getMaterials());
//
//        TextView reqnum = findViewById(R.id.reqNum_txtfrag);
//        detadapter.setLoadListener(new DetAdapter.loadClick() {
//            @Override
//            public void loadButtonClicked(int pos) {
//                requests.get(po).getMaterials().get(pos).setLoaded(true);
//                detadapter.notifyDataSetChanged();
//                myadapter.notifyDataSetChanged();
//            }
//        });
//        detadapter.setUnloadListener(new DetAdapter.unloadClick() {
//            @Override
//            public void unloadButtonClick(int pos) {
//                requests.get(po).getMaterials().get(pos).setLoaded(false);
//                detadapter.notifyDataSetChanged();
//                myadapter.notifyDataSetChanged();
//            }
//        });
//
//        reqnum.setText(requests.get(po).getReq_name());
//        detadapter.notifyDataSetChanged();
//        rv2.setAdapter(detadapter);
//
////        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
////        rv2.addItemDecoration(itemDecoration);
//        rv2.setLayoutManager(new LinearLayoutManager(this));
//    }

   // @Override
//    public void loadButtonClicked(final int po2) {
//
//        mLoad = findViewById(R.id.load_button);
//        mLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                requests.get(po).getMaterials().get(po2).setLoaded(true);
//                //materials.get(pos).setLoaded(true);
//                detadapter.notifyDataSetChanged();
//                myadapter.notifyDataSetChanged();
//            }
//        });
//
//
//    }
//
//    @Override
//    public void unloadButtonClick(final int pos) {
//        mUnload  = findViewById(R.id.unload_button);
//        mUnload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requests.get(po).getMaterials().get(pos).setLoaded(false);
//                materials.get(pos).setLoaded(false);
//                detadapter.notifyDataSetChanged();
//                myadapter.notifyDataSetChanged();
//            }
//        });
//
//    }
    //    @Override
//    public void successData(JSONArray data) throws JSONException {
//        String nameof = data.getJSONObject(0).getString("ZuphrProfid");
//        Log.d("listen", nameof);
//        stageRes = data;
//
//
//        Toast tst = Toast.makeText(this, nameof, Toast.LENGTH_LONG);
//        tst.show();
//
//    }

    //    public void createList(){
//        materials = new ArrayList<>();
//        materials.add(new Material("Buouy", "1", false, R.drawable.buouy , "", "", true));
//        materials.add(new Material("OCTG Pipe", "2", true, R.drawable.octg_pipe, "", "", true));
//        materials.add(new Material("Wellhead", "3", false, R.drawable.wellhead, "", "", true));
//
//
//        requests = new ArrayList<>();
//        requests.add(new Request("incomplete", "Plan#1478523690 Station 5", "ADC-12",  "19.09.2018", "12:00",  "Details1", "ZAMIL1", "d1" , materials));
//
//
//        materials2 = new ArrayList<>();
//        materials2.add(new Material("OCTG Pipe", "4", false, R.drawable.octg_pipe2, "", "", true));
//        materials2.add(new Material("Buouy", "5", false, R.drawable.buouy2, "", "", true));
//        materials2.add(new Material("Wellhead", "6", false, R.drawable.wellhead2, "", "", true));
//        requests.add(new Request("complete", "Plan#1231824578 Station 2", "111-SU",  "05.10.2018", "15:00",  "Details2", "RAWABI1", "d2", materials2));
//
//        materials3 = new ArrayList<>();
//        materials3.add(new Material("Wellhead", "7", false, R.drawable.wellhead2, "", "", true));
//        materials3.add(new Material("Buouy", "8", false, R.drawable.buouy, "", "", true));
//        materials3.add(new Material("OCTG Pipe", "9", false, R.drawable.octg_pipe, "", "", true));
//        requests.add(new Request("incomplete", "Plan#1824192537 Station 4", "ADC-12",  "20.01.2019", "01:00", "Details3", "ZAMIL3", "d3", materials3));
//
//
//        materials4 = new ArrayList<>();
//        materials4.add(new Material("Buouy", "10", false, R.drawable.buouy2, "", "", true));
//        materials4.add(new Material("OCTG Pipe", "11", true, R.drawable.octg_pipe2, "", "", false));
//        materials4.add(new Material("Wellhead", "12", true, R.drawable.wellhead4, "", "", false));
//        requests.add(new Request("incomplete", "Plan#1820263427 Station 6", "ADC-17",  "17.12.2019", "12:00", "Details4", "RAWABI4", null, materials4));
//
//        materials5 = new ArrayList<>();
//        materials5.add(new Material("Wellhead", "13", true, R.drawable.wellhead3, "", "", false));
//        materials5.add(new Material("Buouy", "14", true, R.drawable.buouy2, "", "", false));
//        materials5.add(new Material("OCTG Pipe", "15", true, R.drawable.octg_pipe, "", "", false));
//        requests.add(new Request("incomplete", "Plan#1325291629 Station 1", "111-SU",  "05.10.2018", "19:00",  "Details5", "ZAMIL5", null, materials5));
//
//    }


