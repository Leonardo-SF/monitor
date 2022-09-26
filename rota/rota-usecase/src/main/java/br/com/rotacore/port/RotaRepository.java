package br.com.rotacore.port;

import br.com.rotadomain.Rota;

public interface RotaRepository {

    Rota findByTopico(String topico);
}
