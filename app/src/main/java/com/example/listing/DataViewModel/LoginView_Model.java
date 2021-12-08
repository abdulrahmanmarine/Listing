package com.example.listing.DataViewModel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listing.R;
import com.example.listing.Utils.AppExecutors;
import com.example.listing.Utils.Loginsession;
import com.example.listing.Utils.OfflineDatabaseClient;
import com.example.listing.Utils.RestApiClient;
import com.example.listing.Utils.RestLoginClient;
import com.example.listing.models.User;
import com.example.listing.models.Userunpack;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginView_Model extends ViewModel {

    public MutableLiveData<Boolean> Logged_in = new MutableLiveData<>();
    public MutableLiveData<Boolean> Offline = new MutableLiveData<>();
    public MutableLiveData<String> ErrorMsg = new MutableLiveData<>();
    Application application;


    public LoginView_Model(Application application) {
        super();
        this.application = application;
    }

    public void OffLogin(String username, LifecycleOwner owner) {
        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());
        db.Users().GetUser(username.toUpperCase())
                .observe(owner, user -> {
                    if (user != null) {
                        user.setUserId(user.getUserId());
                        Loginsession.initializer(null, user);
                        Log.d("userid1", user.getUserId() + "");
                        Offline.postValue(true);
                    } else {
                        Offline.postValue(false);
                        Log.d("userid2", username.toUpperCase() + "");
                        ErrorMsg.postValue(application.getResources().getString(R.string.OfflineLoginError));
                    }
                });


    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public void Login(Application application, User user, LifecycleOwner owner) {
        RestLoginClient.initializer(application);

        String credentials = Credentials.basic(user.UserId, user.getPassword());
        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());


//        Objects.requireNonNull(RestLoginClient.getInstance()).getRetrofitInterfacelogin().login(credentials).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
//                Logged_in.postValue(false);
//                ErrorMsg.setValue(t.getLocalizedMessage());
//            }
//        });
        RestApiClient.initializer(null, application);
        RestApiClient.getInstance(application).getRetrofitInterface().DVClogin("Fetch").enqueue(new Callback<Userunpack>() {
            @Override
            public void onResponse(@NotNull Call<Userunpack> call, @NotNull retrofit2.Response<Userunpack> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (response.isSuccessful()) {
                    User user1 = response.body().getUser();

                    db.Users().GetUser(user1.getUserId().toUpperCase()).observe(owner, user -> {
                        if (user == null) {
                            AppExecutors.getInstance().diskIO().execute(() -> {
                                user1.setUserId(user1.UserId.toUpperCase());
                                db.Users().insertUser(user1);
                            });
                        }
                    });
                    Loginsession.initializer( response.headers().get("x-csrf-token"), user1);
                    Log.i("response",response.body().getUser().Profileid);
                    Loginsession.getInstance().setUser(user1);
                    Logged_in.postValue(true);

                }
            }

            @Override
            public void onFailure(@NotNull Call<Userunpack> call, @NotNull Throwable t) {
                Logged_in.postValue(false);
                Log.i("response-error",t.getLocalizedMessage());
                ErrorMsg.setValue(t.getLocalizedMessage());
            }
        });


    }


    public void Logout() {

        Loginsession.getInstance().setUser(null);

//        Objects.requireNonNull(RestLoginClient.getInstance()).getRetrofitInterfacelogin().logout().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
//
//
//                if (response.isSuccessful()) {
//                    Stageitem_Singleton.getInstance().setUser(null);
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
//
//
//            }
//        });

    }

}



