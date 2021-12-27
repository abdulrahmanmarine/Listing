package com.example.listing.Utils;

import com.example.listing.models.Device;
import com.example.listing.models.Deviceunpack;
import com.example.listing.models.Driver;
import com.example.listing.models.DriverUnpack;
import com.example.listing.models.LoadAction;
import com.example.listing.models.MatrialDispatching;
import com.example.listing.models.Plan;
import com.example.listing.models.ImageList;

import com.example.listing.models.PlanUnpack;
import com.example.listing.models.Userunpack;
import com.example.listing.models.Vehicle;
import com.example.listing.models.VehicleUnpack;
import com.example.listing.models.imagenode;
import com.example.listing.models.SAPNote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {




    @GET("LoadingPlanSet?$expand=PlanToItems&$filter=ZuphrActtype eq 'LOAD' and ZuphrDeleted eq false")
    Call<PlanUnpack> getPlansLoader(@Header("x-csrf-token") String token);


    @GET("LoadingPlanSet?$expand=PlanToItems&$filter=ZuphrActtype eq 'DISP' and ZuphrDeleted eq false")
    Call<PlanUnpack> getPlansDispatcher(@Header("x-csrf-token") String token);



    @GET("VehicleSet")
    Call<VehicleUnpack> GetVehicle(@Header("x-csrf-token") String token);
    @GET("DriverSet")
    Call<DriverUnpack> GetLoader();
    @GET("DeviceSet")
    Call<ResponseBody> GetDevice();
    @GET
    Call<ResponseBody> getDispatch(@Header("x-csrf-token") String token,@Url String url);



    @POST("ImageHandlingSet/")
    Call<ResponseBody> SaveImages(@Header("x-csrf-token") String token,@Body imagenode image);

    @GET
    Call<ImageList> getImageList(@Url String filter);

    @GET()
    Call<ResponseBody> retrieveNotes(@Url String filter);

    @POST("NoteSet")
    Call<ResponseBody> submitNote(@Body SAPNote note, @Header("x-csrf-token") String Token);

    @POST("DriverSet")
    Call<ResponseBody> SaveDriver(@Body Driver driver, @Header("x-csrf-token") String Token);
    @POST("DeviceSet")
    Call<ResponseBody> SaveDevice(@Body Device device, @Header("x-csrf-token") String Token);
    @POST("VehicleSet")
    Call<ResponseBody> SaveVechile(@Body Vehicle vehicle, @Header("x-csrf-token") String Token);
    @POST("DispatcherSet")
    Call<ResponseBody> Dispatch(  @Body  MatrialDispatching matrialDispatch, @Header("x-csrf-token") String token);



    @POST("LoadingPlanLoadActionSet")
    Call<ResponseBody> postLoadAction(@Body LoadAction loadAction, @Header("x-csrf-token") String token);

    @GET("UserInfoSet/")
    Call<Userunpack> DVClogin(@Header("x-csrf-token") String fetch);







   }
