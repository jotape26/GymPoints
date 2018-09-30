package br.com.fiap.gympoints.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fiap.gympoints.Model.Academia;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.Model.Produto;
import br.com.fiap.gympoints.Model.Usuario;

public class AcademiaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Academia> academias;

    public AcademiaAdapter(Context context, ArrayList<Academia> academias) {
        this.context = context;
        this.academias = academias;
    }

    @Override
    public int getCount() {
        return academias.size();
    }

    @Override
    public Object getItem(int position) {
        return academias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Academia academia = academias.get(position);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.academia, parent, false);
        ((TextView)convertView.findViewById(R.id.ac_nome)).setText(academia.getNome());
        return convertView;
    }
}
