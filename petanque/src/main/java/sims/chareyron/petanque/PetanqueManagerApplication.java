package sims.chareyron.petanque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import javafx.scene.Scene;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.view.MainView;

@Lazy
@SpringBootApplication
public class PetanqueManagerApplication extends AbstractJavaFxSpring {

	@Autowired
	MainView projectsView;

	public static void main(String[] args) {
		launchApp(PetanqueManagerApplication.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Petanque");
		stage.setScene(new Scene(projectsView.getView()));
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.setFullScreen(true);
		stage.show();
	}

}
