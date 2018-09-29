package br.com.fiap.gympoints.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.gympoints.DAO.ClienteDAO;
import br.com.fiap.gympoints.DAO.Conexao;
import br.com.fiap.gympoints.Model.Cliente;
import br.com.fiap.gympoints.Model.Produto;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.ProdutoAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LojaFragment extends Fragment {

    private List<Produto> produtos = new ArrayList<>();
    private ListView listView;
    private View myView;
    private AlertDialog alerta;
    private com.android.volley.RequestQueue requestQueue;
    private String epQuery = "/services/data/v43.0/query/?q=";
    private StringRequest request;


    public LojaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_loja, container, false);
        listView = myView.findViewById(R.id.lista_loja);
        getProdutos();
        ProdutoAdapter adapter = new ProdutoAdapter(getContext(), produtos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater2 = getLayoutInflater();
                builder.setCustomTitle(inflater2.inflate(R.layout.compra, null));
                final Produto produto = produtos.get(position);

                String msg = "Confirmar compra do produto " + produto.getNome() +
                        " por "+produto.getPreco()+" pontos?";
                builder.setMessage(msg);

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClienteDAO dao = new ClienteDAO(getContext(), view);
                        dao.comprarProduto(produto);
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

    public String getCompra(Produto produto){
        StringBuilder sb = new StringBuilder();
        sb.append("Compra do produto ");
        sb.append(produto.getNome());
        sb.append(" com sucesso!");
        return sb.toString();
    }

    public void getProdutos(){
        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getContext());
        String query;
        query = "SELECT Id, nome__c, valor__c, descricao__c, porcentagem__c FROM Desconto__c";

        request = new StringRequest(com.android.volley.Request.Method.GET, Conexao.instanceURL + epQuery + query, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray records = jsonResponse.getJSONArray("records");
                    for (int i = 0; i < records.length(); i++) {
                        try {
                            String nome = records.getJSONObject(i).getString("nome__c");
                            Integer preco = records.getJSONObject(i).getInt("valor__c");
                            String descricao = records.getJSONObject(i).getString("descricao__c");
                            String idSF = records.getJSONObject(i).getString("Id");
                            Log.d("idSF", idSF);
                            String desconto = "\nDesconto de " + records.getJSONObject(i).getInt("porcentagem__c") + "%";
                            if (descricao == null) {
                                descricao += desconto;
                            } else {
                                descricao = desconto;
                            }
                            Produto produto = new Produto(idSF, nome, descricao, preco);
                            Log.d("Name",idSF);
                            produtos.add(produto);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(!produtos.isEmpty()){
                        ProdutoAdapter adapter = new ProdutoAdapter(getContext(), produtos);
                        listView.setAdapter(adapter);
                    }else{
                        Log.d("Size else",produtos.size() + "");
                    }
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
