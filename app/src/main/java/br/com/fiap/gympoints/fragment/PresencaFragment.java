package br.com.fiap.gympoints.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import br.com.fiap.gympoints.DAO.ClienteDAO;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.MainActivity;
import br.com.fiap.gympoints.Model.Produto;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.PresencaAdapter;
import br.com.fiap.gympoints.Model.Presenca;


/**
 * A simple {@link Fragment} subclass.
 */
public class PresencaFragment extends Fragment {

    private ArrayList<Presenca> presencas = new ArrayList<Presenca>();
    private ListView listView;
    private View myView;
    private List<Produto> produtos = new ArrayList<>();
    private com.android.volley.RequestQueue requestQueue;
    private String epQuery = "/services/data/v43.0/query/?q=";
    private StringRequest request;

    public PresencaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_presenca, container, false);

        listView = myView.findViewById(R.id.lista_presenca);
        getPresencas();

        PresencaAdapter adapter = new PresencaAdapter(getContext(), presencas);
        listView.setAdapter(adapter);

        return myView;
    }

    public void getPresencas(){
        requestQueue = Volley.newRequestQueue(getContext());
        String query;
        query = "SELECT dataRegistro__c FROM Frequencia__c WHERE nomeCliente__c='"+ ClienteDAO.clienteAtual.getNome()+"'";

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
                            PresencaAdapter adapter = new PresencaAdapter(getContext(), presencas);
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
