package sims.chareyron.petanque.javafx.framework.mvp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;

@Component
public class PlaceManagerImpl implements PlaceManager {

	private Stage stage;
	@SuppressWarnings("rawtypes")
	private Map<Presenter, Boolean> boundPresentersMap = new HashMap<>();
	private Presenter<? extends View> currentPresenter;
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
		Presenter<?> parentPresenter = null;
		if (!boundPresentersMap.containsKey(defaultPresenter)) {
			boundPresentersMap.put(defaultPresenter, true);
			parentPresenter = onBind(defaultPresenter);

		} else {
			boundPresentersMap.put(defaultPresenter, false);
			defaultPresenter.onReveal();
		}
		currentPresenter = defaultPresenter;
		revealInScene(parentPresenter);

	}

	private Presenter<?> onBind(Presenter<? extends View> presenterToBind) {
		boundPresentersMap.put(presenterToBind, true);
		// appeler on bind sur tous ses enfants
		presenterToBind.childrenPresenter().forEach(p -> p.onBind());
		// reveler dans le parent
		Slot revealedInSlot = presenterToBind.revealedInSlot();
		// trouver ou se trouve le slot
		@SuppressWarnings("rawtypes")
		Presenter<?> parentPresenter = presenters.stream().filter(p -> {
			return p.getSlotList().contains(revealedInSlot);
		}).findFirst().get();
		((AbstractFxmlView) parentPresenter.getView()).getParent();
		parentPresenter.getView().setInSlot(revealedInSlot, presenterToBind.getView());
		return parentPresenter;
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
