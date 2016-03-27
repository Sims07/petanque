package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.model.EquipeModel;
import sims.chareyron.petanque.javafx.model.NextEvent;
import sims.chareyron.petanque.javafx.model.PetanqueEvent;
import sims.chareyron.petanque.javafx.model.PreviousEvent;

public abstract class AbstractJoueursPresenter extends AbstractWidgetPresenter<IJoueursView>
		implements JoueursPresenter, JoueursUiHandlers {
	/**
	 * The data as an observable list of Persons.
	 */
	private ObservableList<EquipeModel> equipes = FXCollections.observableArrayList();

	protected EquipeModel equipeModel;
	protected BooleanProperty tirageAuSortEnable;

	@Autowired
	TournoiFS tournoiFS;

	public AbstractJoueursPresenter(JoueursView view) {
		super(view);
		this.view = view;
		tirageAuSortEnable = new SimpleBooleanProperty();
	}

	@EventListener
	public void handlePreviousActionEvent(PetanqueEvent<PreviousEvent> creationEvent) {
		chargerLesEquipes(equipes);
	}

	@EventListener
	public void handleNextActionEvent(PetanqueEvent<NextEvent> creationEvent) {
		chargerLesEquipes(equipes);
	}

	@Override
	public void onBind() {
		equipeModel = new EquipeModel();
		ajouterTypeEquipe(equipeModel);
		chargerLesEquipes(equipes);
		getView().setEquipes(equipes);
		getView().setViewBindings();
		getView().setEquipe(equipeModel);
		getView().setUiHandlers(this);
		getView().setTirageAuSortEnable(tirageAuSortEnable);
	}

	abstract void chargerLesEquipes(ObservableList<EquipeModel> equipes);

	abstract void ajouterTypeEquipe(EquipeModel equipeModel);

	@Override
	public void onAjouterEquipeClicked() {
		tournoiFS.addEquipeToTournoi(equipeModel.map());
		chargerLesEquipes(equipes);
	}

	@Override
	public void onDeleteEquipeClicked(EquipeModel equipe) {
		tournoiFS.removeEquipeFromTournoi(equipe.map());
		chargerLesEquipes(equipes);
	}

	@Override
	public void onReveal() {
		// TODO Auto-generated method stub

	}
}
