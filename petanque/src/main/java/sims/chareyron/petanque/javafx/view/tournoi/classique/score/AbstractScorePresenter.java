package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import sims.chareyron.petanque.javafx.controller.ExecutorFS;
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
	@Autowired
	protected ExecutorFS executorFS;
	protected SousTournoi currentSousTournoi;
	protected Tour currentTour;
	protected int pageIndex = -1;
	private SimpleStringProperty filter;

	private SimpleBooleanProperty filterDisplayPartieEnded;

	public AbstractScorePresenter(IScoreView view) {
		super(view);
		filter = new SimpleStringProperty("");
		filterDisplayPartieEnded = new SimpleBooleanProperty(true);
	}

	@Override
	public void setSousTournoi(SousTournoi sousTournoi) {
		this.currentSousTournoi = sousTournoi;
		if (currentTour == null && currentSousTournoi.getActiveNbTour() > 0) {
			pageIndex = currentSousTournoi.getActiveNbTour() - 1;
			currentTour = currentSousTournoi.getTours().get(pageIndex);
		} else {
			pageIndex = -1;
		}
		getView().setSousTournoi(currentSousTournoi, pageIndex);
	}

	@Override
	public void onTourClicked(int currentPageIndex) {
		pageIndex = currentPageIndex;
		executorFS.executeLongOp((Long tournoiId) -> {
			return tournoiFS.getLoadedTournoi();
		}, null, (Tournoi t) -> {
			currentSousTournoi = getSousTournoi(tournoiFS.getLoadedTournoi());
			currentTour = currentSousTournoi.getTours().get(currentPageIndex);
			getView().setTour(currentPageIndex, currentSousTournoi);
			return t;
		}, "Chargement du tour " + (currentPageIndex + 1));

	}

	@Override
	public void onBind() {
		pageIndex = -1;
		getView().setUiHandlers(this);
		getView().setFilter(filter, filterDisplayPartieEnded);
	}

	@Override
	public void onGagnantSelected(Partie aPartie, Equipe equipeGagnante) {

		Partie updated = tournoiFS.marquerScorePartie(aPartie, equipeGagnante, currentTour.getId(), isPrincipal());

		getView().setScore(updated);
	}

	@Override
	public void onResetEquipeGagnante(Partie currentPartie) {
		Partie updated = tournoiFS.resetEquipeGagnante(currentPartie, currentTour.getId(), isPrincipal());
		getView().setScore(updated);
	}

	protected abstract SousTournoi getSousTournoi(Tournoi tournoi);

	protected abstract boolean isPrincipal();

	@Override
	public void onReveal() {
		currentTour = null;
	}

}
