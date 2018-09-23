package br.com.fiap.gympoints.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.Model.Produto;

public class ProdutoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Produto> produtos;

    public ProdutoAdapter(Context context, ArrayList<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.produto, parent, false);
        ((TextView)convertView.findViewById(R.id.txt_nome)).setText(produto.getNome());
        ((TextView)convertView.findViewById(R.id.txt_desc)).setText(produto.getDescricao());
        ((TextView)convertView.findViewById(R.id.txt_preco)).setText(produto.getPreco().toString());
        return convertView;
    }
}
