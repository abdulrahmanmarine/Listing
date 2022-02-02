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
import java.util.Objects;

import okhttp3.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginView_Model extends ViewModel {

    public MutableLiveData<Boolean> Logged_in = new MutableLiveData<>();
    public MutableLiveData<Boolean> Offline = new MutableLiveData<>();
    Application application;
    public MutableLiveData<String> ErrorMsg = new MutableLiveData<>();


    public LoginView_Model(Application application) {
        super();
        this.application = application;
    }


    public void OffLogin(String username, LifecycleOwner owner) {
        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());
        db.Users().GetUser(username.toUpperCase())
                .observe(owner, user -> {
                    if (user != null) {
                        user=new User();
                        user.setUserId(user.getUserId());
                        Loginsession.initializer(null,  user);
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


    public void Login(Application application, User user,LifecycleOwner owner) {


        String credentials = Credentials.basic(user.UserId, user.getPassword());

          RestLoginClient.initializer(application);

        final OfflineDatabaseClient db = OfflineDatabaseClient.getInstance(application.getApplicationContext());


        Objects.requireNonNull(RestLoginClient.getInstance(application)).getRetrofitInterfaceLogin().login(credentials).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                    RestApiClient.getInstance(application).getRetrofitInterface().DVClogin("Fetch").enqueue(new Callback<Userunpack>() {
                        @Override
                        public void onResponse(@NotNull Call<Userunpack> call, @NotNull retrofit2.Response<Userunpack> response) {
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
                                Loginsession.getInstance().setUser(user1);
                                Logged_in.postValue(true);

                            }
                            if(response.errorBody()!=null){
                                Logged_in.postValue(false);
                                try {
                                    String error=response.errorBody().string();
                                    Log.i("Dvc1 ERROR", response.code()+response.message()+error);
                                    ErrorMsg.setValue("Login Unauthorized check credentials ");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Userunpack> call, @NotNull Throwable t) {
                            Logged_in.postValue(false);
                            Log.i("Dvc -2 ERROR",t.getLocalizedMessage());
                            ErrorMsg.setValue(t.getLocalizedMessage());
                        }
                    });






                }
            }
            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Logged_in.postValue(false);
                ErrorMsg.setValue(t.getLocalizedMessage());
            }
        });




    }




    public void Logout() {

        Objects.requireNonNull(RestLoginClient.getInstance(application)).getRetrofitInterfaceLogin().logout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {


                if(response.errorBody()!=null){
                    try {
                        String error=response.errorBody().string();
                        Log.i("logout-failed",response.code()+response.message()+error);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                if (response.isSuccessful()) {

                    Log.i("logout","worked");
                    Loginsession.getInstance().setUser(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Log.i("logout-failed",t.getMessage());

            }
        });

    }

}
