package sims.chareyron.petanque.javafx.model;

import org.springframework.context.ApplicationEvent;

import sims.chareyron.petanque.model.Tournoi;

public class TournoiLoadedEvent extends ApplicationEvent {
	private final Tournoi tournoi;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TournoiLoadedEvent(Object source, Tournoi tournoi) {
		super(source);
		this.tournoi = tournoi;
	}

	public Tournoi getTournoi() {
		return tournoi;
	}

}
