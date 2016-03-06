package sims.chareyron.petanque.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import sims.chareyron.petanque.filter.View;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 */
@Entity
public class Tournoi extends GenericPE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(View.Light.class)
	private Long id;
	@JsonView(View.Light.class)
	private String nom;
	@OneToOne(cascade = CascadeType.ALL)
	private Principal principal;
	@OneToOne(cascade = CascadeType.ALL)
	private Complementaire complementaire;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("numero DESC")
	@JoinColumn(name = "tournoi_fk")
	@BatchSize(size = 32)
	private Set<Equipe> equipes = new HashSet<Equipe>();
	private int nextNumeroEquipe;
	private int nextNumeroEquipeC;
	@Transient
	private EquipeStats stats;

	public Tournoi() {
		super();
		nextNumeroEquipe = 1;
		nextNumeroEquipeC = 1;
		principal = new Principal();
		complementaire = new Complementaire();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String name) {
		this.nom = name;
	}

	public Principal getPrincipal() {
		return principal;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public Complementaire getComplementaire() {
		return complementaire;
	}

	public void setComplementaire(Complementaire repechage) {
		this.complementaire = repechage;
	}

	public Set<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(Set<Equipe> equipes) {
		this.equipes = equipes;
	}

	public int getNextNumeroEquipe() {
		return nextNumeroEquipe;
	}

	public void setNextNumeroEquipe(int nextNumeroEquipe) {
		this.nextNumeroEquipe = nextNumeroEquipe;
	}

	public int getNextNumeroEquipeC() {
		return nextNumeroEquipeC;
	}

	public void setNextNumeroEquipeC(int nextNumeroEquipeC) {
		this.nextNumeroEquipeC = nextNumeroEquipeC;
	}

	public EquipeStats getStats() {
		return stats;
	}

	public void setStats(EquipeStats stats) {
		this.stats = stats;
	}

}