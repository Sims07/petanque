package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.beans.factory.annotation.Autowired;

import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.model.SousTournoi;

public abstract class AbstractScorePresenter extends AbstractWidgetPresenter<IScoreView>
		implements ScorePresenter, ScoreUiHandlers {

	@Autowired
	protected TournoiFS tournoiFS;
	protected SousTournoi currentSousTournoi;

	public AbstractScorePresenter(IScoreView view) {
		super(view);
	}

	@Override
	public void setSousTournoi(SousTournoi sousTournoi) {
		this.currentSousTournoi = sousTournoi;
		getView().setSousTournoi(currentSousTournoi);
	}

	@Override
	public void onTourClicked(int currentPageIndex) {
		getView().setTour(currentPageIndex, currentSousTournoi);

	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
	}

	@Override
	public void onReveal() {

	}

}
