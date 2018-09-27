package br.com.fiap.gympoints.Controller;

import java.util.List;

import br.com.fiap.gympoints.Model.Presenca;

public class PresencaController implements ServerCallback {

    private List<Presenca> presencas;

    public List<Presenca> getPresencas() {
        return presencas;
    }

    @Override
    public void onSuccess(List<Presenca> presencas) {
        this.presencas = presencas;
    }
}
