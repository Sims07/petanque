package sims.chareyron.petanque.model;

public class StatisticModel {
	private int tour;
	private int nbPartiesTermine;
	private int nbTotalPartieDsLeTour;

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public int getNbPartiesTermine() {
		return nbPartiesTermine;
	}

	public void setNbPartiesTermine(int nbPartiesTermine) {
		this.nbPartiesTermine = nbPartiesTermine;
	}

	public int getNbTotalPartieDsLeTour() {
		return nbTotalPartieDsLeTour;
	}

	public void setNbTotalPartieDsLeTour(int nbTotalPartieDsLeTour) {
		this.nbTotalPartieDsLeTour = nbTotalPartieDsLeTour;
	}

	@Override
	public String toString() {
		return "StatisticModel [tour=" + tour + ", nbPartiesTermine="
				+ nbPartiesTermine + ", nbTotalPartieDsLeTour="
				+ nbTotalPartieDsLeTour + "]";
	}

}
