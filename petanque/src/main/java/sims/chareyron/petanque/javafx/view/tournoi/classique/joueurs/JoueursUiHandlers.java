package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public interface JoueursUiHandlers extends UiHandlers {
	void onAjouterEquipeClicked();

	void onDeleteEquipeClicked(EquipeModel equipe);

	void onTirageAuSortClicked();
}
