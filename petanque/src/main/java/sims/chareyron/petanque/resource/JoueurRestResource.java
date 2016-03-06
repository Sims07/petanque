package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Joueur;

public interface JoueurRestResource extends CrudRepository<Joueur, Long> {

}
