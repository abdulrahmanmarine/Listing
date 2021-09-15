package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.listing.models.Plan2;
import com.example.listing.Material.Material;
import com.example.listing.R;
import com.example.listing.Utils.RestApi;
import com.example.listing.Utils.RetrofitInterface;
import com.example.listing.models.ImageList;
import com.example.listing.models.LoadAction;
import com.example.listing.models.PlanUnpack;
import com.example.listing.models.imagenode;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PlansDataModel extends ViewModel {

    public MutableLiveData<List<Plan2>> Plans = new MutableLiveData<>();

    public MutableLiveData<List<Material>>MatrialsList = new MutableLiveData<>();
    public MutableLiveData<Material> Matrial = new MutableLiveData<>();

    public MutableLiveData<List<imagenode>> MatrialImageList = new MutableLiveData<>();
    public MutableLiveData<LoadAction> LoadAction = new MutableLiveData<>();



    Application application;

    private static RetrofitInterface retrofitInterface;


    public PlansDataModel(Application application) throws NoSuchAlgorithmException, KeyStoreException,
            KeyManagementException, AuthenticationException  {
        super();
        this.application = application;
        RestApi.initializer(application,null);
        retrofitInterface = RestApi.getInstance().getRetrofitInterface();

    }

    public void setMatrial(Material matrial) {
        Matrial.postValue(matrial);
    }


    public void getplans(Application application){

        RestApi.initializer(application,null);
       retrofitInterface.getPlans("Fetch").enqueue(new Callback<PlanUnpack>() {
           @Override
           public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
               Log.i("response-plan" ,response.code()+""+response.message());
               if (response.isSuccessful()) {
                   List<Plan2> temp =response.body().getItems();
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
