package sims.chareyron.petanque.javafx.view.accueil;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;

@Component
public class AccueilView extends AbstractFxmlView implements AccueilPresenter.MyView, Initializable {
	@FXML
	Pane body;

	@Override
	public void setInSlot(Slot slot, View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViewBindings(Stage stage) {
		body.prefWidthProperty().bind(stage.widthProperty());
		body.prefHeightProperty().bind(stage.heightProperty());
	}

}
