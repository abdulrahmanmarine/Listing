package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.listing.AssignDriver.AssignPair;
import com.example.listing.models.Driver;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;

import com.example.listing.R;
import com.example.listing.Utils.RestApi;
import com.example.listing.Utils.RetrofitInterface;
import com.example.listing.models.ImageList;
import com.example.listing.models.LoadAction;
import com.example.listing.models.PlanUnpack;
import com.example.listing.models.Vehicle;
import com.example.listing.models.imagenode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlansDataModel extends ViewModel {

    public MutableLiveData<List<Plan>> Plans = new MutableLiveData<>();
    public  MutableLiveData<Plan> plan = new MutableLiveData<>();
    public MutableLiveData<List<com.example.listing.models.Material>>MatrialsList = new MutableLiveData<>();

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
        RestApi.initializer(application,null);

        retrofitInterface = RestApi.getInstance().getRetrofitInterface();

    }

    public void setMatrial(com.example.listing.models.Material matrial) {
        Matrial.postValue(matrial);
    }


    public void getplans(Application application) throws IOException {
        //OFFLINE DATA RETRIEVAL

        List<Plan> temp = new ArrayList<>();
        List<com.example.listing.models.Material> mats = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        List<Driver> drivers2 = new ArrayList<>();

        List<Vehicle> vehicles = new ArrayList<>();
        List<Vehicle> vehicles2 = new ArrayList<>();

        List<AssignPair> assignPairs = new ArrayList<>();

    LoadAction load = new LoadAction("lpid", "mjahr", "mblpo", "loadid",
            "acttype", "assignedquan", "unit", false,"fpdate", "fptime", "name", "size", "confirmedquan",
            "zuphract", drivers, "weight", vehicles, "status", "content");

        LoadAction load2 = new LoadAction("lpid", "mjahr", "mblpo", "loadid",
                "acttype", "assignedquan", "unit", false,"fpdate", "fptime", "name", "size", "confirmedquan",
                "zuphract", drivers2, "weight", vehicles2, "status", "content");

        Driver driver1 = new Driver("3", "Abdul", "Heavy Vehicle Driving",
                "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
        Driver driver2 = new Driver("2", "Ahmed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");

        Vehicle vehicle1 = new Vehicle("3","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456");
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456");


        mats.add(new com.example.listing.models.Material("actquan", "String log",  load, "String zuphrMovem", "String zuphrActtype",
                "String zuphrContents", "String zuphrHeight", "String zuphrLength", "String zuphrLpid",
                "String zuphrObjecte", "String zuphrSchar"," String zuphrFrom", "String zuphrSchtask",
                "String zuphrSeq", "String zuphrWidth", "String zuphrShipper", "String zuphrTo",
                "String zuphrVolmeins", "String zuphrMjahr", "String zuphrMblpo", "String zuphrStgid",
                "String zuphrMatnr"," String zuphrReqid", "String zuphrReqitm", "String zuphrShortxt",
                "String zuphrDescrip", "String zuphrQuan", "String meins", "String zuphrOffshore",
                "String zuphrAreacode", "String zuphrStatus", "String zuphrClass", "String zuphrDeleted",
                "String zuphrStatdt", "String zuphrCompflg", "String zuphrCompdat", "String zuphrAction",
               " String zuphrResponse", "String zuphrAutoact", "String zuphrActposgrp", "String zuphrSent",
                "String zuphrActuname", "String zuphrActdate", "String zuphrActtime", "String zuphrWellnm",
                "String zuphrEquipnumber", "String zuphrCnumber", "String zuphrTicketno", "String zuphrMfrpn",
                "String zuphrBitType", "String zuphrBitstatus", "String zuphrSrlno"," String zuphrGl",
                "String zuphrCostcen", "String zuphrMattype", "String zuphrFpDate", "String zuphrFpTime",
                "String zuphrFpName", drivers, vehicles));

        mats.add(new com.example.listing.models.Material("actquan", "String log",  load2, "String zuphrMovem", "String zuphrActtype",
                "String zuphrContents", "String zuphrHeight", "String zuphrLength", "String zuphrLpid",
                "String zuphrObjecte", "String zuphrSchar"," String zuphrFrom", "String zuphrSchtask",
                "String zuphrSeq", "String zuphrWidth", "String zuphrShipper", "String zuphrTo",
                "String zuphrVolmeins", "String zuphrMjahr", "String zuphrMblpo", "String zuphrStgid",
                "String zuphrMatnr"," String zuphrReqid", "String zuphrReqitm", "String zuphrShortxt",
                "String zuphrDescrip", "String zuphrQuan", "String meins", "String zuphrOffshore",
                "String zuphrAreacode", "String zuphrStatus", "String zuphrClass", "String zuphrDeleted",
                "String zuphrStatdt", "String zuphrCompflg", "String zuphrCompdat", "String zuphrAction",
                " String zuphrResponse", "String zuphrAutoact", "String zuphrActposgrp", "String zuphrSent",
                "String zuphrActuname", "String zuphrActdate", "String zuphrActtime", "String zuphrWellnm",
                "String zuphrEquipnumber", "String zuphrCnumber", "String zuphrTicketno", "String zuphrMfrpn",
                "String zuphrBitType", "String zuphrBitstatus", "String zuphrSrlno"," String zuphrGl",
                "String zuphrCostcen", "String zuphrMattype", "String zuphrFpDate", "String zuphrFpTime",
                "String zuphrFpName", drivers2, vehicles2));



        temp.add(new Plan("String zuphrLoadtype", false, "String zuphrActtype", "String zuphrStatus",
                "String zuphrVesselName", "String zuphrCaptain", "String zuphrStation", "String zuphrLpid",
                "String majhr"," String zuphrLpname", "String zuphrProfid", "String zuphrRqtype", "String zuphrLpdate",
                "String zuphrLptime", "String zuphrLpuser", "String zuphrLifnr", "String zuphrVessel",
               false, false, "String zuphrFpDate", "String zuphrFpTime",
                "String zuphrFpName", mats));

        temp.add(new Plan("String zuphrLoadtype", false, "String zuphrActtype", "String zuphrStatus",
                "String zuphrVesselName", "String zuphrCaptain", "String zuphrStation", "String zuphrLpid",
                "String majhr"," String zuphrLpname", "String zuphrProfid", "String zuphrRqtype", "String zuphrLpdate",
                "String zuphrLptime", "String zuphrLpuser", "String zuphrLifnr", "String zuphrVessel",
                false, false, "String zuphrFpDate", "String zuphrFpTime",
                "String zuphrFpName", mats));


        Plans.setValue(temp);

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
