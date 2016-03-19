package sims.chareyron.petanque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;

@Lazy
@SpringBootApplication
public class PetanqueManagerApplication extends AbstractJavaFxSpring {

	@Autowired
	PlaceManager placeManager;

	public static void main(String[] args) {
		launchApp(PetanqueManagerApplication.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		placeManager.setStage(stage);
		placeManager.revealDefaultPlace();
		placeManager.init();
	}

}
