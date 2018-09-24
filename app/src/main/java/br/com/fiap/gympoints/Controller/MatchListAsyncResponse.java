package br.com.fiap.gympoints.Controller;

import java.util.List;

import br.com.fiap.gympoints.Model.Frequencia;

public interface MatchListAsyncResponse {
    void processFinish(List<Frequencia> frequencias);
}
