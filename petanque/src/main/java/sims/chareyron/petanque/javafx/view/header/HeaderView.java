package sims.chareyron.petanque.javafx.view.header;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sims.chareyron.petanque.PetanqueManagerApplication;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.view.tournoi.classique.tableau.Tableau;
import sims.chareyron.petanque.model.Complementaire;
import sims.chareyron.petanque.model.Principal;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class HeaderView extends AbstractViewWithUiHandlers<HeaderUiHandlers>
		implements HeaderPresenter.MyView, Initializable {
	private SimpleBooleanProperty principalAffichageEnable = new SimpleBooleanProperty();
	private SimpleBooleanProperty complementaireAffichageEnable = new SimpleBooleanProperty();
	@FXML
	MenuBar menuBar;
	@FXML
	MenuItem principalAffichage;
	@FXML
	MenuItem complemetaireAffichage;
	@FXML
	MenuItem preferencesAffichagePrincipal;
	@FXML
	MenuItem preferencesAffichageComplementaire;
	@FXML
	Menu menuCharger;

	@FXML
	MenuItem previous;
	Scene sceneTournoiPrincipal;
	Scene sceneTournoiComplemetaire;
	Stage dialogTournoiPrincipal;
	Stage dialogTournoiComplementaire;
	Tableau principalTableau;
	Tableau complementaireTableau;
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

	public void onPrincipalAffichageClicked() {
		getUiHandlers().onPrincipalAffichageClicked();
	}

	public void onComplementaireClicked() {
		getUiHandlers().onComplementaireClicked();

	}

	public void onPreferencesPrincipalClicked() {
		getUiHandlers().onPreferencesClicked(true);
	}

	public void onPreferencesComplemetaireClicked() {

		getUiHandlers().onPreferencesClicked(false);
	}

	@Override
	public void setLoadedTournoi(Tournoi loaded) {
		principalAffichageEnable.set(loaded.getPrincipal().isTirageAuSortFait());
		complementaireAffichageEnable.set(loaded.getComplementaire().isTirageAuSortFait());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		principalAffichage.disableProperty().bind(Bindings.not(principalAffichageEnable));
		complemetaireAffichage.disableProperty().bind(Bindings.not(complementaireAffichageEnable));

		principalTableau = new Tableau();
		dialogTournoiPrincipal = new Stage();
		dialogTournoiPrincipal.setResizable(true);
		dialogTournoiPrincipal.initStyle(StageStyle.DECORATED);
		dialogTournoiPrincipal.initOwner(PetanqueManagerApplication.MAIN_STAGE);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		dialogTournoiPrincipal.setMinWidth(primaryScreenBounds.getWidth());
		dialogTournoiPrincipal.setMinHeight(primaryScreenBounds.getHeight());
		dialogTournoiPrincipal.setAlwaysOnTop(false);
		dialogTournoiPrincipal.initModality(Modality.NONE);
		sceneTournoiPrincipal = new Scene(principalTableau);

		complementaireTableau = new Tableau();
		dialogTournoiComplementaire = new Stage();
		dialogTournoiComplementaire.setResizable(true);
		dialogTournoiComplementaire.initOwner(PetanqueManagerApplication.MAIN_STAGE);
		dialogTournoiComplementaire.initStyle(StageStyle.DECORATED);
		dialogTournoiComplementaire.initModality(Modality.NONE);
		sceneTournoiComplemetaire = new Scene(complementaireTableau);
	}

	@Override
	public void setDisplayPrincipalTournoi(Principal principal) {

		principalTableau.drawTableau(principal);
		dialogTournoiPrincipal.setScene(sceneTournoiPrincipal);
		dialogTournoiPrincipal.show();

	}

	@Override
	public void setDisplayComplementaireTournoi(Complementaire compl) {
		complementaireTableau.drawTableau(compl);
		dialogTournoiComplementaire.setScene(sceneTournoiComplemetaire);
		dialogTournoiComplementaire.show();

	}

	@Override
	public void setDisplayPreferencesTournoi(Tournoi currentTournoi) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpdateDisplayPrincipalTournoi(Principal principal) {
		principalTableau.drawTableau(principal);

	}

	@Override
	public void setUpdateDisplayPrincipalTournoi(Complementaire complementaire) {
		complementaireTableau.drawTableau(complementaire);

	}

}
