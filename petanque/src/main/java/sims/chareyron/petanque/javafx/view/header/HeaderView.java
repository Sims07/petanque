package sims.chareyron.petanque.javafx.view.header;

import java.util.List;

import org.springframework.stereotype.Component;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class HeaderView extends AbstractViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {

	@FXML
	MenuBar menuBar;

	@FXML
	Menu menuCharger;

	@FXML
	MenuItem previous;

	@FXML
	MenuItem next;

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

	public void onTournoiLoadedClicked(Long id, String nom) {
		getUiHandlers().onTournoiLoadedClicked(id, nom);
	}

	@Override
	public void setPreviousEnabled(BooleanProperty enable) {
		previous.disableProperty().bind(Bindings.not(enable));

	}

	@Override
	public void setNextEnabled(BooleanProperty enable) {
		next.disableProperty().bind(Bindings.not(enable));

	}

	public void onPrecedentClicked(ActionEvent event) {
		getUiHandlers().onPreviousClicked();
	}

	public void onSuivantClicked() {
		getUiHandlers().onNextClicked();
	}

	@Override
	public void setListeTournoi(List<Tournoi> tournois) {
		menuCharger.getItems().clear();
		tournois.stream().forEach(t -> {
			MenuItem tournoi = new MenuItem(t.getNom());
			tournoi.setId(t.getId().toString());
			tournoi.setOnAction(e -> {
				onTournoiLoadedClicked(t.getId(), t.getNom());
			});
			menuCharger.getItems().add(tournoi);
		});

	}

}
