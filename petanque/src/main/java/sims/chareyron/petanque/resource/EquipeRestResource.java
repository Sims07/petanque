package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Equipe;

public interface EquipeRestResource extends CrudRepository<Equipe, Long> {

}
