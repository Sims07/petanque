package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;

public interface PartieUiHandlers extends UiHandlers {
	void onGagnantSelected(Partie aPartie, Equipe equipeGagnante);

	void onResetEquipeGagnante(Partie currentPartie);
}
