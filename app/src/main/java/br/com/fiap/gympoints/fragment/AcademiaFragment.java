package br.com.fiap.gympoints.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fiap.gympoints.Model.Academia;
import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.AcademiaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcademiaFragment extends Fragment {

    private ArrayList<Academia> academias;
    private ListView listView;
    private View myView;
    private TextView atual;
    private AlertDialog alerta;

    public AcademiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_academia, container, false);
        listView = myView.findViewById(R.id.lista_academias);
        atual = myView.findViewById(R.id.atual);

        academias = new ArrayList<Academia>();
        academias.add(new Academia("1", "Academia X", "academiax@email.com", "Rua X"));
        academias.add(new Academia("2", "Academia Y", "academiay@email.com", "Rua Y"));
        academias.add(new Academia("3", "Academia Z", "academiaz@email.com", "Rua Z"));

        AcademiaAdapter adapter = new AcademiaAdapter(getContext(), academias);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getLayoutInflater();
                builder.setCustomTitle(inflater1.inflate(R.layout.mudar, null));
                String msg = "Deseja trocar para a academia "+academias.get(position).getNome()+" ?";
                builder.setMessage(msg);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        atual.setText(academias.get(position).getNome());
                        Snackbar.make(myView, "Academia alterada com sucesso!", Snackbar.LENGTH_SHORT).show();
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

}
