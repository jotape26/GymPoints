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
import br.com.fiap.gympoints.model.Produto;
import br.com.fiap.gympoints.model.Usuario;

public class UsuarioAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Usuario> usuarios;

    public UsuarioAdapter(Context context, ArrayList<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Usuario usuario = usuarios.get(position);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.posicao, parent, false);
        ((TextView)convertView.findViewById(R.id.rnk_nome)).setText(usuario.getNome());
        ((TextView)convertView.findViewById(R.id.rnk_pontos)).setText(usuario.getPontos().toString());
        return convertView;
    }
}
