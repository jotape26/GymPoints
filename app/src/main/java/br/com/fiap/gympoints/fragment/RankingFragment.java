package br.com.fiap.gympoints.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.fiap.gympoints.R;
import br.com.fiap.gympoints.adapter.UsuarioAdapter;
import br.com.fiap.gympoints.Model.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private ArrayList<Usuario> usuarios;
    private ListView listView;
    private View myView;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_ranking, container, false);
        listView = myView.findViewById(R.id.lista_ranking);

        usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario("Aluno Z", 100));
        usuarios.add(new Usuario("Aluno X",50));

        UsuarioAdapter adapter = new UsuarioAdapter(getContext(), usuarios);
        listView.setAdapter(adapter);

        return myView;
    }

}
