package sims.chareyron.petanque.javafx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquipeModel {
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

}
