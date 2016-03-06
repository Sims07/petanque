package sims.chareyron.petanque.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 */
@Entity
public class Principal extends AbstractTournoi {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy("nbTour ASC")
	private List<Tour> tours = new ArrayList<Tour>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int activeNbTour;

	private boolean tirageAuSortFait;

	@Override
	public int getActiveNbTour() {
		return activeNbTour;
	}

	@Override
	public void setActiveNbTour(int activeNbTour) {
		this.activeNbTour = activeNbTour;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	public boolean isTirageAuSortFait() {
		return tirageAuSortFait;
	}

	@Override
	public void setTirageAuSortFait(boolean tirageAuSortFait) {
		this.tirageAuSortFait = tirageAuSortFait;
	}

}