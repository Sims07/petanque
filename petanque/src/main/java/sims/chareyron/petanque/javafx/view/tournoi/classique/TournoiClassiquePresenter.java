package sims.chareyron.petanque.javafx.view.tournoi.classique;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.javafx.view.MainPresenter;
import sims.chareyron.petanque.javafx.view.Token;
import sims.chareyron.petanque.javafx.view.tournoi.TournoiUiHandlers;
import sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs.JoueurPrincipalPresenter;
import sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs.JoueursComplementairePresenter;

@Component
public class TournoiClassiquePresenter extends AbstractPresenter<TournoiClassiquePresenter.MyView>
		implements TournoiUiHandlers {

	public final static Slot PRINCIPAL_JOUEUR_SLOT = new Slot("PRINCIPAL_JOUEUR_SLOT");
	public final static Slot COMPLEMENTAIRE_JOUEUR_SLOT = new Slot("COMPLEMENTAIRE_JOUEUR_SLOT");
	public final static Slot INFO_SLOT = new Slot("INFO_SLOT");
	public final static Slot PRINCIPAL_TOURNOI_SLOT = new Slot("PRINCIPAL_TOURNOI_SLOT");
	public final static Slot COMPLENTAIRE_TOURNOI_SLOT = new Slot("COMPLENTAIRE_TOURNOI_SLOT");

	public interface MyView extends View, ViewWithUiHandlers<TournoiUiHandlers> {
		void setViewBindings(Stage stage);
	}

	private PlaceManager placeManager;
	private JoueursComplementairePresenter joueursComplementairePresenter;
	private JoueurPrincipalPresenter joueurPrincipalPresenter;

	@Autowired
	public TournoiClassiquePresenter(MyView view, JoueurPrincipalPresenter joueurPrincipalPresenter,
			JoueursComplementairePresenter joueursComplementairePresenter, PlaceManager placeManager) {
		super();
		this.view = view;
		this.placeManager = placeManager;
		this.joueurPrincipalPresenter = joueurPrincipalPresenter;
		this.joueursComplementairePresenter = joueursComplementairePresenter;
	}

	@Override
	public String getToken() {
		return Token.TOKEN_TOURNOI_CLASSIQUE;
	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
		getView().setViewBindings(placeManager.getStage());
		setInSlot(COMPLEMENTAIRE_JOUEUR_SLOT, joueursComplementairePresenter);
		setInSlot(PRINCIPAL_JOUEUR_SLOT, joueurPrincipalPresenter);
	}

	public Slot revealedInSlot() {
		return MainPresenter.BODY_SLOT;
	}

	@Override
	public List<Slot> getSlotList() {

		return Arrays.asList(COMPLEMENTAIRE_JOUEUR_SLOT, COMPLENTAIRE_TOURNOI_SLOT, PRINCIPAL_JOUEUR_SLOT,
				PRINCIPAL_TOURNOI_SLOT, INFO_SLOT);
	}

	@Override
	public void onReveal() {
		System.out.println("Main presenter on start");
	}
}
