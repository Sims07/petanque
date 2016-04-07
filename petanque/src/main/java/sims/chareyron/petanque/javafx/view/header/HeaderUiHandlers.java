package sims.chareyron.petanque.javafx.view.header;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;

public interface HeaderUiHandlers extends UiHandlers {
	void onTournoiClassiqueCreationClicked();

	void onPreviousClicked();

	void onNextClicked();

	void onTournoiLoadedClicked(Long idTournoi, String nom);
}
