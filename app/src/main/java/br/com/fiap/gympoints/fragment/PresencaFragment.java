package br.com.fiap.gympoints.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.PresencaAdapter;
import br.com.fiap.gympoints.model.Presenca;


/**
 * A simple {@link Fragment} subclass.
 */
public class PresencaFragment extends Fragment {

    private ArrayList<Presenca> presencas;
    private ListView listView;
    private View myView;

    public PresencaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_presenca, container, false);

        listView = myView.findViewById(R.id.lista_presenca);
        presencas = new ArrayList<Presenca>();
        presencas.add(new Presenca("11/09/2018", 25));
        presencas.add(new Presenca("10/09/2018", 25));
        presencas.add(new Presenca("09/09/2018", 25));
        presencas.add(new Presenca("08/09/2018", 25));
        presencas.add(new Presenca("07/09/2018", 25));
        presencas.add(new Presenca("06/09/2018", 25));

        PresencaAdapter adapter = new PresencaAdapter(getContext(), presencas);
        listView.setAdapter(adapter);

        return myView;
    }

}
