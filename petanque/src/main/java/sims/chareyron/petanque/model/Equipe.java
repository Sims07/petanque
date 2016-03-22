package sims.chareyron.petanque.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javafx.beans.property.SimpleStringProperty;
import sims.chareyron.petanque.javafx.model.EquipeModel;

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
	@OrderColumn(name = "list_index")
	private List<Joueur> joueurs = new ArrayList<Joueur>();

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

	public void setInscritDansLeComplementaire(boolean inscritDansLeComplementaire) {
		this.inscritDansLeComplementaire = inscritDansLeComplementaire;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Joueur> joueurs) {
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

	public EquipeModel map() {
		EquipeModel equipeModel = new EquipeModel();
		equipeModel.setId(id);
		equipeModel.setInscritComplementaire(inscritDansLeComplementaire);
		equipeModel.setInscritPrincipal(inscritDansLePrincipal);
		Iterator<Joueur> iterator = joueurs.iterator();
		equipeModel.setJoueur1(new SimpleStringProperty(iterator.next().getNom()));
		equipeModel.setJoueur2(new SimpleStringProperty(iterator.next().getNom()));
		return equipeModel;
	}

}