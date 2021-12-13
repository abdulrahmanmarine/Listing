package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listing.R;
import com.example.listing.Utils.AppExecutors;

import com.example.listing.Utils.Loginsession;
import com.example.listing.Utils.OfflineDatabaseClient;
import com.example.listing.Utils.RestApiClient;
import com.example.listing.Utils.RetrofitInterface;
import com.example.listing.models.Driver;
import com.example.listing.models.ImageList;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;
import com.example.listing.models.PlanUnpack;
import com.example.listing.models.User;
import com.example.listing.models.Vehicle;
import com.example.listing.models.imagenode;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlansDataModel extends ViewModel {

    private final SharedPreferences preferences;
    private final String Mode;
    private final OfflineDatabaseClient db;
    public MutableLiveData<List<Plan>> Plans = new MutableLiveData<>();
    public  MutableLiveData<Plan> plan = new MutableLiveData<>();
    public MutableLiveData<List<com.example.listing.models.Material>>MatrialsList = new MutableLiveData<>();
    public  MutableLiveData<Boolean> UserRule =new MutableLiveData<>();

    public  MutableLiveData<List<Driver>> MasterdriversList = new MutableLiveData<>();
    public MutableLiveData<List<Vehicle>> MastervehiclesList = new MutableLiveData<>();

    public MutableLiveData<com.example.listing.models.Material> Matrial = new MutableLiveData<>();

    public MutableLiveData<List<imagenode>> MatrialImageList = new MutableLiveData<>();
    public MutableLiveData<LoadAction> LoadAction = new MutableLiveData<>();
    public MutableLiveData<Boolean> flag = new MutableLiveData<>(true);
    Application application;


    private static RetrofitInterface retrofitInterface;


    public PlansDataModel(Application application) {
        super();
        this.application = application;

        retrofitInterface = RestApiClient.getInstance(application).getRetrofitInterface();

        preferences = application.getSharedPreferences(application.getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        Mode = preferences.getString(application.getResources().getString(R.string.UserMode), "");
        db = OfflineDatabaseClient.getInstance(application.getApplicationContext());


    }


    public void getplansLoader(Application application,LifecycleOwner owner) throws IOException {
        //OFFLINE DATA RETRIEVAL

        if (Mode.equals("offline")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                List<Plan> itemlist = db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId()).getValue();
            });


            db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId()).observe(owner,itemlist->{
                Log.i("test plans:",itemlist.size()+"");

                for(int i=0 ;i<itemlist.size();i++){
                    Plan plan=itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist->{
                        Log.i("test matrial:",matriallist.size()+"");

                        for(int j=0 ;j<matriallist.size();j++) {
                            Material material=matriallist.get(j);
                            int x=j;
                            db.MatrialLoadAction().GetItemAll(String.valueOf(material.MatrialId)).observe(owner, loadaction->{
                                Log.i("test loadaction:",loadaction.MatiralOfflineID+"");

                                material.setZuphrLoada(loadaction);
                                matriallist.set(x,material);
                            });
                        }
                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i,plan);
                }
                Plans.postValue(itemlist);
            });

        }


        //ONLINE DATA RETRIEVAL
        else {
            retrofitInterface.getPlansLoader("Fetch").enqueue(new Callback<PlanUnpack>() {
                @Override
                public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
                    if (response.isSuccessful()) {
                        List<Plan> temp =response.body().getItems();
                        Plans.postValue(temp);

                         AppExecutors.getInstance().diskIO().execute(() -> {
                            for(int i=0 ;i<temp.size();i++){
                                Plan plan= temp.get(i);
                                plan.setZuphrFpName(Loginsession.getInstance().getUser().getUserId());
                                String id = String.valueOf(db.planitem().insertplan(plan));

                                for(int j=0 ;j<temp.get(i).getPlanToItems().size();j++) {
                                    Material material=temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    String Mid= String.valueOf(db.Matrial().insertMatrial(material));
                                    LoadAction loadAction=material.getZuphrLoada();
                                    loadAction.setMatiralOfflineID(Mid);
                                    db.MatrialLoadAction().insertLoadAction(loadAction);
                                }



                            }
                        });

                    }

                }
                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {
                    Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

                }
            });
        }





   }


    public void getplansDispatcher(Application application, LifecycleOwner owner) throws IOException {
        //OFFLINE DATA RETRIEVAL
        if (Mode.equals("offline")) {
            db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId()).observe(owner,itemlist->{
                Log.i("test plans:",itemlist.size()+"");

                for(int i=0 ;i<itemlist.size();i++){
                    Plan plan=itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist->{
                        Log.i("test matrial:",matriallist.size()+"");

                        for(int j=0 ;j<matriallist.size();j++) {
                            Material material=matriallist.get(j);
                            int x=j;
                            db.MatrialLoadAction().GetItemAll(String.valueOf(material.MatrialId)).observe(owner, loadaction->{
                                Log.i("test loadaction:",loadaction.MatiralOfflineID+"");

                                material.setZuphrLoada(loadaction);
                                matriallist.set(x,material);
                            });
                        }
                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i,plan);
                }
                Plans.postValue(itemlist);
            });

        }

        //ONLINE DATA RETRIEVAL
        else {
            retrofitInterface.getPlansDispatcher("Fetch").enqueue(new Callback<PlanUnpack>() {
                @Override
                public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
                    if (response.isSuccessful()) {
                        List<Plan> temp =response.body().getItems();
                        Plans.postValue(temp);

                        AppExecutors.getInstance().diskIO().execute(() -> {
                            for(int i=0 ;i<temp.size();i++){
                                Plan plan= temp.get(i);
                                plan.setZuphrFpName(Loginsession.getInstance().getUser().getUserId());
                                String id = String.valueOf(db.planitem().insertplan(plan));

                                for(int j=0 ;j<temp.get(i).getPlanToItems().size();j++) {
                                    Material material=temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    String Mid= String.valueOf(db.Matrial().insertMatrial(material));
                                    LoadAction loadAction=material.getZuphrLoada();
                                    loadAction.setMatiralOfflineID(Mid);
                                    db.MatrialLoadAction().insertLoadAction(loadAction);
                                }



                            }
                        });

                    }

                }
                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {
                    Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

                }
        });
        }
    }

    public  void getVechiles(){

       Driver driver1 = new Driver("1", "Abdul", "Heavy Vehicle Driving",
               "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
       Driver driver2 = new Driver("2", "Ahmed", "Small Vehicle Driving",
               "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
       Driver driver3 = new Driver("3", "Ali", "Small Vehicle Driving",
               "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
       Driver driver4 = new Driver("4", "Murada", "Small Vehicle Driving",
               "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
       Driver driver5 = new Driver("5", "Yousef", "Small Vehicle Driving",
               "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
       Driver driver6 = new Driver("6", "Mohammed", "Small Vehicle Driving",
               "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");


       Vehicle vehicle1 = new Vehicle("1","Medium", "Truck", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);
       Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);
       Vehicle vehicle3 = new Vehicle("3","Medium", "Two Wheel", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);
       Vehicle vehicle4 = new Vehicle("4","Medium", "Four Wheel", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);
       Vehicle vehicle5 = new Vehicle("5","Medium", "Small Truck", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);
       Vehicle vehicle6 = new Vehicle("6","Medium", "Huge Truck", "456234", "1000",
               "Red", "2012", "DDMMYYYY", "123456",null);

        ArrayList<Driver> driversList = new ArrayList<>();
        ArrayList<Vehicle> vehiclesList = new ArrayList<>();

       driversList.add(driver1);
       driversList.add(driver2);
       driversList.add(driver3);
       driversList.add(driver4);
       driversList.add(driver5);
       driversList.add(driver6);
       MasterdriversList.setValue(driversList);



       vehiclesList.add(vehicle1);
       vehiclesList.add(vehicle2);
       vehiclesList.add(vehicle3);
       vehiclesList.add(vehicle4);
       vehiclesList.add(vehicle5);
       vehiclesList.add(vehicle6);
       MastervehiclesList.setValue(vehiclesList);


   }

    public void GetVehicle (Application application ,String Vehicleid){

        retrofitInterface.GetVehicle("VehicleSet('"+Vehicleid+"')").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("response-plan" ,response.code()+""+response.message());
                if (response.isSuccessful()) {

                    Log.i("response-plan" ,response.body().toString());

                }
                else {
                    Log.i("response-plan-error1" ,response.code()+""+response.message());
                    try {
                        Log.i("response-plan-error2" ,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

            }
        });
    }

    public void GetVehicles (Application application ){

        retrofitInterface.GetVehicle("VehicleSet?$filter=Vehid eq '1' or Vehid eq '2'").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("response-plan" ,response.code()+""+response.message());
                if (response.isSuccessful()) {

                    Log.i("response-plan" ,response.body().toString());

                }
                else {
                    Log.i("response-plan-error1" ,response.code()+""+response.message());
                    try {
                        Log.i("response-plan-error2" ,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

            }
        });
    }

    public void GetImages(Application application,  String MaterialId){

        retrofitInterface.getImageList("ImageHandlingSet?$filter=ZuphrId eq'"+MaterialId+
                "'and AppPrefix eq 'STEV 'and Item eq '1' and ThumbNail eq ''")
                .enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, retrofit2.Response<ImageList> response) {
                if (response.isSuccessful()) {
                    List<imagenode> temp = response.body().getItems();
                    MatrialImageList.postValue(temp);
                }
            }
            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());
            }
        });
    }

    public void postLoadAction(LoadAction loadAction){
        SharedPreferences preferences=application.getSharedPreferences(application.getResources()
                .getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        String token2 =preferences.getString("x-csrf-token","");
        retrofitInterface.postLoadAction(loadAction, token2).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void StroingImages(Application application, List<imagenode> imagenode,String MatrialId)
            throws JSONException {

        SharedPreferences preferences=application.getSharedPreferences(application.getResources()
                .getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        String token2 =preferences.getString("x-csrf-token","");

        Log.i("Imagelist",imagenode.size()+"");
        for(int i=0;i<imagenode.size();i++) {
            imagenode imagenode1 = imagenode.get(i);
            imagenode1.setZuphrId(MatrialId);
            retrofitInterface.SaveImages(token2, imagenode1).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            Log.d("response-post", response.code() + "");
                            if (response.code() == 201) {
                                ResponseBody temp = response.body();

                                try {
                                    Log.i("response-post", temp.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("response-error", t.getMessage());


                        }
                    });
        }

    }


}
