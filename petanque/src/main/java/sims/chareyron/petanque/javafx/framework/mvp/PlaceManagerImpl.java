package sims.chareyron.petanque.javafx.framework.mvp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class PlaceManagerImpl implements PlaceManager {

	private Stage stage;
	private Map<Presenter, Boolean> boundPresentersMap = new HashMap<>();
	private Presenter currentPresenter;
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
		Presenter<? extends View> defaultPresenter = presenters.parallelStream()
				.filter(p -> p.getToken() != null && p.getToken().equals("default")).findFirst().get();

		if (!boundPresentersMap.containsKey(defaultPresenter)) {
			boundPresentersMap.put(defaultPresenter, true);
		} else {
			boundPresentersMap.put(defaultPresenter, false);
		}
		currentPresenter = defaultPresenter;
		revealInScene(defaultPresenter);

	}

	@Override
	public void revealDefaultPlace() {
		revealPlace("default");

	}

	private void revealInScene(Presenter<? extends View> defaultPresenter) {
		Scene scene = new Scene(defaultPresenter.getView().getParent());
		defaultPresenter.reveal();
		stage.setScene(scene);
	}

}
