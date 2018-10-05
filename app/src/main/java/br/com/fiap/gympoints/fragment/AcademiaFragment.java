package br.com.fiap.gympoints.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.fiap.gympoints.DAO.ClienteDAO;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.MainActivity;
import br.com.fiap.gympoints.Model.Academia;
import br.com.fiap.gympoints.Model.Presenca;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.AcademiaAdapter;
import br.com.fiap.gympoints.adapter.PresencaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcademiaFragment extends Fragment {

    private ArrayList<Academia> academias;
    private ListView listView;
    private View myView;
    private TextView atual;
    private AlertDialog alerta;
    private com.android.volley.RequestQueue requestQueue;
    private JsonObjectRequest jsonRequest;
    private String epQuery = "/services/data/v43.0/query/?q=";
    private String epCliente = "/services/data/v43.0/sobjects/Cliente__c";
    private String queryAcad = "q=SELECT Name, Id, nome__c, email__c, endereço__c FROM Academia__c ";
    private StringRequest request;

    public AcademiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_academia, container, false);
        listView = myView.findViewById(R.id.lista_academias);
        atual = myView.findViewById(R.id.atual);
        ClienteDAO dao = new ClienteDAO(getContext(), getView());

        String sourceString = "<b>Academia:</b> " +dao.clienteAtual.getAcademia();
        if(dao.clienteAtual.getAcademia() == "Not Found"){
            sourceString = "<b>Você ainda não se <br/>filiou a uma academia</b>";
        }

        atual.setText(Html.fromHtml(sourceString));
        academias = new ArrayList<Academia>();

        getAcademias();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getLayoutInflater();
                builder.setCustomTitle(inflater1.inflate(R.layout.mudar, null));
                String msg = "Deseja trocar para a academia "+academias.get(position).getNome()+" ?";
                builder.setMessage(msg);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        atualizarAcademiaFiliada(academias.get(position));
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
        });

        return myView;
    }

    private void atualizarAcademiaFiliada(final Academia academia) {
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("Academia__c", academia.getIdSf());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JOAO", jsonObject.toString());


        jsonRequest = new JsonObjectRequest(Request.Method.PATCH, Conexao.instanceURL + epCliente + "/" + Conexao.clientID, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Snackbar.make(myView, "Academia alterada com sucesso!", Snackbar.LENGTH_SHORT).show();
                String sourceString = "<b>Academia:</b> " + academia.getNome();
                atual.setText(Html.fromHtml(sourceString));
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + Conexao.accessToken);
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(jsonRequest);
    }

    private void getAcademias() {
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getContext());
        String query;
        query = "SELECT Id,nome__c,email__c,endereco__c FROM Academia__c";

        request = new StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray records = jsonResponse.getJSONArray("records");

                    for(int i = 0; i < records.length(); i++){
                        String sfID = records.getJSONObject(i).getString("Id");
                        String nome = records.getJSONObject(i).getString("nome__c");
                        String email = records.getJSONObject(i).getString("Email__c");
                        String endereco = records.getJSONObject(i).getString("endereco__c");

                        academias.add(new Academia(sfID,nome,email,endereco));
                    }

                    Log.d("lIST", academias.toString());
                    if(!academias.isEmpty()){
                        AcademiaAdapter adapter = new AcademiaAdapter(getContext(), academias);
                        listView.setAdapter(adapter);
                    }else{
                        Toast.makeText(getContext(),"Não foi possivel recuperar a lista de academias. Tente novamente mais tarde",Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                   Log.e("ERROR", e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + Conexao.accessToken);
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(request);
    }

}
