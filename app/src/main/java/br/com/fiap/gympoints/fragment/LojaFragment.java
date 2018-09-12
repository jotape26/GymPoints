package br.com.fiap.gympoints.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fiap.gympoints.LoginActivity;
import br.com.fiap.gympoints.MainActivity;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.ProdutoAdapter;
import br.com.fiap.gympoints.model.Produto;

/**
 * A simple {@link Fragment} subclass.
 */
public class LojaFragment extends Fragment {

    private ArrayList<Produto> produtos;
    private ListView listView;
    private View myView;
    private AlertDialog alerta;

    public LojaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_loja, container, false);
        listView = myView.findViewById(R.id.lista_loja);

        produtos = new ArrayList<Produto>();
        produtos.add(new Produto("iFood", "Desconto de 10%", 100));
        produtos.add(new Produto("Uber", "Desconto de 5%", 50));

        ProdutoAdapter adapter = new ProdutoAdapter(getContext(), produtos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater2 = getLayoutInflater();
                builder.setCustomTitle(inflater2.inflate(R.layout.compra, null));

                String msg = "Confirmar compra do produto "+produtos.get(position).getNome()+
                        " por "+produtos.get(position).getPreco()+" pontos?";
                builder.setMessage(msg);

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), getCompra(produtos.get(position)),Toast.LENGTH_LONG).show();
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
}
