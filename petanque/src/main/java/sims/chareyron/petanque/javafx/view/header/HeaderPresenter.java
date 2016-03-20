package sims.chareyron.petanque.javafx.view.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.javafx.view.Token;

@Component
public class HeaderPresenter extends AbstractWidgetPresenter<HeaderPresenter.MyView> implements HeaderUiHandlers {
	@Autowired
	private TournoiFS tournoiFS;
	private PlaceManager placeManager;

	public interface MyView extends View, ViewWithUiHandlers<HeaderUiHandlers> {
		void setViewBindings(Stage stage);
	}

	@Autowired
	public HeaderPresenter(MyView view, PlaceManager placeManager) {
		super();
		this.view = view;
		this.placeManager = placeManager;
	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
		getView().setViewBindings(placeManager.getStage());

	}

	@Override
	public void onReveal() {

	}

	@Override
	public void onTournoiClassiqueCreationClicked() {
		tournoiFS.createTournoiClassique();
		placeManager.revealPlace(Token.TOKEN_TOURNOI_CLASSIQUE);

	}

	@Override
	public void onTournoiLoadedClicked() {
		// TODO Auto-generated method stub

	}

}
