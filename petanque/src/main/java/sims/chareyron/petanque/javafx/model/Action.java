package sims.chareyron.petanque.javafx.model;

public interface Action {

	String idExecute();

	String idRollback();

	void execute();

	void rollback();
}
