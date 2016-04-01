package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;

public interface ScoreUiHandlers extends UiHandlers, PartieUiHandlers {

	void onTourClicked(int currentPageIndex);

}
