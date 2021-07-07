package com.example.listing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.listing.Material.Material;
import com.example.listing.Plan.Plan;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class MaterialAsyncTask extends AsyncTask<String, String, String> {
    String id1, resp;
    Context ctx;
    NetworkResponseListener networkResponseListener;
    ArrayList<Plan> reqs = new ArrayList<>();
    ArrayList<Material> mats = new ArrayList<>();

    MaterialAsyncTask(Context ctx, NetworkResponseListener networkResponseListener){
        this.ctx = ctx;
        this.networkResponseListener = networkResponseListener;
    }



    @Override
    protected String doInBackground(String... strings) {
        //input = prof.getText().toString();

        HttpClientBuilder b = HttpClientBuilder.create();
        BasicCookieStore httpCookieStore = new BasicCookieStore();
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, (X509HostnameVerifier) hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connmgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        final HttpUriRequest mHttpRequest = new HttpGet("https://dp4.aramco.com.sa/irj/portal/");
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("alsaliat", "Newday.1");
        Header header = null;
        try {
            header = new BasicScheme().authenticate(credentials, mHttpRequest);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        mHttpRequest.addHeader("Content-Type", "application/json; charset=utf-8");
        mHttpRequest.addHeader("Accept", "application/json");
        mHttpRequest.addHeader("X-CSRF-Token", "Fetch");
        mHttpRequest.addHeader("sap-client", "025");
        mHttpRequest.addHeader(header);

        b.setSslcontext(sslContext);
        b.setConnectionManager(connmgr);
        b.setDefaultCookieStore(httpCookieStore);
        b.setUserAgent("Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, Like Gecko) Version/4.0 Safari/534.13");
        HttpClient client = b.build();
        Header header2 = null;

//                final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPLG_SRV/RigSet");
//        final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('alsaliat')");
//        final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('yahiao0a')");
       // Log.i("userid", user + " and then some");




//        final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/sap/ZU_UPHR_MAIN_SRV/StagedItemsSet");
        final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/sap/ZU_UPHR_MAIN_SRV/LoadingPlanSet?$expand=PlanToItems&sap-client=025");

//        final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('"+ user + "')");
//                final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('"+  input + "')");
        //final HttpUriRequest odataHttpRequest = new HttpGet("https://example.com");
        try {
            header2 = new BasicScheme().authenticate(credentials, odataHttpRequest);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        odataHttpRequest.addHeader(header2);
        odataHttpRequest.addHeader("Content-Type", "application/json; charset=utf-8");
        odataHttpRequest.addHeader("Accept", "application/json");
        odataHttpRequest.addHeader(header);


        try{
            HttpResponse response = client.execute(mHttpRequest);

            if(response.getStatusLine().getStatusCode() == 200){
                Log.i("response dp4",response.getStatusLine().getReasonPhrase());
                HttpResponse response1 = client.execute(odataHttpRequest);
                Log.i("response-StatusCode", response1.getStatusLine().getReasonPhrase() + "," + response1.getStatusLine().getStatusCode());
                Header[] headers = odataHttpRequest.getAllHeaders();
                BufferedReader in = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                try{

                    while ((line = in.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    resp = sb.toString();
                    Log.d("response", resp);

                }catch (IOException e){
                    e.printStackTrace();
                }

                Log.i("response", sb.toString() + "");

            }
            return resp;
        }catch (IOException e){
            e.printStackTrace();
            Log.i("error IO", e.toString());
        }


        return resp;
    }

    @Override
    protected void onPostExecute(String resp){

        if(resp!=null) {
            try {
                JSONObject jsonObj = new JSONObject(resp);
                JSONObject data = jsonObj.getJSONObject("d");
                JSONArray results = data.getJSONArray("results");



                for(int i = 0 ; i < results.length() ; i++) {
                    JSONObject res = results.getJSONObject(i);
                    String stat = res.getString("ZuphrStatus");
                    String rq_name = res.getString("ZuphrLpid");
                    String time = res.getString("ZuphrLptime");
                    String date = res.getString("ZuphrLpdate");
                    String vessel = res.getString("ZuphrVessel");
                    String driver = "";
                    JSONObject materObj = res.getJSONObject("PlanToItems");
                    JSONArray mater = materObj.getJSONArray("results");
                    for (int j = 0; j < mater.length(); j++) {
                        JSONObject mat = mater.getJSONObject(j);
                        String mater_name = mat.getString("ZuphrShortxt");
                        String mater_quan = mat.getString("ZuphrQuan");
                        String mater_driver = "";
                        String mater_vehicle = "";
                        mats.add(new Material(mater_name, mater_quan, false, mater_driver, mater_vehicle, false));
                    }
                    reqs.add(new Plan(stat, "Plan# " + rq_name, time, date, vessel, driver, mats));
                }
                networkResponseListener.successData(reqs);






//                JSONObject jsonObj = new JSONObject(resp);
//                JSONObject data = jsonObj.getJSONObject("d");
//                JSONArray results = data.getJSONArray("results");
//                JSONObject res = results.getJSONObject(0);
//
//
//                id1 = res.getString("ZuphrLpid");
////                String nameOf;
////                nameOf = results.getString(8);
//                networkResponseListener.successData(results);


            } catch (JSONException e) {
                networkResponseListener.failedData();
                Toast.makeText(ctx, "Username not found or incorrect", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }



    }



}
