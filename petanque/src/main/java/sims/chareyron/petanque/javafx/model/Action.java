package sims.chareyron.petanque.javafx.model;

public interface Action {

	String idExecute();

	String idRollback();

	void execute() throws Exception;

	void rollback() throws Exception;
}
