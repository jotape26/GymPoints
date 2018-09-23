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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.volley.toolbox.Volley;

import br.com.fiap.gympoints.Model.Cliente;
import br.com.fiap.gympoints.Model.Frequencia;

public class ClienteDAO {
    public static Cliente clienteAtual = new Cliente();
    public static List<Frequencia> frequencias = new ArrayList<>();
    public static String teste = "";
    public static List<Integer> inteiros = new ArrayList<>();
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

    // Contrutor para quando não precisar de snackbar
    public ClienteDAO(Context context){
        this.context = context;
    }

    public void login(final Cliente cliente){
        requestQueue = Volley.newRequestQueue(context);
        String query = "q=SELECT senha__c, nome__c, cpf__c, email__c, idade__c FROM Cliente__c WHERE email__c ='" + cliente.getEmail() + "'";
        request = new StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {
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
                    String nomeSF  = data.getString("nome__c");
                    String cpfSF = data.getString("cpf__c");
                    String emailSF  = data.getString("email__c");
                    Integer idadeSF = data.getInt("idade__c");
                    ClienteDAO.clienteAtual = new Cliente(nomeSF,  cpfSF,  emailSF,  senhaSF,  idadeSF);
                    // Extraindo attributes porque no atributo url tem o clientID usado para manipular o registro da conta logada
                    JSONObject atributes = data.getJSONObject("attributes");
                    Conexao.clientID = atributes.getString("url").substring(41);
                    Log.d("Client ID", Conexao.clientID);

                    for(int i =0; i < 15; i++){
                        ClienteDAO.inteiros.add(i);
                    }
                    Log.d("ICliente", ClienteDAO.inteiros.size()+"");
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
        // Atualiza as frequencias do cliente Atual;
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

    // Métodos para a Tela de Perfil: 1 e 2
    // Métodos para a Tela de Frequência: 2 com getAll = true

    // 1. Pegar Usuário (Nome e Pontos)

    // 2. Pegar Frequências do SF e seta na variável de Conexão do Cliente atual logado
    // (Parâmetro Boolean getAll para retornar 5 ultimas presenças ou todas)
    public void getFrequencias(){
        requestQueue = Volley.newRequestQueue(context);
        String query;
        query = "q=SELECT dataRegistro__c FROM Frequencia__c";

        request = new StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    JSONObject jsonResponse = new JSONObject(response);
                    // Pega o vetor que contém os registros de Frequencia na query no SF
                    JSONArray records = jsonResponse.getJSONArray("records");
                    if (records.length() == 0) {
                        throw new Exception("Comece a frequentar a academia para ganhar pontos!");
                    }

                    for (int i=0; i < records.length(); i++) {
                        try{
                            SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
                            String string = records.getJSONObject(i).getString("dataRegistro__c");
                            Date data = formatter.parse(string);
                            Frequencia frequencia = new Frequencia(data);
                            ClienteDAO.frequencias.add(frequencia);
                        }catch (Exception e ){
                            e.printStackTrace();
                        }
                    }
                    Log.d("iFrequencia", ClienteDAO.frequencias.get(0).getData().toString()+"");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Error", e.getMessage());
                }
                Log.d("Success query", response);
                Log.d("Anonymous Class",ClienteDAO.frequencias.size()+"");
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
        Log.d("oFrequencia", ClienteDAO.frequencias.size()+"");
        requestQueue.add(request);
    }

    //Métodos para a Tela de Loja

    // 3. ComprarDesconto
    // Subtrair o total de pontos
    // Vincular Cliente com Desconto no SF

}
