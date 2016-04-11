package sims.chareyron.petanque.javafx.view.tournoi.classique.tableau;

import sims.chareyron.petanque.javafx.framework.mvp.UiHandlers;
import sims.chareyron.petanque.model.PreferenceAffichage;

public interface PreferenceUiHandlers extends UiHandlers {
	void onPreferenceChanged(PreferenceAffichage pref);
}
