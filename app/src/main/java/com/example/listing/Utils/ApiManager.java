package com.example.listing.Utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.listing.R;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class ApiManager {
    private static ApiManager sInstance;
    private HttpClient mClient;

    private  BasicCookieStore httpCookieStore = new BasicCookieStore();


    private ApiManager(Application application){

        mClient = CreateClinet(application);
    }

    public static void initializer(Application application){
        if(sInstance == null){
            synchronized (ApiManager.class){
                sInstance = new ApiManager(application);
            }
        }
    }
    public static ApiManager getInstance(){
        if(sInstance == null){
            throw new IllegalStateException("Get Instance can't be called before initializer");
        }else{
            Log.i("response","here");
            return sInstance;
        }

    }

    public HttpClient getClient() {
        return mClient;
    }
    public void Logout (){
        sInstance=null;
    }

    private HttpClient CreateClinet(Application application) {

        HttpClientBuilder b = HttpClientBuilder.create();

        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                    return true;
                }}).build();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (KeyManagementException keyManagementException) {
            keyManagementException.printStackTrace();
        } catch (KeyStoreException keyStoreException) {
            keyStoreException.printStackTrace();
        }

        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, (X509HostnameVerifier) hostnameVerifier);

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

// allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);

        b.setSslcontext(sslContext);
        b.setConnectionManager(connMgr);
        b.setDefaultCookieStore(httpCookieStore);
        b.setUserAgent(application.getResources().getString(R.string.user_agent));
        HttpClient client = b.build();

        return client;

    }
}