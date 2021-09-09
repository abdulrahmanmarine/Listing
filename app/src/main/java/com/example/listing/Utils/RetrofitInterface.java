package com.example.listing.Utils;

import com.example.listing.models.Plan2;
import com.example.listing.models.ImageList;
import com.example.listing.models.imagenode;
import com.example.listing.SAPNote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitInterface {




    @GET("LoadingPlanSet?\\$expand=PlanToItems&\\$filter=ZuphrActtype eq 'STEV' and ZuphrDeleted eq false")
    Call<Plan2.PlanUnpack> getPlans(@Header("x-csrf-token") String token);




    @POST("ImageHandlingSet/")
    Call<ResponseBody> SaveImages(@Header("x-csrf-token") String token,@Body imagenode image);

    @GET
    Call<ImageList> getImageList(@Url String filter);



    @GET()
    Call<ResponseBody> retrieveNotes(@Url String filter);

    @POST("NoteSet")
    Call<ResponseBody> submitNote(@Body SAPNote note, @Header("x-csrf-token") String Token);




}
