package sims.chareyron.petanque.javafx.framework.mvp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class PlaceManagerImpl implements PlaceManager {

	private Stage stage;

	@Autowired
	private List<Presenter<? extends View>> presenters;

	@Override
	public void init(Stage stage) {
		this.stage = stage;
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.setFullScreen(true);
		stage.show();
	}

	@Override
	public void revealPlace(String token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void revealDefaultPlace() {
		Presenter<? extends View> defaultPresenter = presenters.parallelStream()
				.filter(p -> p.getToken() != null && p.getToken().equals("default")).findFirst().get();

		Scene scene = new Scene(defaultPresenter.getView().getParent());
		defaultPresenter.reveal();
		stage.setScene(scene);

	}

}
