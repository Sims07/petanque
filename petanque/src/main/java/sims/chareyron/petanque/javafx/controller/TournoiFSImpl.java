package sims.chareyron.petanque.javafx.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.Partition;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tournoi;
import sims.chareyron.petanque.service.PetanqueService;

@Service
public class TournoiFSImpl implements TournoiFS {

	private Tournoi currentTournoi;

	@Autowired
	private PetanqueService petanqueService;

	@Autowired
	private ActionMementoFS actionMementoFS;

	public Tournoi createTournoi(Tournoi aTournoi) {
		currentTournoi = petanqueService.createTournoi(aTournoi);
		return currentTournoi;
	}

	public Set<Equipe> getAllTeamByTournoi(Long idTournoi) {
		return petanqueService.getAllTeamByTournoi(idTournoi);
	}

	public List<Tournoi> getAllTournoi() {
		return petanqueService.getAllTournoi();
	}

	public String isPetanqueRestStarted() {
		return petanqueService.isPetanqueRestStarted();
	}

	public Tournoi addEquipeToTournoi(Long aIdTournoi, Equipe aEquipeToAdd) {
		currentTournoi = petanqueService.addEquipeToTournoi(aIdTournoi, aEquipeToAdd);
		return currentTournoi;
	}

	public Tournoi tirageAuSort(Long tournoiId, boolean principal) {
		currentTournoi = petanqueService.tirageAuSort(tournoiId, principal);
		return currentTournoi;
	}

	public List<Partition> buildPartitions(int nbEquipes) {
		return petanqueService.buildPartitions(nbEquipes);
	}

	public Tournoi getTournoiById(Long aId) {
		currentTournoi = petanqueService.getTournoiById(aId);
		return currentTournoi;
	}

	public void refreshTournoi() {
		getTournoiById(currentTournoi.getId());
	}

	public void removeEquipeToTournoi(Long aEquipeId) {
		petanqueService.removeEquipeToTournoi(aEquipeId);
		refreshTournoi();
	}

	public Partie marquerLeScoreDeLaPartie(Long aTournoiId, Long aPartieId, boolean isEquipe1Gagnante, String aScore,
			Long aTourId, boolean aIsPrincipal) {
		return petanqueService.marquerLeScoreDeLaPartie(aTournoiId, aPartieId, isEquipe1Gagnante, aScore, aTourId,
				aIsPrincipal);
	}

	public void jouerLesPartiesForfaits(Long sousTournoi, boolean isPrincipal) {
		petanqueService.jouerLesPartiesForfaits(sousTournoi, isPrincipal);
	}

	public void calculerLesStatistiques(SousTournoi aSousTournoi, Tournoi aTournoi) {
		petanqueService.calculerLesStatistiques(aSousTournoi, aTournoi);
	}

	@Override
	public Tournoi createTournoiClassique() {
		currentTournoi = new Tournoi();
		currentTournoi.setNom("A definir");
		currentTournoi = petanqueService.createTournoi(currentTournoi);
		return currentTournoi;
	}

}