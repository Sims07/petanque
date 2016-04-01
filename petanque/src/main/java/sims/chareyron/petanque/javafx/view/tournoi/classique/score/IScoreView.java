package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.model.SousTournoi;

public interface IScoreView extends View, ViewWithUiHandlers<ScoreUiHandlers> {

	void setSousTournoi(SousTournoi ssTournoi, int tourIndex);

	void setTour(int currentPageIndex, SousTournoi currentSousTournoi);

}
