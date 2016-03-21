package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public abstract class JoueursView extends AbstractViewWithUiHandlers<JoueursUiHandlers> implements IJoueursView {
	@FXML
	Button creerEquipe;

	@FXML
	Pane mainPanel;
	@FXML
	Pane tablePanel;
	@FXML
	TitledPane titledTablePane;
	@FXML
	TextField joueur1;

	@FXML
	TextField joueur2;

	@FXML
	TableView<EquipeModel> equipeTable;

	@FXML
	TableColumn<EquipeModel, String> joueur1Column;

	@FXML
	TableColumn<EquipeModel, String> joueur2Column;

	@Override
	public void setInSlot(Slot slot, View view) {

	}

	@Override
	public void setViewBindings() {
		// Initialize the person table with the two columns.
		joueur1Column.setCellValueFactory(cellData -> cellData.getValue().getJoueur1());
		joueur2Column.setCellValueFactory(cellData -> cellData.getValue().getJoueur2());
		mainPanel.prefWidthProperty().bind(((Pane) mainPanel.getParent()).widthProperty());
		mainPanel.prefHeightProperty().bind(((Pane) mainPanel.getParent()).heightProperty());
	}

	@Override
	public void setEquipes(ObservableList<EquipeModel> equipes) {
		equipeTable.setItems(equipes);

	}

	@Override
	public void setEquipe(EquipeModel equipe) {
		joueur1.textProperty().bindBidirectional(equipe.getJoueur1());
		joueur2.textProperty().bindBidirectional(equipe.getJoueur2());
		creerEquipe.disableProperty()
				.bind(Bindings.or(joueur1.textProperty().isEmpty(), joueur2.textProperty().isEmpty()));
	}

	public void onAjouterEquipeClicked() {
		getUiHandlers().onAjouterEquipeClicked();
	}

}
