package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Joueur;
import sims.chareyron.petanque.model.Partie;

@Component
@Scope("prototype")
public class PartieView extends AbstractViewWithUiHandlers<PartieUiHandlers> implements Initializable {
	@FXML
	private Label partie;
	@FXML
	private Label equipe1;
	@FXML
	private Label equipe2;

	@FXML
	private GridPane partiePanel;
	@FXML
	private ChoiceBox<Equipe> gagnants;
	private ChangeListener<Equipe> listener;
	private final String partieTerminee = "-fx-background-color: #32C5E3; -fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5;";
	private final String partieAttente = "-fx-background-color: #88E868; -fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5;";
	private final String styleEquipeGagnante = "-fx-text-fill: white";
	private final String styleEquipePerdante = "-fx-text-fill: grey";
	private Equipe equipeModel1;
	private Equipe equipeModel2;
	private Partie currentPartie;

	public void setPartie(Partie apartie, String index) {
		gagnants.getSelectionModel().selectedItemProperty().removeListener(listener);

		currentPartie = apartie;
		partie.setText(String.format("Partie %s", index));
		equipeModel1 = apartie.getEquipe1() != null ? apartie.getEquipe1() : emptyEquipe();
		equipeModel2 = apartie.getEquipe2() != null ? apartie.getEquipe2() : emptyEquipe();
		List<Joueur> joueursEq1 = equipeModel1.getJoueurs();
		List<Joueur> joueursEq2 = equipeModel2.getJoueurs();
		equipe1.setText(String.format("%s-%s", equipeModel1.getNumero(),
				joueursEq1.isEmpty() ? "-" : joueursEq1.get(0).getNom()));
		equipe2.setText(String.format("%s-%s", equipeModel2.getNumero(),
				joueursEq2.isEmpty() ? "-" : joueursEq2.get(0).getNom()));
		String style = "-fx-border-radius:5;";
		gagnants.getItems().add(equipeModel1);
		gagnants.getItems().add(equipeModel2);
		if (!apartie.isTermine()) {
			style = partieAttente;
		} else {
			style = partieTerminee;
			if (apartie.getEquipe1Gagnante() != null) {
				if (apartie.getEquipe1Gagnante()) {

					equipe1.setStyle(styleEquipeGagnante);
					equipe2.setStyle(styleEquipePerdante);
					gagnants.getSelectionModel().select(equipeModel1);
				} else {
					equipe2.setStyle(styleEquipeGagnante);
					equipe1.setStyle(styleEquipePerdante);
					gagnants.getSelectionModel().select(equipeModel2);
				}
			}
		}
		partiePanel.setStyle(style);
		gagnants.getSelectionModel().selectedItemProperty().addListener(listener);

	}

	private Equipe emptyEquipe() {
		Equipe equipe = new Equipe();
		equipe.setNumero(-1);
		equipe.getJoueurs().add(emptyJoueur());
		equipe.getJoueurs().add(emptyJoueur());
		return equipe;
	}

	private Joueur emptyJoueur() {
		Joueur j = new Joueur();
		j.setNom("-");
		return j;
	}

	@Override
	public void setInSlot(Slot slot, View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listener = new ChangeListener<Equipe>() {

			@Override
			public void changed(ObservableValue<? extends Equipe> observable, Equipe oldValue, Equipe newValue) {
				getUiHandlers().onGagnantSelected(currentPartie, gagnants.getSelectionModel().getSelectedItem());

			}
		};
		gagnants.getSelectionModel().selectedItemProperty().addListener(listener);
		gagnants.setConverter(new StringConverter<Equipe>() {

			@Override
			public String toString(Equipe object) {

				return String.valueOf(object.getNumero());
			}

			@Override
			public Equipe fromString(String numero) {

				if (equipeModel1.getNumero() == Integer.valueOf(numero)) {
					return equipeModel1;
				} else {
					return equipeModel2;
				}
			}
		});

	}

}
