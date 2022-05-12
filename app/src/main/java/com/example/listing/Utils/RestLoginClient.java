package com.example.listing.Utils;


import android.app.Application;
import android.util.Log;

import com.example.listing.R;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.util.List;
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
    private final RetrofitInterfaceLogin RetrofitInterfaceLogin;


    private RestLoginClient( Application application) {
        RetrofitInterfaceLogin = Login(application);
    }

    public static void initializer(Application application) {
        if (instance == null) {
            synchronized (RestLoginClient.class) {
                instance = new RestLoginClient(application);

            }
        }
    }

    public static RestLoginClient getInstance(Application application) {
        if (instance == null) {
            instance = new RestLoginClient( application);
        }
        return instance;

    }

    static OkHttpClient headersInterceptors(Application application) {

        return new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(application))
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(900, TimeUnit.SECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor(application))
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }


    public static RetrofitInterfaceLogin Login( Application application) {
        return CreateClient( application).create(RetrofitInterfaceLogin.class);
    }

    public static Retrofit CreateClient( Application application) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(application.getString(R.string.portalURL))
                    .client(headersInterceptors(application))
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().
                            setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy())))
                    .build();
        }
        return retrofit;
    }




    public RetrofitInterfaceLogin getRetrofitInterfaceLogin() {
        return RetrofitInterfaceLogin;
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
                    .add("Content-Type", application.getResources().getString(R.string.Content_Type))
                    .add("Accept", application.getResources().getString(R.string.accept))
                    .add("sap-client", application.getResources().getString(R.string.sapclient_222))
                    .add("User-Agent", application.getResources().getString(R.string.user_agent))

                    .build();

            Request request = chain.request().newBuilder().headers(headers).build();

            return chain.proceed(request);
        }
    }

    private static class ReceivedCookiesInterceptor implements Interceptor {
        Application application;

        public ReceivedCookiesInterceptor(Application application) {
            this.application = application;
        }

        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            Log.i("Login url:", originalResponse.request().url().toString());
            Log.i("Login header-request:", originalResponse.request().headers().toString());
            Log.i("Login header-response:", originalResponse.headers().toString());


            List<String> Cookielist = originalResponse.headers().values("Set-Cookie");


            RestApiClient.initializer(application,Cookielist,null);


            return originalResponse;
        }
    }


}
