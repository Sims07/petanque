package sims.chareyron.petanque.javafx.view.tournoi.classique.tableau;

import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.framework.mvp.ViewWithUiHandlers;
import sims.chareyron.petanque.model.PreferenceAffichage;

public interface IPreferencesView extends View, ViewWithUiHandlers<PreferenceUiHandlers> {
	void setPreferences(PreferenceAffichage pref);

	void setShowInDialog();
}
