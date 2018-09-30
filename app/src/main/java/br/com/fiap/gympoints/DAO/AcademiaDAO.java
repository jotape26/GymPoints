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
    private String queryAcad = "q=SELECT Name, Id, nome__c, email__c, endereço__c FROM Academia__c ";

    // Criar método para obter lista de academias com queryAcad, basear-se no getFrequencias presente no MainActivity

    // Criar método para vincular cliente a academia, olhar método de comprarProduto no clienteDAO
    // Passos: 
    // Pegar Id da academia (Obtido com o epQuery+queryAcad)
    // Fazer uma requisição PATCH passando um JSON
    // contendo { Academia__c: Id }, 
    // para o endereço epCliente+"/Id"

    // Obs: 
    // As ações de vincular-se a academia será feita pela lista de academias (exatamento o mesmo processo de comprar um produto)
    // Será adicionado um campo de academia no perfil que possuirá o nome da academia atual vinculada 

}
