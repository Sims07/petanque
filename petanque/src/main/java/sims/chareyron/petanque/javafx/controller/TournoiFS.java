package sims.chareyron.petanque.javafx.controller;

import java.util.List;

import sims.chareyron.petanque.javafx.model.EquipeModel;
import sims.chareyron.petanque.model.Tournoi;

public interface TournoiFS {
	Tournoi createTournoiClassique();

	Tournoi addEquipeToTournoi(EquipeModel equipe);

	List<EquipeModel> getEquipesPrincipal();

	List<EquipeModel> getEquipesComplementaire();

}
