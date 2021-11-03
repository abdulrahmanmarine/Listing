package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.listing.Utils.DataClass;
import com.example.listing.models.Driver;
import com.example.listing.models.Plan;

import com.example.listing.R;
import com.example.listing.Utils.RestApiClient;
import com.example.listing.Utils.RetrofitInterface;
import com.example.listing.models.ImageList;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Vehicle;
import com.example.listing.models.imagenode;

import org.apache.http.auth.AuthenticationException;
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


    public PlansDataModel(Application application) throws NoSuchAlgorithmException, KeyStoreException,
            KeyManagementException, AuthenticationException  {
        super();
        this.application = application;
        RestApiClient.initializer(application,null);

        retrofitInterface = RestApiClient.getInstance().getRetrofitInterface();

    }

    public void setMatrial(com.example.listing.models.Material matrial) {
        Matrial.postValue(matrial);
    }


    public void getplans(Application application) throws IOException {
        //OFFLINE DATA RETRIEVAL

        Plans.setValue(DataClass.getInstance().getPlans());

        Log.i("response-plan-size" ,Plans.getValue().size()+"");

        //ONLINE DATA RETRIEVAL

/*
        RestApi.initializer(application,null);
       retrofitInterface.getPlans("Fetch").enqueue(new Callback<PlanUnpack>() {
           @Override
           public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
               Log.i("response-plan" ,response.code()+""+response.message());
               if (response.isSuccessful()) {
                   List<Plan> temp =response.body().getItems();
                   Log.i("response-plan" ,temp.size()+"items");
                   for(int i=0 ;i<temp.size();i++){
                       Log.i("response-plan:"+i ,temp.get(i)+"items");
                       Log.i("response-plan:"+i+"M:" ,temp.get(i).getPlanToItems().size()+"");
                   }
                   Plans.postValue(temp);

               }else {
                   Log.i("response-plan" ,response.code()+""+response.message());
                   try {
                       Log.i("response-plan-error" ,response.errorBody().string());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

           }
           @Override
           public void onFailure(Call<PlanUnpack> call, Throwable t) {
               Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

           }
       });

        retrofitInterface.portal().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                   }
                Log.i("response-portal" ,response.code()+""+response.message());



            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("response-portal-http" ,t.getMessage()+t.getLocalizedMessage());

            }
        });
*/

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
