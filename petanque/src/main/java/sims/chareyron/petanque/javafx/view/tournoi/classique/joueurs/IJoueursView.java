package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public interface IJoueursView extends View, ViewWithUiHandlers<JoueursUiHandlers> {
	void setEquipe(EquipeModel equipe);
}
