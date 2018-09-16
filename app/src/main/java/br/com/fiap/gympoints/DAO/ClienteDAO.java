package br.com.fiap.gympoints.DAO;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.toolbox.Volley;

import br.com.fiap.gympoints.Model.Cliente;

public class ClienteDAO {
    private RequestQueue requestQueue;
    private StringRequest request;
    private JsonObjectRequest jsonRequest;
    private Context context;
    private View v;
    // Endpoints da salesforce
    private String epQuery = "/services/data/v43.0/query/?";
    private String epCliente = "/services/data/v43.0/sobjects/Cliente__c";

    // Construtor para quando usarmos snackbar(dentro de um setOnClickListener)
    public ClienteDAO(Context context, View v){
        this.context = context;
        this.v = v;
    }

    //Contrutor para quando não precisar de snackbar
    public ClienteDAO(Context context){
        this.context = context;
    }

    public void login(final Cliente cliente){
        requestQueue = Volley.newRequestQueue(context);
        String query = "q=SELECT senha__c FROM Cliente__c WHERE email__c ='" + cliente.getEmail() + "'";
        request = new com.android.volley.toolbox.StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    JSONObject jsonResponse = new JSONObject(response);
                    // Pega o vetor que contém as keys email e senha obtidas na query no SF
                    JSONArray auth = jsonResponse.getJSONArray("records");
                    if (auth.length() == 0) {
                        throw new Exception("Login ou senha inválidos");
                    }

                    // Acessa a única posição disponível(Email único por usuário)
                    JSONObject data = auth.getJSONObject(0);
                    // Pega a senha do JSON final, já que filtramos a query pelo email e retornou um Cliente
                    String senhaSF = data.getString("senha__c");

                    // Valida Senha inserida com a recebida pelo SF
                    if (cliente.getSenha().toString().equals(senhaSF)) {
                        context.startActivity(new Intent(context, br.com.fiap.gympoints.MainActivity.class));
                    } else {
                        Snackbar.make(v, "Login ou senha inválidos", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
                Log.d("Success query", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + Conexao.accessToken);
                header.put("Content-Type", "application/json");
                return header;
            }
        };


        requestQueue.add(request);
    }

    public void registrar(final Cliente cliente) {
        requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("nome__c", cliente.getNome());
            jsonObject.put("cpf__c", cliente.getCpf());
            jsonObject.put("email__c", cliente.getEmail());
            jsonObject.put("senha__c", cliente.getSenha());
            jsonObject.put("idade__c", cliente.getIdade());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonRequest = new JsonObjectRequest(Request.Method.POST, Conexao.instanceURL+epCliente, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Snackbar.make(v, "Registro concluído com sucesso", Snackbar.LENGTH_SHORT).show();
                Log.d("Response", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = null;
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.d("Status_Code", statusCode.toString());
                //Pega o body e converte para String
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Log.d("json_error", jsonError);
                }
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                //Configuração solicitada pela SF para ter acesso ao SF
                header.put("Authorization", "Bearer "+ Conexao.accessToken);
                //Define o tipo de conteúdo que está sendo enviado
                header.put("Content-Type", "application/json");
                return header;
            }

        };

        requestQueue.add(jsonRequest);
    }
}
