package sims.chareyron.petanque.javafx.view.header;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;
import sims.chareyron.petanque.javafx.view.tournoi.classique.tableau.PreferenceUiHandlers;

public interface HeaderUiHandlers extends UiHandlers, PreferenceUiHandlers {
	void onTournoiClassiqueCreationClicked();

	void onPreviousClicked();

	void onNextClicked();

	void onTournoiLoadedClicked(Long idTournoi, String nom);

	public void onPrincipalAffichageClicked();

	public void onComplementaireClicked();

	public void onPreferencesClicked(boolean principal);
}
