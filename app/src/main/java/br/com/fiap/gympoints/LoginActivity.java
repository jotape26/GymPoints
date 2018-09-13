package br.com.fiap.gympoints;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.gympoints.Model.Cliente;

public class LoginActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtCadastrar;
    private Button btnEntrar;
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Conexao conexao = new Conexao();
        conexao.autenticacao(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        txtEmail = findViewById(R.id.txtUsuario);
        txtSenha = findViewById(R.id.txtSenha);
        txtCadastrar = findViewById(R.id.txtCadastrar);
        btnEntrar = findViewById(R.id.btnEntrar);



        txtCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrarActivity.class));

            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Cliente cliente;
                cliente = new Cliente(txtEmail.getText().toString(), txtSenha.getText().toString());
                if(cliente != null) {
                    request = new StringRequest(Request.Method.GET, Conexao.instanceURL + "/services/data/v43.0/query/?q=SELECT senha__c FROM Cliente__c WHERE email__c ='" + cliente.getEmail() + "'", new Response.Listener<String>() {
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
                                    startActivity(new Intent(LoginActivity.this, Main2Activity.class));
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
            }
        });

    }
}
