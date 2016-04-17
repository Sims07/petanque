package sims.chareyron.petanque.javafx.view.header;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.beans.property.BooleanProperty;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.controller.ActionMementoFS;
import sims.chareyron.petanque.javafx.controller.ExecutorFS;
import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.javafx.model.RefreshTournoiEvent;
import sims.chareyron.petanque.javafx.view.Token;
import sims.chareyron.petanque.javafx.view.tournoi.classique.tableau.IPreferencesView;
import sims.chareyron.petanque.model.Complementaire;
import sims.chareyron.petanque.model.PreferenceAffichage;
import sims.chareyron.petanque.model.Principal;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class HeaderPresenter extends AbstractWidgetPresenter<HeaderPresenter.MyView> implements HeaderUiHandlers {
	private Tournoi currentTournoi;
	@Autowired
	private TournoiFS tournoiFS;
	@Autowired
	private ExecutorFS executorFS;
	@Autowired
	private ActionMementoFS actionMementoFS;

	private PlaceManager placeManager;
	private IPreferencesView prefView;

	public interface MyView extends View, ViewWithUiHandlers<HeaderUiHandlers> {
		void setViewBindings(Stage stage);

		void setPreviousEnabled(BooleanProperty enable);

		void setNextEnabled(BooleanProperty enable);

		void setListeTournoi(List<Tournoi> tournois);

		void setLoadedTournoi(Tournoi loaded);

		void setDisplayPrincipalTournoi(Principal principal);

		void setDisplayComplementaireTournoi(Complementaire principal);

		void setDisplayPreferencesTournoi(Tournoi currentTournoi);

		void setUpdateDisplayPrincipalTournoi(Principal principal);

		void setUpdateDisplayComplementaireTournoi(Complementaire complementaire);

	}

	@Autowired
	public HeaderPresenter(MyView view, IPreferencesView prefView, PlaceManager placeManager) {
		super(view);
		this.view = view;
		this.placeManager = placeManager;
		this.prefView = prefView;
	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
		getView().setViewBindings(placeManager.getStage());
		getView().setNextEnabled(actionMementoFS.isNextEnabled());
		getView().setPreviousEnabled(actionMementoFS.isPreviousEnabled());
		getView().setListeTournoi(tournoiFS.getAllSavedTournoi());
		((AbstractFxmlView) prefView).getParent();
		prefView.setUiHandlers(this);
	}

	@Override
	public void onReveal() {
		getView().setListeTournoi(tournoiFS.getAllSavedTournoi());
	}

	@Override
	public void onTournoiClassiqueCreationClicked() {
		updateCurrentTournoi(tournoiFS.createTournoiClassique());

		placeManager.revealPlace(Token.TOKEN_TOURNOI_CLASSIQUE);

	}

	@Override
	public void onPreviousClicked() {
		actionMementoFS.executerPreviousAction();
	}

	@Override
	public void onNextClicked() {
		actionMementoFS.executerNextAction();

	}

	@Override
	public void onTournoiLoadedClicked(Long idTournoi, String nom) {

		executorFS.executeLongOp((Long tournoiId) -> {
			return tournoiFS.loadTournoiById(tournoiId);
		}, idTournoi, (Tournoi t) -> {
			placeManager.revealPlace(Token.TOKEN_TOURNOI_CLASSIQUE);
			updateCurrentTournoi(t);
			return t;
		}, "Chargement du tournoi " + nom);
	}

	private void updateCurrentTournoi(Tournoi t) {
		this.currentTournoi = t;
		getView().setLoadedTournoi(currentTournoi);
	}

	@EventListener
	public void onRefreshedTournoi(RefreshTournoiEvent evt) {
		updateCurrentTournoi(evt.getRefreshedTournoi());
		if (currentTournoi.getPrincipal().isTirageAuSortFait()) {
			getView().setUpdateDisplayPrincipalTournoi(currentTournoi.getPrincipal());
		}
		if (currentTournoi.getComplementaire().isTirageAuSortFait()) {
			getView().setUpdateDisplayComplementaireTournoi(currentTournoi.getComplementaire());
		}
	}

	@Override
	public void onPrincipalAffichageClicked() {
		getView().setDisplayPrincipalTournoi(currentTournoi.getPrincipal());

	}

	@Override
	public void onComplementaireClicked() {
		getView().setDisplayComplementaireTournoi(currentTournoi.getComplementaire());

	}

	@Override
	public void onPreferencesClicked(boolean principal) {
		PreferenceAffichage pref = principal ? currentTournoi.getPrincipal().getPreferenceAffichage()
				: currentTournoi.getComplementaire().getPreferenceAffichage();
		prefView.setPreferences(pref);
		prefView.setShowInDialog();

	}

	@Override
	public void onPreferenceChanged(PreferenceAffichage pref) {
		tournoiFS.savePreference(pref);

	}

}
