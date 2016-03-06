package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Tour;

public interface TourResource extends CrudRepository<Tour, Long> {

}
