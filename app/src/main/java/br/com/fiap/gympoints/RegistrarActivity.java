package br.com.fiap.gympoints;

import android.content.Intent;
import android.os.AsyncTask;
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

import br.com.fiap.gympoints.DAO.ClienteDAO;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.Model.Cliente;

public class RegistrarActivity extends AppCompatActivity {
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
                ClienteDAO dao = new ClienteDAO(getApplicationContext(), findViewById(android.R.id.content));
                dao.registrar(cliente);
            }
        });

    }
}
