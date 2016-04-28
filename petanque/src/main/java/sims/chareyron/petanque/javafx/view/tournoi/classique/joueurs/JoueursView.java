package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	Button tirageAuSortButton;

	@FXML
	TableView<EquipeModel> equipeTable;

	@FXML
	TableColumn<EquipeModel, Integer> numeroColumn;

	@FXML
	TableColumn<EquipeModel, String> joueur1Column;

	@FXML
	TableColumn<EquipeModel, String> joueur2Column;

	@Override
	public void setInSlot(Slot slot, View view) {

	}

	@Override
	public void setViewBindings() {
		initContextMenu();
		joueur2.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (!creerEquipe.disableProperty().get() && ke.getCode().equals(KeyCode.ENTER)) {
					onAjouterEquipeClicked();
				}

			}
		});
		// Initialize the person table with the two columns.
		numeroColumn.setCellValueFactory(new PropertyValueFactory<EquipeModel, Integer>("numero"));
		joueur1Column.setCellValueFactory(cellData -> cellData.getValue().getJoueur1());
		joueur2Column.setCellValueFactory(cellData -> cellData.getValue().getJoueur2());
		mainPanel.prefWidthProperty().bind(((Pane) mainPanel.getParent()).widthProperty());
		mainPanel.prefHeightProperty().bind(((Pane) mainPanel.getParent()).heightProperty());
	}

	private void initContextMenu() {

		MenuItem mnuDel = new MenuItem("Supprimer");
		mnuDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				getUiHandlers().onDeleteEquipeClicked(equipeTable.getSelectionModel().getSelectedItem());

			}
		});

		equipeTable.setContextMenu(new ContextMenu(mnuDel));

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

	@Override
	public void setTirageAuSortEnable(BooleanProperty enable) {
		tirageAuSortButton.disableProperty().bind(Bindings.not(enable));

	}

	public void onAjouterEquipeClicked() {
		getUiHandlers().onAjouterEquipeClicked();
		// mettre le focus sur le premier nom
		joueur1.setText("");
		joueur2.setText("");
		joueur1.requestFocus();
	}

	public void onTirageAuSortClicked() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(getMessageConfirmationTirageAuSort());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			getUiHandlers().onTirageAuSortClicked();
		}

	}

	protected abstract String getMessageConfirmationTirageAuSort();

}
