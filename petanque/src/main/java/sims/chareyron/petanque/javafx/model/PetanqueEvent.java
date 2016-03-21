package sims.chareyron.petanque.javafx.model;

import org.springframework.context.ApplicationEvent;

public class PetanqueEvent<T> extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetanqueEvent(Object source) {
		super(source);
	}

}
