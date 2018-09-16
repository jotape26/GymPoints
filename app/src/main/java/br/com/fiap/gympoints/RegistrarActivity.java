package br.com.fiap.gympoints;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.Model.Cliente;

public class RegistrarActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonObjectRequest request;
    private EditText txtNome;
    private EditText txtCpf;
    private EditText txtEmail;
    private EditText txtIdade;
    private EditText txtSenha;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        txtNome = findViewById(R.id.txtNome);
        txtCpf = findViewById(R.id.txtCPF);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtIdade = findViewById(R.id.txtIdade);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pega valores do novo cliente
                String nome = txtNome.getText().toString();
                String cpf = txtCpf.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                Integer idade = Integer.parseInt(txtIdade.getText().toString());

                Cliente cliente = new Cliente(nome, cpf, email, senha, idade);
                registrar(cliente);
            }
        });

    }

    private void registrar(final Cliente cliente) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
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

        request = new JsonObjectRequest(Request.Method.POST, Conexao.instanceURL+"/services/data/v43.0/sobjects/Cliente__c", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

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

        requestQueue.add(request);
    }
}
