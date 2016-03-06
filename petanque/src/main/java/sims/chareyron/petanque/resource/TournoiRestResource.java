package sims.chareyron.petanque.resource;

import org.springframework.data.repository.CrudRepository;

import sims.chareyron.petanque.model.Tournoi;

public interface TournoiRestResource extends CrudRepository<Tournoi, Long> {
	// @Query("select t from Tournoi t "
	// + "left join fetch t.principal p left join fetch t.complementaire c"
	// + " left join fetch p.tours left join fetch c.tours "
	// + "where t.id = ?1")
	// Tournoi findTournoi(Long aId);
}
