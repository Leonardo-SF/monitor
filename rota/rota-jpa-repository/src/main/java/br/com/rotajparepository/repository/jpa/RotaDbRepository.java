package br.com.rotajparepository.repository.jpa;

import br.com.rotajparepository.repository.model.RotaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotaDbRepository extends JpaRepository<RotaDb, String> {

    RotaDb findByTopico(String topico);
}
