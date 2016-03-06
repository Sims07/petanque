package sims.chareyron.petanque.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 */
@Entity
public class Equipe extends GenericPE {

	@Override
	public String toString() {
		return "Equipe [" + numero + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@BatchSize(size = 2)
	@Fetch(FetchMode.JOIN)
	private Set<Joueur> joueurs = new HashSet<Joueur>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int numero;
	private boolean inscritDansLePrincipal;
	private boolean inscritDansLeComplementaire;
	private boolean fausseEquipe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isInscritDansLePrincipal() {
		return inscritDansLePrincipal;
	}

	public void setInscritDansLePrincipal(boolean inscritDansLePrncipal) {
		this.inscritDansLePrincipal = inscritDansLePrncipal;
	}

	public boolean isInscritDansLeComplementaire() {
		return inscritDansLeComplementaire;
	}

	public void setInscritDansLeComplementaire(
			boolean inscritDansLeComplementaire) {
		this.inscritDansLeComplementaire = inscritDansLeComplementaire;
	}

	public Set<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(Set<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isFausseEquipe() {
		return fausseEquipe;
	}

	public void setFausseEquipe(boolean fausseEquipe) {
		this.fausseEquipe = fausseEquipe;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}