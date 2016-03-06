package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Partie;

public interface PartieResource extends CrudRepository<Partie, Long> {

}
