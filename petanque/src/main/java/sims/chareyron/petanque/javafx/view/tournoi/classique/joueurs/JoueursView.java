package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public abstract class JoueursView extends AbstractViewWithUiHandlers<JoueursUiHandlers> implements IJoueursView {
	@FXML
	Button creerEquipe;

	@FXML
	TextField joueur1;

	@FXML
	TextField joueur2;

	@Override
	public void setInSlot(Slot slot, View view) {

	}

	@Override
	public void setEquipe(EquipeModel equipe) {
		joueur1.textProperty().bindBidirectional(equipe.getJoueur1());
		joueur2.textProperty().bindBidirectional(equipe.getJoueur2());
		creerEquipe.disableProperty()
				.bind(Bindings.and(joueur1.textProperty().isEmpty(), joueur2.textProperty().isEmpty()));
	}

	public void onAjouterEquipeClicked() {
		getUiHandlers().onAjouterEquipeClicked();
	}

}
