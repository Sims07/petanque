package sims.chareyron.petanque.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PreferenceAffichage extends GenericPE {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private static final long serialVersionUID = 1L;
	private int ecartHeight = 5;
	private int ecartWidth = 20;
	private int partieHeight = 20;
	private String couleurTexteFinale = "GOLD";
	private String couleurEquipeGagnante = "GREEN";
	private String couleurEquipePerdante = "GRAY";
	private String couleurEquipePartieNonJouée = "BLUE";
	private String couleurPartieFinale = "GOLD";
	private String couleurPartie = "GREEN";
	private String couleurPartieeDemiFinale = "SILVER";
	private String couleurTexteTitre = "BLACK";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEcartHeight() {
		return ecartHeight;
	}

	public void setEcartHeight(int ecartHeight) {
		this.ecartHeight = ecartHeight;
	}

	public int getEcartWidth() {
		return ecartWidth;
	}

	public void setEcartWidth(int ecartWidth) {
		this.ecartWidth = ecartWidth;
	}

	public int getPartieHeight() {
		return partieHeight;
	}

	public void setPartieHeight(int partieHeight) {
		this.partieHeight = partieHeight;
	}

	public String getCouleurEquipeGagnante() {
		return couleurEquipeGagnante;
	}

	public void setCouleurEquipeGagnante(String couleurEquipeGagnante) {
		this.couleurEquipeGagnante = couleurEquipeGagnante;
	}

	public String getCouleurEquipePerdante() {
		return couleurEquipePerdante;
	}

	public void setCouleurEquipePerdante(String couleurEquipePerdante) {
		this.couleurEquipePerdante = couleurEquipePerdante;
	}

	public String getCouleurEquipePartieNonJouée() {
		return couleurEquipePartieNonJouée;
	}

	public void setCouleurEquipePartieNonJouée(String couleurEquipePartieNonJouée) {
		this.couleurEquipePartieNonJouée = couleurEquipePartieNonJouée;
	}

	public String getCouleurPartieFinale() {
		return couleurPartieFinale;
	}

	public void setCouleurPartieFinale(String couleurPartieFinale) {
		this.couleurPartieFinale = couleurPartieFinale;
	}

	public String getCouleurPartieeDemiFinale() {
		return couleurPartieeDemiFinale;
	}

	public void setCouleurPartieeDemiFinale(String couleurPartieeDemiFinale) {
		this.couleurPartieeDemiFinale = couleurPartieeDemiFinale;
	}

	public String getCouleurPartie() {
		return couleurPartie;
	}

	public void setCouleurPartie(String couleurPartieNonjouee) {
		this.couleurPartie = couleurPartieNonjouee;
	}

	public String getCouleurTexteFinale() {
		return couleurTexteFinale;
	}

	public void setCouleurTexteFinale(String couleurTexteFinale) {
		this.couleurTexteFinale = couleurTexteFinale;
	}

	public String getCouleurTexteTitre() {
		return couleurTexteTitre;
	}

	public void setCouleurTexteTitre(String couleurTexteTitre) {
		this.couleurTexteTitre = couleurTexteTitre;
	}

}
