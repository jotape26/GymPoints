package br.com.fiap.gympoints.Controller;

import java.util.List;

import br.com.fiap.gympoints.Model.Presenca;

public interface ServerCallback {
    void onSuccess(List<Presenca> presencas);
}
