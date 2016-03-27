package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Joueur;
import sims.chareyron.petanque.model.Partie;

@Component
@Scope("prototype")
public class PartieView extends AbstractFxmlView {
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

	private final String styleAttente = "-fx-border-color:blue;";
	private final String styleEquipeGagnante = "-fx-border-radius:5;-fx-border-color:green;";
	private final String styleEquipePerdante = ".text { -fx-strikethrough: true; }";
	private final String styleDone = "-fx-border-color:green;";

	public void setPartie(Partie apartie, String index) {
		partie.setText(String.format("Partie %s", index));
		List<Joueur> joueursEq1 = apartie.getEquipe1().getJoueurs();
		List<Joueur> joueursEq2 = apartie.getEquipe2().getJoueurs();
		equipe1.setText(String.format("%s-%s", apartie.getEquipe1().getNumero(),
				joueursEq1.isEmpty() ? "-" : joueursEq1.get(0).getNom()));
		equipe2.setText(String.format("%s-%s", apartie.getEquipe2().getNumero(),
				joueursEq2.isEmpty() ? "-" : joueursEq2.get(0).getNom()));
		String style = "-fx-border-radius:5;";
		if (apartie.isEnAttente()) {
			style += styleAttente;
		} else {
			style += styleDone;
			if (apartie.getEquipe1Gagnante() != null && apartie.getEquipe1Gagnante()) {
				equipe1.setStyle(styleEquipeGagnante);

				equipe2.setStyle(styleEquipePerdante);
			} else {
				equipe2.setStyle(styleEquipeGagnante);
				equipe1.setStyle(styleEquipePerdante);
			}
		}
		partie.setStyle(style);

	}

	@Override
	public void setInSlot(Slot slot, View view) {
		// TODO Auto-generated method stub

	}

}
