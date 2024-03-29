package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;

public interface IScoreView extends View, ViewWithUiHandlers<ScoreUiHandlers> {

	void setSousTournoi(SousTournoi ssTournoi, int tourIndex);

	void setTour(int currentPageIndex, SousTournoi currentSousTournoi);

	void setScore(Partie p);

	void setFilter(SimpleStringProperty filter, SimpleBooleanProperty filterDisplayPartieEnded);

}
