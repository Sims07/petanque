package sims.chareyron.petanque.javafx.view.tournoi.classique;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.view.tournoi.TournoiUiHandlers;

@Component
public class TournoiClassiqueView extends AbstractViewWithUiHandlers<TournoiUiHandlers>
		implements TournoiClassiquePresenter.MyView {
	@FXML
	AnchorPane mainPanel;

	@FXML
	AnchorPane principalJoueursPanel;

	@FXML
	AnchorPane principalTournoiPanel;

	@FXML
	AnchorPane complementaireTournoiPanel;

	@FXML
	AnchorPane complementaireJoueursPanel;

	@FXML
	AnchorPane infoPanel;

	@Override
	public void setInSlot(Slot slot, View view) {
		Pane pane = null;
		if (TournoiClassiquePresenter.COMPLEMENTAIRE_JOUEUR_SLOT.equals(slot)) {
			pane = complementaireJoueursPanel;
		} else if (TournoiClassiquePresenter.COMPLENTAIRE_TOURNOI_SLOT.equals(slot)) {
			pane = complementaireTournoiPanel;
		} else if (TournoiClassiquePresenter.PRINCIPAL_JOUEUR_SLOT.equals(slot)) {
			pane = principalJoueursPanel;
		} else if (TournoiClassiquePresenter.PRINCIPAL_TOURNOI_SLOT.equals(slot)) {
			pane = principalTournoiPanel;
		} else if (TournoiClassiquePresenter.INFO_SLOT.equals(slot)) {
			pane = infoPanel;
		}
		pane.getChildren().clear();
		pane.getChildren().add(view.getParent());

	}

	@Override
	public void setViewBindings(Stage stage) {
		mainPanel.prefWidthProperty().bind(((Pane) mainPanel.getParent()).widthProperty());
		mainPanel.prefHeightProperty().bind(((Pane) mainPanel.getParent()).heightProperty());
	}

}
