package sims.chareyron.petanque.javafx.model;

public abstract class AbstractAction implements Action {

	@Override
	public String toString() {
		return idExecute() + "-" + idRollback();
	}

}
