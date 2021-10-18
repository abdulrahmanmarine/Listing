package com.example.listing.Utils;

import com.example.listing.models.LoadAction;
import com.example.listing.models.Plan;
import com.example.listing.models.ImageList;

import com.example.listing.models.PlanUnpack;
import com.example.listing.models.imagenode;
import com.example.listing.models.SAPNote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitInterface {




    @GET("LoadingPlanSet?$expand=PlanToItems&$filter=ZuphrActtype eq 'STEV' and ZuphrDeleted eq false")
    Call<PlanUnpack> getPlans(@Header("x-csrf-token") String token);


    @GET("/VehicleSet")
    Call<ResponseBody> GetVehicle(@Url String filter);

    @GET("/DriverSet")
    Call<ResponseBody> GetLoader(@Url String filter);

    @GET("/DeviceSet")
    Call<ResponseBody> GetDevice(@Url String filter);


    @POST("ImageHandlingSet/")
    Call<ResponseBody> SaveImages(@Header("x-csrf-token") String token,@Body imagenode image);

    @GET
    Call<ImageList> getImageList(@Url String filter);

    @GET()
    Call<ResponseBody> retrieveNotes(@Url String filter);

    @POST("NoteSet")
    Call<ResponseBody> submitNote(@Body SAPNote note, @Header("x-csrf-token") String Token);

    @POST("LoadingPlanLoadActionSet")
    Call<ResponseBody> postLoadAction(@Body LoadAction loadAction, @Header("x-csrf-token") String token);


    @GET("ZU_UPHR_MAIN_SRV/UserInfoSet/")
    Call<ResponseBody> DVClogin(@Header("x-csrf-token") String fetch);

}