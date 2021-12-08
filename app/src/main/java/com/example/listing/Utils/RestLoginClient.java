package com.example.listing.Utils;

import android.app.Application;
import android.util.Log;

import com.example.listing.R;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.android.gms.common.api.Api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

public class RestLoginClient {

    private static RestLoginClient instance;
    private static Retrofit retrofit;
    private static Retrofit retrofitdvc;
    private static CookieManager cookieManager;
    private  static  OkHttpClient client;

    private final RetrofitInterfaceLogin retrofitInterfacelogin;



    public RetrofitInterfaceLogin getRetrofitInterfacelogin() {
        return retrofitInterfacelogin;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }


    private RestLoginClient(Application application) {
        retrofitInterfacelogin = Login(application);
    }





    public static void initializer(Application application) {
        if (instance == null) {
            synchronized (RestLoginClient.class) {
                instance = new RestLoginClient(application);
            }
        }
    }



    public static RestLoginClient getInstance() {
        if (instance == null) {
            return null;
        } else {
            Log.i("response", "here");
            return instance;
        }

    }


    public static RetrofitInterfaceLogin Login(Application application) {
        Log.i("Login-Repo", "2");
        return CreateClient(application).create(RetrofitInterfaceLogin.class);
    }


    public static Retrofit CreateClient(Application application) {
        if (retrofit == null) {
            Log.i("Login-create", "3");

            retrofit = new Retrofit.Builder()
                    .baseUrl(application.getString(R.string.portalURL))
                    .client(headersInterceptors(application))
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy())))
                    .build();

        }
        return retrofit;
    }




    static OkHttpClient headersInterceptors(Application application) {

        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar javaNetCookieJar = new JavaNetCookieJar(cookieManager);

         client = new OkHttpClient.Builder()
                .cookieJar(javaNetCookieJar)
                .addInterceptor(new BasicAuth(application))
                .addInterceptor(new ReceivedCookiesInterceptor(application))
                .connectTimeout(900, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        client.followRedirects();
        return client;
    }

    private static class BasicAuth implements Interceptor {
        Application application;
        public BasicAuth(Application application) {
            this.application = application;
        }
        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {

            Headers headers = chain.request().headers().newBuilder()

                    .add("Content-Type", "application/json; charset=utf-8")
                    .add("Accept", "application/json")
                    .add("X-CSRF-Token", "Fetch")
                    .add("sap-client", "025")
                    .add("User-Agent", application.getResources().getString(R.string.user_agent))
                    .build();

            Request request = chain.request().newBuilder().headers(headers).build();

            return chain.proceed(request);
        }
    }


    private static class  ReceivedCookiesInterceptor implements Interceptor {
        Application application;
        public ReceivedCookiesInterceptor(Application application) {
            this.application = application;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

      // TODO UNCOMMENT FOR AIRWATCH
              RestApiClient.initializer(client,application);


            return originalResponse;
        }
    }






}





