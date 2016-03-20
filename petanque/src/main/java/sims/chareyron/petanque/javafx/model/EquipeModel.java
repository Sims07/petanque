package sims.chareyron.petanque.javafx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Joueur;

public class EquipeModel {
	private Long id;
	private boolean inscritPrincipal;
	private boolean inscritComplementaire;
	private StringProperty joueur1;
	private StringProperty joueur2;

	public EquipeModel() {
		super();
		joueur1 = new SimpleStringProperty("");
		joueur2 = new SimpleStringProperty("");
	}

	public StringProperty getJoueur1() {
		return joueur1;
	}

	public void setJoueur1(StringProperty joueur1) {
		this.joueur1 = joueur1;
	}

	public StringProperty getJoueur2() {
		return joueur2;
	}

	public void setJoueur2(StringProperty joueur2) {
		this.joueur2 = joueur2;
	}

	public boolean isInscritPrincipal() {
		return inscritPrincipal;
	}

	public void setInscritPrincipal(boolean inscritPrincipal) {
		this.inscritPrincipal = inscritPrincipal;
	}

	public boolean isInscritComplementaire() {
		return inscritComplementaire;
	}

	public void setInscritComplementaire(boolean inscritComplementaire) {
		this.inscritComplementaire = inscritComplementaire;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Equipe map() {
		Equipe equipe = new Equipe();
		equipe.setId(id);
		equipe.setInscritDansLeComplementaire(inscritComplementaire);
		equipe.setInscritDansLePrincipal(inscritPrincipal);
		// creer joueur 1
		Joueur joueurPe1 = new Joueur();
		joueurPe1.setNom(joueur1.get());
		Joueur joueurPe2 = new Joueur();
		joueurPe2.setNom(joueur2.get());
		equipe.getJoueurs().add(joueurPe1);
		equipe.getJoueurs().add(joueurPe2);
		return equipe;
	}
}
