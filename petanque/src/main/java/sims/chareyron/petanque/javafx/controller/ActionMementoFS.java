package sims.chareyron.petanque.javafx.controller;

import javafx.beans.property.BooleanProperty;
import sims.chareyron.petanque.javafx.model.Action;

public interface ActionMementoFS {

	BooleanProperty isPreviousEnabled();

	BooleanProperty isNextEnabled();

	void ajouterAction(Action action);

	void executerPreviousAction();

	void executerNextAction();
}
