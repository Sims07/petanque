package sims.chareyron.petanque.javafx.view.tournoi.classique;

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

@Component
public class TournoiClassiquePresenter extends AbstractPresenter<TournoiClassiquePresenter.MyView>
		implements TournoiUiHandlers {

	public interface MyView extends View, ViewWithUiHandlers<TournoiUiHandlers> {
		void setViewBindings(Stage stage);
	}

	private PlaceManager placeManager;

	@Autowired
	public TournoiClassiquePresenter(MyView view, PlaceManager placeManager) {
		super();
		this.view = view;
		this.placeManager = placeManager;
	}

	@Override
	public String getToken() {
		return Token.TOKEN_TOURNOI_CLASSIQUE;
	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
		getView().setViewBindings(placeManager.getStage());

	}

	public Slot revealedInSlot() {
		return MainPresenter.BODY_SLOT;
	}

	@Override
	public void onReveal() {
		System.out.println("Main presenter on start");
	}
}