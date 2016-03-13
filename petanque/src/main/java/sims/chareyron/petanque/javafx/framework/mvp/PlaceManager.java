package sims.chareyron.petanque.javafx.framework.mvp;

import javafx.stage.Stage;

public interface PlaceManager {

	void init(Stage stage);

	void revealPlace(String token);

	void revealDefaultPlace();
}
