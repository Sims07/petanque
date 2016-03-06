package sims.chareyron.petanque.model;

import java.util.List;

import javax.persistence.Transient;

public abstract class AbstractTournoi extends GenericPE implements SousTournoi {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private List<StatisticModel> statistics;

	@Override
	public List<StatisticModel> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<StatisticModel> statistics) {
		this.statistics = statistics;
	}
}
