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

	public Stage stage;
	@SuppressWarnings("rawtypes")
	private Map<Presenter, Boolean> boundPresentersMap = new HashMap<>();
	private Presenter<? extends View> currentPresenter;
	@Autowired
	private List<Presenter<? extends View>> presenters;

	@Override
	public void init() {

		stage.setResizable(true);
		stage.centerOnScreen();
		stage.setFullScreen(true);
		stage.show();
	}

	@Override
	public void revealPlace(String token) {
		Presenter<? extends View> presenterWithToken = presenters.parallelStream()
				.filter(p -> p.getToken() != null && p.getToken().equals(token)).findFirst().get();
		Presenter<?> parentPresenter = null;
		if (!boundPresentersMap.containsKey(presenterWithToken)) {
			boundPresentersMap.put(presenterWithToken, true);
			parentPresenter = onBind(presenterWithToken);

		} else {
			boundPresentersMap.put(presenterWithToken, false);
			parentPresenter = onReveal(presenterWithToken);
		}
		currentPresenter = presenterWithToken;
		revealInScene(parentPresenter);

	}

	private Presenter<?> onReveal(Presenter<? extends View> presenterToReveal) {

		// appeler on bind sur tous ses enfants
		presenterToReveal.childrenPresenter().forEach(p -> p.onReveal());
		// reveler dans le parent
		Slot revealedInSlot = presenterToReveal.revealedInSlot();
		// trouver ou se trouve le slot
		Presenter<?> parentPresenter = presenters.stream().filter(p -> {
			return p.getSlotList().contains(revealedInSlot);
		}).findFirst().get();
		// initialiser la vue java FX
		((AbstractFxmlView) parentPresenter.getView()).getParent();
		// appeler on reveal sur tout ses enfants
		parentPresenter.onReveal();
		parentPresenter.childrenPresenter().forEach(p -> p.onReveal());
		parentPresenter.getView().setInSlot(revealedInSlot, presenterToReveal.getView());
		return parentPresenter;

	}

	private Presenter<?> onBind(Presenter<? extends View> presenterToBind) {
		boundPresentersMap.put(presenterToBind, true);
		// appeler on bind sur tous ses enfants
		presenterToBind.childrenPresenter().forEach(p -> p.onBind());
		// reveler dans le parent
		Slot revealedInSlot = presenterToBind.revealedInSlot();
		// trouver ou se trouve le slot
		Presenter<?> parentPresenter = presenters.stream().filter(p -> {
			return p.getSlotList().contains(revealedInSlot);
		}).findFirst().get();
		((AbstractFxmlView) parentPresenter.getView()).getParent();
		// appeler on bind sur tout ses enfants
		parentPresenter.onBind();
		parentPresenter.childrenPresenter().forEach(p -> {
			// init java fx view
			((AbstractFxmlView) p.getView()).getParent();
			p.onBind();
		});
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

	@Override
	public Stage getStage() {
		return stage;
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;

	}

}
