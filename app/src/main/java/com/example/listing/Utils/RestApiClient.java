package com.example.listing.Utils;

import android.app.Application;

import com.example.listing.R;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

public class RestApiClient {

    private static RestApiClient instance;
    private static Retrofit retrofit;
    private final RetrofitInterface retrofitInterface;


    private RestApiClient(OkHttpClient client, Application application) {
        retrofitInterface = Login(client, application);
    }

    public static void initializer(OkHttpClient client, Application application) {
        if (instance == null) {
            synchronized (RestApiClient.class) {
                instance = new RestApiClient(client, application);
            }
        }
    }

    public static RestApiClient getInstance(Application application) {
        if (instance == null) {
            instance = new RestApiClient(new OkHttpClient(), application);
        }
        return instance;

    }

    public static RetrofitInterface Login(OkHttpClient client, Application application) {

        return CreateClient(client, application).create(RetrofitInterface.class);
    }

    public static Retrofit CreateClient(OkHttpClient client, Application application) {
        if (retrofit == null) {


            retrofit = new Retrofit.Builder()
                    .baseUrl(application.getString(R.string.dvcURL))
                    //.client(client)
                    .client(headersInterceptors(application))
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().
                            setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy())))
                    .build();
        }
        return retrofit;
    }

    static OkHttpClient headersInterceptors(Application application) {

        return new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(application))
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(900, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

    }

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }

    private static class BasicAuth implements Interceptor {
        Application application;

        public BasicAuth(Application application) {
            this.application = application;

        }


        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            String creds = Credentials.basic("alsoai0a", "ABeer28121997@121");

            Headers headers = chain.request().headers().newBuilder()
                    .add("Authorization", creds)
                    .add("Content-Type", application.getResources().getString(R.string.Content_Type))
                    .add("Accept", application.getResources().getString(R.string.accept))
                    .add("sap-client", application.getResources().getString(R.string.sapclient_25))
                    .build();

            Request request = chain.request().newBuilder().headers(headers).build();

            return chain.proceed(request);
        }
    }


}
