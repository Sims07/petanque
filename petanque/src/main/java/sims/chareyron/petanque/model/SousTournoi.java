package sims.chareyron.petanque.model;

import java.util.List;

/**
 * 
 */
public interface SousTournoi {
	Long getId();

	List<Tour> getTours();

	PreferenceAffichage getPreferenceAffichage();

	int getActiveNbTour();

	void setActiveNbTour(int activeTour);

	void setTirageAuSortFait(boolean tirageAuSortFait);

	boolean isTirageAuSortFait();

	List<StatisticModel> getStatistics();

	void setStatistics(List<StatisticModel> astList);
}