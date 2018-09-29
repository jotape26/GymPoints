package br.com.fiap.gympoints;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.fiap.gympoints.Controller.PresencaController;
import br.com.fiap.gympoints.Controller.ServerCallback;
import br.com.fiap.gympoints.DAO.ClienteDAO;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.Model.Presenca;
import br.com.fiap.gympoints.adapter.PresencaAdapter;
import br.com.fiap.gympoints.fragment.LojaFragment;
import br.com.fiap.gympoints.fragment.PresencaFragment;
import br.com.fiap.gympoints.fragment.RankingFragment;
import br.com.fiap.gympoints.fragment.SobreFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Presenca> presencas = new ArrayList<>();
    private TextView txt_usuario;
    private TextView txt_points;
    private ListView listView;
    private AlertDialog alerta;
    private AlertDialog alerta_token;
    private Button cadastrar;
    ClienteDAO dao;
    private RequestQueue requestQueue;
    private String epQuery = "/services/data/v43.0/query/?";
    private String epCliente = "/services/data/v43.0/sobjects/Cliente__c";
    private StringRequest request;
    private JsonObjectRequest jsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = new ClienteDAO(getApplicationContext());
        getPresencas();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ultimas_presencas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txt_usuario = (TextView) findViewById(R.id.txt_usuario);
        TextView txt_points = (TextView) findViewById(R.id.txt_points);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


        txt_usuario.setText("Olá " + ClienteDAO.clienteAtual.getNome());
        txt_points.setText("Você possui "+ ClienteDAO.clienteAtual.getPontos().toString() + " G-Points!");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cadastrar = findViewById(R.id.btn_token);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.token, null));
                builder.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Token adicionado com sucesso!",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alerta_token = builder.create();
                alerta_token.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (id == R.id.nav_presenca) {
            getSupportFragmentManager().beginTransaction().replace(R.id.inicial, new PresencaFragment(),"presenca")
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_ranking) {
            getSupportFragmentManager().beginTransaction().replace(R.id.inicial, new RankingFragment(),"ranking")
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_loja) {
            getSupportFragmentManager().beginTransaction().replace(R.id.inicial, new LojaFragment(),"loja")
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_sobre) {
            getSupportFragmentManager().beginTransaction().replace(R.id.inicial, new SobreFragment(), "sobre")
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_sair) {
            sair();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setCustomTitle(inflater.inflate(R.layout.sair, null));
        builder.setMessage("Deseja sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), br.com.fiap.gympoints.LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta = builder.create();
        alerta.show();
        TextView messageView = (TextView)alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }






    public void getPresencas(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String query;
        query = "q=SELECT dataRegistro__c FROM Frequencia__c WHERE nomeCliente__c='"+ClienteDAO.clienteAtual.getNome()+"'";

        request = new StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {

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

                    if(records.length() != ClienteDAO.clienteAtual.getFrequencia().size() ) {
                        for (int i = 0; i < records.length(); i++) {
                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
                                String string = records.getJSONObject(i).getString("dataRegistro__c");
                                Date data = formatter.parse(string);
                                Presenca p = new Presenca(data);
                                presencas.add(p);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if(!presencas.isEmpty()){
                            PresencaAdapter adapter = new PresencaAdapter(MainActivity.this, presencas);
                            listView.setAdapter(adapter);
                        }else{
                            Log.i("isEmpty",presencas.size() + "");
                        }

                    }

                    ClienteDAO.clienteAtual.setFrequencia(ClienteDAO.clienteAtual.getFrequencia());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Error", e.getMessage());
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
