package sims.chareyron.petanque.restservice;

import java.util.List;
import java.util.Set;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.Tournoi;

public interface PetanqueRestService {
	Tournoi createTournoi(Tournoi aTournoi);

	Tournoi getTournoiById(Long aId);

	Tournoi getTournoiById(Long aId, Boolean isPrincipal);

	Set<Equipe> getAllTeamByTournoi(Long idTournoi);

	String isPetanqueRestStarted();

	Tournoi addEquipeToTournoi(Long aIdTournoi, Equipe aEquipeToAdd);

	Tournoi removeEquipeToTournoi(Long aIdTournoi, Long aEquipeId);

	Tournoi marquerLeScoreDeLaPartie(Long aTournoiId, Long aSousTournoiId,
			Long aTourId, Long aPartieId, Partie partieAModifier,
			boolean aIsPrincipal);

	Tournoi tirageAuSort(Long aId, Boolean isPrincipa);

	List<Tournoi> getAll();
}
