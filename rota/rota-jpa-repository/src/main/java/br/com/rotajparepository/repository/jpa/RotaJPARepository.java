package br.com.rotajparepository.repository.jpa;

import br.com.rotacore.port.RotaRepository;
import br.com.rotadomain.Rota;
import org.springframework.stereotype.Component;

@Component
public class RotaJPARepository implements RotaRepository {

    private final RotaDbRepository repository;

    public RotaJPARepository(RotaDbRepository repository) {
        this.repository = repository;
    }

    @Override
    public Rota findByTopico(String topico) {
        return repository.findByTopico(topico).toRota();
    }
}
