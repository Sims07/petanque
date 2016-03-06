package sims.chareyron.petanque.model;

import java.io.Serializable;

public class Partition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbEquipe;
	private int nbPartition;

	public int getNbPartition() {
		return nbPartition;
	}

	public void setNbPartition(int nbPartition) {
		this.nbPartition = nbPartition;
	}

	public int getNbEquipe() {
		return nbEquipe;
	}

	public void setNbEquipe(int nbEquipe) {
		this.nbEquipe = nbEquipe;
	}

	@Override
	public String toString() {
		return "Partition [nbEquipe=" + nbEquipe + ", nbPartition="
				+ nbPartition + "]";
	}

}
