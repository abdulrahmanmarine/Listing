package com.example.listing.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RetrofitInterfaceLogin {

    @GET("irj/portal/")
    Call<ResponseBody> login(@Header("Authorization") String creds);

    @GET("/irj/servlet/prt/portal/prtroot/com.sap.portal.navigation.masthead.LogOutComponent?logout_submit=true")
    Call<ResponseBody> logout();


}
