package br.com.fiap.gympoints;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Conexao {
    public static String accessToken;
    public static String instanceURL;
    private static final String URL = "https://login.salesforce.com/services/oauth2/token";
    private RequestQueue requestQueue;
    private StringRequest request;

    public void autenticacao(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Log.d("Response", response);

                try {

                    //Converte response para objeto manipulável
                    JSONObject jsonResponse = new JSONObject(new String(response));
                    //Define token de acesso para utilizar no app a cada requisição
                    Conexao.accessToken = jsonResponse.getString("access_token");
                    Conexao.instanceURL = jsonResponse.getString("instance_url");


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error.Response", "Erro na request");

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
