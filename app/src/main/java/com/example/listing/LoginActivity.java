package com.example.listing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    String input, id1, user;
    NetworkResponseListener networkResponseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login);
        TextView prof = findViewById(R.id.username);

        Log.i("userid3", user+ " yssir");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = prof.getText().toString();
                Log.i("ifnnull", user);
                LoginAsyncTask tasker = new LoginAsyncTask(LoginActivity.this, user);
              //  tasker.execute();
            }
        });

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                input = prof.getText().toString();
//                HttpClientBuilder b = HttpClientBuilder.create();
//                BasicCookieStore httpCookieStore = new BasicCookieStore();
//                SSLContext sslContext = null;
//                try {
//                    sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                        @Override
//                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                            return true;
//                        }
//                    }).build();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (KeyManagementException e) {
//                    e.printStackTrace();
//                } catch (KeyStoreException e) {
//                    e.printStackTrace();
//                }
//
//                HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, (X509HostnameVerifier) hostnameVerifier);
//                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                        .register("https", sslSocketFactory)
//                        .build();
//
//                PoolingHttpClientConnectionManager connmgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//                final HttpUriRequest mHttpRequest = new HttpGet("https://dp4.aramco.com.sa/irj/portal/");
//                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("alsaliat", "Newday.1");
//                Header header = null;
//                try {
//                    header = new BasicScheme().authenticate(credentials, mHttpRequest);
//                } catch (AuthenticationException e) {
//                    e.printStackTrace();
//                }
//                mHttpRequest.addHeader("Content-Type", "application/json; charset=utf-8");
//                mHttpRequest.addHeader("Accept", "application/json");
//                mHttpRequest.addHeader("X-CSRF-Token", "Fetch");
//                mHttpRequest.addHeader("sap-client", "025");
//                mHttpRequest.addHeader(header);
//
//                b.setSslcontext(sslContext);
//                b.setConnectionManager(connmgr);
//                b.setDefaultCookieStore(httpCookieStore);
//                b.setUserAgent("Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, Like Gecko) Version/4.0 Safari/534.13");
//                HttpClient client = b.build();
//                Header header2 = null;
////                final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPLG_SRV/RigSet");
//                final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('alsaliat')");
////                final HttpUriRequest odataHttpRequest = new HttpGet("https://dvc.aramco.com.sa:1443/sap/opu/odata/SAP/ZU_UPHR_MAIN_SRV/UserInfoSet('"+  input + "')");
//                //final HttpUriRequest odataHttpRequest = new HttpGet("https://example.com");
//                try {
//                    header2 = new BasicScheme().authenticate(credentials, odataHttpRequest);
//                } catch (AuthenticationException e) {
//                    e.printStackTrace();
//                }
//                odataHttpRequest.addHeader(header2);
//                odataHttpRequest.addHeader("Content-Type", "application/json; charset=utf-8");
//                odataHttpRequest.addHeader("Accept", "application/json");
//                odataHttpRequest.addHeader(header);
//
//                new Thread(() -> {
//                    try{
//                        HttpResponse response = client.execute(mHttpRequest);
//
//                        if(response.getStatusLine().getStatusCode() == 200){
//                            Log.i("response dp4",response.getStatusLine().getReasonPhrase());
//                            HttpResponse response1 = client.execute(odataHttpRequest);
//                            Log.i("response-StatusCode", response1.getStatusLine().getReasonPhrase() + "," + response1.getStatusLine().getStatusCode());
//                            Header[] headers = odataHttpRequest.getAllHeaders();
//                            BufferedReader in = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
//                            StringBuilder sb = new StringBuilder();
//                            String line = "";
//                            try{
//
//                                while ((line = in.readLine()) != null){
//                                    sb.append(line + "\n");
//                                }
//                            }catch (IOException e){
//                                e.printStackTrace();
//                            }
//                            JSONObject jsonObj  = new JSONObject(String.valueOf(sb));
//                            JSONObject data = jsonObj.getJSONObject("d");
//                            id1 = data.getString("OrgUnit");
//
//
////                            JSONArray profiles = jsonObj.getJSONArray("__metadata");
//                            Log.i("response", sb.toString() + "");
//                            Log.i("idtest", id1 + " grimes ");
////
////                            JSONObject profile = profiles.getJSONObject(0);
////                            String id = profile.getString("id");
//                        }
//                    }catch (IOException | JSONException e){
//                        e.printStackTrace();
//                        Log.i("error IO", e.toString());
//                    }
//                }).start();
//
//
//                Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
////                MainIntent.putExtra("profile",input);
//                MainIntent.putExtra("profile",id1);
//                Toast tst = Toast.makeText(getBaseContext(), id1, Toast.LENGTH_LONG);
//                tst.show();
//                startActivity(MainIntent);
//            }
//        });
    }
}