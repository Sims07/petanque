package sims.chareyron.petanque.javafx.view.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.property.BooleanProperty;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.controller.ActionMementoFS;
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
	@Autowired
	private ActionMementoFS actionMementoFS;

	private PlaceManager placeManager;

	public interface MyView extends View, ViewWithUiHandlers<HeaderUiHandlers> {
		void setViewBindings(Stage stage);

		void setPreviousEnabled(BooleanProperty enable);

		void setNextEnabled(BooleanProperty enable);
	}

	@Autowired
	public HeaderPresenter(MyView view, PlaceManager placeManager) {
		super(view);
		this.view = view;
		this.placeManager = placeManager;
	}

	@Override
	public void onBind() {
		getView().setUiHandlers(this);
		getView().setViewBindings(placeManager.getStage());
		getView().setNextEnabled(actionMementoFS.isNextEnabled());
		getView().setPreviousEnabled(actionMementoFS.isPreviousEnabled());
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
	public void onPreviousClicked() {
		actionMementoFS.executerPreviousAction();
	}

	@Override
	public void onNextClicked() {
		actionMementoFS.executerNextAction();

	}

	@Override
	public void onTournoiLoadedClicked() {
		// TODO Auto-generated method stub

	}

}
