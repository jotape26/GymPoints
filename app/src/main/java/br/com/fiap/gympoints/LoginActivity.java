package br.com.fiap.gympoints;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.Model.Cliente;
import br.com.fiap.gympoints.DAO.ClienteDAO;

public class LoginActivity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtCadastrar;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Conexao.autenticacao(getApplicationContext());

        txtEmail = findViewById(R.id.lblUsuario);
        txtSenha = findViewById(R.id.txtSenha);
        txtCadastrar = findViewById(R.id.btnCadastrar);
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
                   ClienteDAO dao = new ClienteDAO(getApplicationContext(), v);
                   dao.login(cliente);
                }
            }
        });

    }
}
