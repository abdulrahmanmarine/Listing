package com.example.listing.Utils;

import android.app.Application;

import com.example.listing.R;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

public class RestApiClient {

    private static RestApiClient instance;
    private static Retrofit retrofit;
    private static List<String> credns;
    private static String kkey;
    private final RetrofitInterface retrofitInterface;


    private RestApiClient(Application application) {
        retrofitInterface = Login(application);
    }

    public static void initializer(Application application, List<String> creds, String key) {
        if (instance == null) {
            synchronized (RestApiClient.class) {
                credns = creds;
                instance = new RestApiClient(application);
                kkey = key;


            }
        }
    }

    public static RestApiClient getInstance(Application application) {
        if (instance == null) {
            instance = new RestApiClient(application);
        }
        return instance;

    }

    static OkHttpClient headersInterceptors(Application application) {

        //   String[] newDesc1=credns.get(0).split("=");
        //    String[] test=newDesc1[1].split(";path");

        return new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(application))
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        return Arrays.asList(createNonPersistentCookie(test[0]));
//                    }
//                })
                .connectTimeout(900, TimeUnit.SECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor(application))
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

    }


    public static Cookie createNonPersistentCookie(String key) {
        return new Cookie.Builder()
                .domain("aramco.com.sa")
                .path("/")
                .name("MYSAPSSO2")
                .value(key)
                .httpOnly()
                .secure()
                .build();
    }

    public static RetrofitInterface Login(Application application) {
        return CreateClient(application).create(RetrofitInterface.class);
    }

    public static Retrofit CreateClient(Application application) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(application.getString(R.string.dvcURL))
                    .client(headersInterceptors(application))
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().
                            setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy())))
                    .build();
        }
        return retrofit;
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


            Headers headers = chain.request().headers().newBuilder()
                    .add("Content-Type", application.getResources().getString(R.string.Content_Type))
                    .add("Accept", application.getResources().getString(R.string.accept))
                    .add("Authorization", kkey)
                    .add("sap-client", application.getResources().getString(R.string.sapclient_25))
                    .add("User-Agent", application.getResources().getString(R.string.user_agent)).build();

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

            final Request copy = chain.request().newBuilder().build();
            final Buffer buffer = new Buffer();

            if (copy.body() != null) {
                copy.body().writeTo(buffer);
            }


            return originalResponse;
        }
    }


}
