package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.beans.binding.BooleanBinding;
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
	private Label equipe1;
	@FXML
	private Label equipe2;
	@FXML
	private Label numero1;
	@FXML
	private Label numero2;

	@FXML
	private GridPane partiePanel;
	@FXML
	private ChoiceBox<Equipe> gagnants;
	private ChangeListener<Equipe> listener;
	private final String partieTerminee = "-fx-background-color: #32C5E3; -fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5;";
	private final String partieAttente = "-fx-background-color: white; -fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5;";
	private final String styleEquipeGagnante = "-fx-text-fill: white";
	private final String styleEquipePerdante = "-fx-text-fill: grey";
	private final String styleEquipeAttente = "-fx-text-fill: #32C5E3";
	private final String styleNumero = "-fx-border-radius: 5; -fx-border-color: black;";
	private Equipe equipeModel1;
	private Equipe equipeModel2;
	private Partie currentPartie;
	private String index;

	public Partie getCurrentPartie() {
		return currentPartie;
	}

	public void setCurrentPartie(Partie currentPartie) {
		this.currentPartie = currentPartie;
	}

	public void setPartie(Partie apartie, String index) {
		this.index = index;
		gagnants.getSelectionModel().selectedItemProperty().removeListener(listener);
		String numero1Style = styleNumero;
		String numero2Style = styleNumero;
		currentPartie = apartie;
		equipeModel1 = apartie.getEquipe1() != null ? apartie.getEquipe1() : emptyEquipe();
		equipeModel2 = apartie.getEquipe2() != null ? apartie.getEquipe2() : emptyEquipe();
		List<Joueur> joueursEq1 = equipeModel1.getJoueurs();
		List<Joueur> joueursEq2 = equipeModel2.getJoueurs();
		numero1.setText(String.valueOf(equipeModel1.getNumero()));
		numero2.setText(String.valueOf(equipeModel2.getNumero()));
		equipe1.setText(displayEquipe(joueursEq1));
		equipe2.setText(displayEquipe(joueursEq2));

		String style = "-fx-border-radius:5;";
		gagnants.getItems().add(equipeModel1);
		gagnants.getItems().add(equipeModel2);
		if (!apartie.isTermine()) {
			style = partieAttente;
		} else {
			style = partieTerminee;
			if (apartie.getEquipe1Gagnante() != null) {
				if (apartie.getEquipe1Gagnante()) {
					numero1Style += styleEquipeGagnante;
					numero2Style += styleEquipePerdante;
					equipe1.setStyle(styleEquipeGagnante);
					equipe2.setStyle(styleEquipePerdante);
					gagnants.getSelectionModel().select(equipeModel1);
				} else {
					numero2Style += styleEquipeGagnante;
					numero1Style += styleEquipePerdante;
					equipe2.setStyle(styleEquipeGagnante);
					equipe1.setStyle(styleEquipePerdante);
					gagnants.getSelectionModel().select(equipeModel2);
				}
			} else {
				equipe1.setStyle(styleEquipeAttente);
				equipe2.setStyle(styleEquipeAttente);
			}
		}
		numero1.setStyle(numero1Style);
		numero2.setStyle(numero2Style);
		partiePanel.setStyle(style);
		gagnants.getSelectionModel().selectedItemProperty().addListener(listener);

	}

	private String displayEquipe(List<Joueur> joueursEq1) {

		return String.format("%s et %s", (joueursEq1.isEmpty() ? "-" : joueursEq1.get(0).getNom()),
				(joueursEq1.isEmpty() ? "-" : joueursEq1.get(1).getNom()));
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

	public Partie getPartie() {
		return currentPartie;
	}

	public void updatePartie(Partie partie) {
		setPartie(partie, index);
	}

	public void setVisible(BooleanBinding createBooleanBinding) {
		partiePanel.visibleProperty().bind(createBooleanBinding);

	}

}
