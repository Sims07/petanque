package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public interface IJoueursView extends View, ViewWithUiHandlers<JoueursUiHandlers> {
	void setEquipe(EquipeModel equipe);

	void setViewBindings();

	void setTirageAuSortEnable(BooleanProperty enable);

	void setEquipes(ObservableList<EquipeModel> equipes);
}
