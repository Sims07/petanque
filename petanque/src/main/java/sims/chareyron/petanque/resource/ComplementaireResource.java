package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Complementaire;

public interface ComplementaireResource extends
		CrudRepository<Complementaire, Long> {

}
