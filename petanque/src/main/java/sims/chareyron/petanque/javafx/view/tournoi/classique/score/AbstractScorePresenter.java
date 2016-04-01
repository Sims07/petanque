package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.beans.factory.annotation.Autowired;

import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;
import sims.chareyron.petanque.model.Tournoi;

public abstract class AbstractScorePresenter extends AbstractWidgetPresenter<IScoreView>
		implements ScorePresenter, ScoreUiHandlers {

	@Autowired
	protected TournoiFS tournoiFS;
	protected SousTournoi currentSousTournoi;
	protected Tour currentTour;
	protected int pageIndex;

	public AbstractScorePresenter(IScoreView view) {
		super(view);
	}

	@Override
	public void setSousTournoi(SousTournoi sousTournoi) {
		this.currentSousTournoi = sousTournoi;
		if (currentTour == null && currentSousTournoi.getActiveNbTour() > 0) {
			pageIndex = currentSousTournoi.getActiveNbTour() - 1;
			currentTour = currentSousTournoi.getTours().get(pageIndex);
		}
		getView().setSousTournoi(currentSousTournoi, pageIndex);
	}

	@Override
	public void onTourClicked(int currentPageIndex) {
		pageIndex = currentPageIndex;
		currentTour = currentSousTournoi.getTours().get(currentPageIndex);
		getView().setTour(currentPageIndex, currentSousTournoi);

	}

	@Override
	public void onBind() {
		pageIndex = -1;
		getView().setUiHandlers(this);
	}

	@Override
	public void onGagnantSelected(Partie aPartie, Equipe equipeGagnante) {

		Tournoi tournoi = tournoiFS.marquerScorePartie(aPartie, equipeGagnante, currentTour.getId(), isPrincipal());
		currentSousTournoi = getSousTournoi(tournoi);
		getView().setSousTournoi(currentSousTournoi, pageIndex);
	}

	protected abstract SousTournoi getSousTournoi(Tournoi tournoi);

	protected abstract boolean isPrincipal();

	@Override
	public void onReveal() {
		currentTour = null;
	}

}
