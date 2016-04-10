package sims.chareyron.petanque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.controller.TournoiFS;
import sims.chareyron.petanque.javafx.framework.mvp.PlaceManager;

@Lazy
@SpringBootApplication
public class PetanqueManagerApplication extends AbstractJavaFxSpring {

	@Autowired
	PlaceManager placeManager;
	@Autowired
	TournoiFS tournoiFS;
	public static Stage MAIN_STAGE;

	public static void main(String[] args) {
		launchApp(PetanqueManagerApplication.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		MAIN_STAGE = stage;
		placeManager.setStage(stage);
		placeManager.revealDefaultPlace();
		placeManager.init();
		/*
		 * Principal pr = tournoiFS.getAllSavedTournoi().get(0).getPrincipal();
		 * placeManager.init(); Tableau root = new Tableau();
		 * 
		 * Scene scene = new Scene(root); root.drawTableau(pr);
		 * stage.setScene(scene);
		 */

	}

}
