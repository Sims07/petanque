package sims.chareyron.petanque.javafx.model;

import org.springframework.context.ApplicationEvent;

import sims.chareyron.petanque.model.Tournoi;

public class RefreshTournoiEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tournoi refreshedTournoi;

	public RefreshTournoiEvent(Object source, Tournoi refreshedTournoi) {
		super(source);
		this.refreshedTournoi = refreshedTournoi;
	}

	public Tournoi getRefreshedTournoi() {
		return refreshedTournoi;
	}

}
