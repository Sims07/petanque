package sims.chareyron.petanque.javafx.controller;

import java.util.List;

import sims.chareyron.petanque.javafx.model.EquipeModel;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Tournoi;

public interface TournoiFS {

	Tournoi removeEquipeFromTournoi(Equipe equipeModel);

	Tournoi createTournoiClassique();

	Tournoi addEquipeToTournoi(Equipe equipe);

	List<EquipeModel> getEquipesPrincipal();

	List<EquipeModel> getEquipesComplementaire();

	Tournoi tirageAuSortPrincipal();

	Tournoi tirageAuSortComplementaire();
}
