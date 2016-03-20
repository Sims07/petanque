package sims.chareyron.petanque.javafx.view.header;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;

@Component
public class HeaderView extends AbstractViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {

	@FXML
	MenuBar menuBar;

	@FXML
	AnchorPane mainPanel;

	@Override
	public void setInSlot(Slot slot, View view) {

	}

	@Override
	public void setViewBindings(Stage stage) {

		mainPanel.prefWidthProperty().bind(stage.widthProperty());

	}

	public void onTournoiCreationClassiqueClicked() {
		getUiHandlers().onTournoiClassiqueCreationClicked();
	}

	public void onTournoiLoadedClicked() {
		getUiHandlers().onTournoiLoadedClicked();
	}

}
