package br.com.fiap.gympoints.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.fiap.gympoints.Model.Presenca;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.Model.Presenca;
import br.com.fiap.gympoints.Model.Produto;

public class PresencaAdapter extends BaseAdapter {

    private Context context;
    private List<Presenca> presencas;

    public PresencaAdapter(Context context, List<Presenca> presencas) {
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
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        String pontosString = presenca.getPontos() + " Pontos";

        convertView = inflater.inflate(R.layout.presenca, parent, false);
        ((TextView)convertView.findViewById(R.id.presenca_dia)).setText(format.format(presenca.getDia()));
        ((TextView)convertView.findViewById(R.id.presenca_ponto)).setText(pontosString);
        return convertView;
    }
}
