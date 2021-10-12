package com.example.listing.DataViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listing.Utils.OfflineDatabaseClient;
import com.example.listing.Utils.RestApiClient;
import com.example.listing.Utils.RestLoginClient;
import com.example.listing.models.User;
import com.example.listing.models.UserList;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginView_Model extends ViewModel {

    public MutableLiveData<Boolean> Logged_in = new MutableLiveData<>();
    public MutableLiveData<Boolean> Offline = new MutableLiveData<>();
    Application application;

    public LoginView_Model(Application application) {
        super();
        this.application = application;
    }

    public void OffLogin(String username) {

        AsyncTask.execute(new Runnable() {
            final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());

            @Override
            public void run() {
                UserList user = db.Users().GetUser(username);
                if (user != null) {
                    Offline.postValue(true);
                    Log.i("user", user.getUserId() + "");
                } else
                    Offline.postValue(false);

            }
        });

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public void Login(Application application, User user) {
        RestLoginClient.initializer(application);



        String credentials = Credentials.basic(user.getUserId(), user.getPassword());
        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());


        RestApiClient.initializer(application,null);
        RestApiClient.getInstance().getRetrofitInterface().DVClogin("Fetch").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {

                if (response.isSuccessful()) {
               //     User user1=response.body().getUser();


//                    AsyncTask.execute(new Runnable() {
//                        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());
//
//                        @Override
//                        public void run() {
//                            if(db.Users().GetUser(user1.UserId)==null){
//                                UserList user =new UserList(user1.UserId);
//                                db.Users().insertUser(user);
//                            }
//                        }
//                    });


                    try {
                        Log.i("userinfo",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    Logged_in.postValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Log.i("responsedvc-error", t.getMessage() + t.getLocalizedMessage());
                Logged_in.postValue(false);
            }
        });


//
//        Objects.requireNonNull(RestLoginClient.getInstance()).getRetrofitInterfacelogin().login(credentials).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
//                Log.i("response-portal-failure", t.getMessage() + t.getCause());
//                Logged_in.postValue(false);
//            }
//        });

    }




    public void Logout() {

        Objects.requireNonNull(RestLoginClient.getInstance()).getRetrofitInterfacelogin().logout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {

                Log.i("response-portal-logout", response.message() + response.code());

                if (response.isSuccessful()) {
                    Log.i("response-portal-logout-headers", response.headers().toString());

                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

                Log.i("response-portal-logout-failure", t.getMessage() + t.getCause());

            }
        });

    }

}
