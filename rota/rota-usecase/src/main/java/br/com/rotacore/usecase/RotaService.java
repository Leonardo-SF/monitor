package br.com.rotacore.usecase;

import br.com.rotacore.port.RotaRepository;
import br.com.rotadomain.Rota;

public class RotaService {

    private final RotaRepository rotaRepository;

    public RotaService(RotaRepository rotaRepository) {
        this.rotaRepository = rotaRepository;
    }

    public Rota findRotaByTopic(String topic) {
        return this.rotaRepository.findByTopico(topic);
    }
}
