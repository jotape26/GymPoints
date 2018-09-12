package br.com.fiap.gympoints;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fiap.gympoints.adapter.PresencaAdapter;
import br.com.fiap.gympoints.fragment.LojaFragment;
import br.com.fiap.gympoints.fragment.PresencaFragment;
import br.com.fiap.gympoints.fragment.RankingFragment;
import br.com.fiap.gympoints.fragment.SobreFragment;
import br.com.fiap.gympoints.model.Presenca;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Presenca> presencas;
    private ListView listView;
    private AlertDialog alerta;
    private AlertDialog alerta_token;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presencas = new ArrayList<Presenca>();
        presencas.add(new Presenca("11/09/2018", 25));
        presencas.add(new Presenca("10/09/2018", 25));
        presencas.add(new Presenca("09/09/2018", 25));

        PresencaAdapter adapter = new PresencaAdapter(MainActivity.this, presencas);
        listView = findViewById(R.id.ultimas_presencas);
        listView.setAdapter(adapter);

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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
}
