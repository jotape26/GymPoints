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
import br.com.fiap.gympoints.model.Presenca;
import br.com.fiap.gympoints.model.Produto;

public class PresencaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Presenca> presencas;

    public PresencaAdapter(Context context, ArrayList<Presenca> presencas) {
        this.context = context;
        this.presencas = presencas;
    }

    @Override
    public int getCount() {
        return presencas.size();
    }

    @Override
    public Object getItem(int position) {
        return presencas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Presenca presenca = presencas.get(position);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.presenca, parent, false);
        ((TextView)convertView.findViewById(R.id.presenca_dia)).setText(presenca.getDia());
        ((TextView)convertView.findViewById(R.id.presenca_ponto)).setText(presenca.getPontos().toString());
        return convertView;
    }
}
