package br.com.fiap.gympoints;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {


    private static final String URL = "https://login.salesforce.com/services/oauth2/token";
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        //Autenticação
        autenticacao();
    }

    public void autenticacao(){
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Log.d("Response", response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error.Response", "");

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<String, String>();
                params.put("grant_type", "password");
                params.put("client_id", "3MVG9dZJodJWITSsLl9Gbw5MqpTFalnkZeSE42RMZg6I15UwjjlEqvwgDRnIIOWLYsBtiXTpcXFDuDzLadPSl");
                params.put("client_secret", "8847434933220872042");
                params.put("username", "membro@gympoints.com");
                params.put("password", "userGYMPOINTS7hzngV7xOYqzO7B4mFTW6Mhu0w"); //Password+Security token
                return params;
            }

        };

        requestQueue.add(request);
    }
}
