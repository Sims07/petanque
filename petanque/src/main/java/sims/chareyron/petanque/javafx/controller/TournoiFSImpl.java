package sims.chareyron.petanque.javafx.controller;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sims.chareyron.petanque.javafx.model.AbstractAction;
import sims.chareyron.petanque.javafx.model.Action;
import sims.chareyron.petanque.javafx.model.EquipeModel;
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

	public Equipe addEquipeToTournoi(Long aIdTournoi, Equipe aEquipeToAdd) {
		return petanqueService.addEquipeToTournoi(aIdTournoi, aEquipeToAdd);

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

	public Tournoi refreshTournoi() {
		currentTournoi = getTournoiById(currentTournoi.getId());
		return currentTournoi;
	}

	public void removeEquipeToTournoi(Long aEquipeId) {
		petanqueService.removeEquipeToTournoi(aEquipeId);
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

	@Override
	public Tournoi addEquipeToTournoi(Equipe equipe) {

		Equipe addedEquipe = addEquipeToTournoi(currentTournoi.getId(), equipe);
		actionMementoFS.ajouterAction(new Action() {
			private Long equipeId = addedEquipe.getId();

			@Override
			public void rollback() {
				removeEquipeToTournoi(equipeId);

			}

			@Override
			public void execute() {
				equipe.setId(null);
				equipe.getJoueurs().stream().forEach(j -> j.setId(null));
				equipeId = addEquipeToTournoi(currentTournoi.getId(), equipe).getId();

			}

			@Override
			public String idExecute() {
				return "AddTeam-" + equipeId;
			}

			@Override
			public String idRollback() {
				return "RemoveTeam-" + equipeId;

			}
		});
		return refreshTournoi();

	}

	Function<Equipe, EquipeModel> mapperEquipeToModel = new Function<Equipe, EquipeModel>() {

		@Override
		public EquipeModel apply(Equipe t) {
			return t.map();
		}

	};

	@Override
	public List<EquipeModel> getEquipesPrincipal() {
		currentTournoi = getTournoiById(currentTournoi.getId());
		return currentTournoi.getEquipes().parallelStream().filter(e -> {
			return e.isInscritDansLePrincipal();
		}).map(mapperEquipeToModel).collect(Collectors.<EquipeModel> toList());
	}

	@Override
	public List<EquipeModel> getEquipesComplementaire() {
		currentTournoi = getTournoiById(currentTournoi.getId());
		return currentTournoi.getEquipes().parallelStream().filter(e -> {
			return e.isInscritDansLeComplementaire();
		}).map(mapperEquipeToModel).collect(Collectors.<EquipeModel> toList());
	}

	@Override
	public Tournoi tirageAuSortPrincipal() {
		currentTournoi = tirageAuSort(currentTournoi.getId(), true);
		return currentTournoi;

	}

	@Override
	public Tournoi tirageAuSortComplementaire() {
		currentTournoi = tirageAuSort(currentTournoi.getId(), false);
		return currentTournoi;

	}

	@Override
	public Tournoi removeEquipeFromTournoi(final Equipe equipeToAdd) {
		removeEquipeToTournoi(equipeToAdd.getId());
		actionMementoFS.ajouterAction(new AbstractAction() {
			private Long equipeId = equipeToAdd.getId();
			private Equipe equipe = equipeToAdd;

			@Override
			public void rollback() {
				Equipe toAdd = equipe;
				toAdd.setId(null);
				toAdd.getJoueurs().stream().forEach(j -> j.setId(null));
				equipe = addEquipeToTournoi(currentTournoi.getId(), toAdd);
				equipeId = equipe.getId();

			}

			@Override
			public void execute() {
				removeEquipeToTournoi(equipeId);

			}

			@Override
			public String idExecute() {
				return "RemoveTeam-" + equipeId;
			}

			@Override
			public String idRollback() {
				return "AddTeam-" + equipeId;
			}

		});
		return refreshTournoi();

	}

}
