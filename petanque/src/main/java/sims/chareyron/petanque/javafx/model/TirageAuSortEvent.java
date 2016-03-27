package sims.chareyron.petanque.javafx.model;

import org.springframework.context.ApplicationEvent;

import sims.chareyron.petanque.model.SousTournoi;

public class TirageAuSortEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean principal;
	private SousTournoi sousTournoi;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SousTournoi getSousTournoi() {
		return sousTournoi;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public TirageAuSortEvent(Object source, boolean principal, SousTournoi sousTournoi) {
		super(source);
		this.principal = principal;
		this.sousTournoi = sousTournoi;
	}

}
