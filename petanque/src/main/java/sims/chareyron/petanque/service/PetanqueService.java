package sims.chareyron.petanque.service;

import java.util.List;
import java.util.Set;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.Partition;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tournoi;

public interface PetanqueService {
	Tournoi createTournoi(Tournoi aTournoi);

	Set<Equipe> getAllTeamByTournoi(Long idTournoi);

	List<Tournoi> getAllTournoi();

	String isPetanqueRestStarted();

	Equipe addEquipeToTournoi(Long aIdTournoi, Equipe aEquipeToAdd);

	Tournoi tirageAuSort(Long tournoiId, boolean principal);

	List<Partition> buildPartitions(int nbEquipes);

	Tournoi getTournoiById(Long aId);

	void removeEquipeToTournoi(Long aEquipeId);

	Partie marquerLeScoreDeLaPartie(Long aTournoiId, Long aPartieId, boolean isEquipe1Gagnante, String aScore,
			Long aTourId, boolean aIsPrincipal);

	void jouerLesPartiesForfaits(Long sousTournoi, boolean isPrincipal);

	void calculerLesStatistiques(SousTournoi aSousTournoi, Tournoi aTournoi);

}
