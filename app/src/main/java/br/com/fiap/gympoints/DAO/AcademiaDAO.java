package br.com.fiap.gympoints.DAO;

import android.content.Context;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

public class AcademiaDAO {
    private RequestQueue requestQueue;
    private StringRequest request;
    private JsonObjectRequest jsonRequest;
    private Context context;
    private View v;
    // Endpoints da salesforce
    private String epCliente = "/services/data/v43.0/sobjects/Cliente__c";
    private String epQuery = "/services/data/v43.0/query/?";
    private String queryAcad = "q=SELECT Name, nome__c, email__c, endereço__c FROM Academia__c ";

    // Criar método para obter lista de academias com queryAcad, basear-se no

    // Criar método para vincular cliente a academia
    // Pegar Name(Id) da academia (Obtido com o queryAcad)
    // Fazer uma requisição PATCH passando
    // com Id do ClienteAtual em epCliente+"/Id"
    // e um JSON { Academia__c: Name }
    // O Name é o Id padrão da academia no SF

}
