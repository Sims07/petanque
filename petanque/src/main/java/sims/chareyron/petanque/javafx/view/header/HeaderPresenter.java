package sims.chareyron.petanque.javafx.view.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;
import sims.chareyron.petanque.javafx.framework.mvp.View;

@Component
public class HeaderPresenter extends AbstractWidgetPresenter<HeaderPresenter.MyView> {
	private PlaceManager placeManager;

	public interface MyView extends View {
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
		getView().setViewBindings(placeManager.getStage());

	}

	@Override
	public void onReveal() {
		// TODO Auto-generated method stub

	}

}
