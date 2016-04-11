package sims.chareyron.petanque.javafx.view.tournoi.classique.tableau;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sims.chareyron.petanque.PetanqueManagerApplication;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.model.PreferenceAffichage;

@Component
public class PreferencesView extends AbstractViewWithUiHandlers<PreferenceUiHandlers>
		implements Initializable, IPreferencesView {
	@FXML
	Slider partieHeight;

	@FXML
	Pane mainPanel;
	@FXML
	ColorPicker couleurTexteFinale;
	@FXML
	ColorPicker couleurEquipeGagnante;
	@FXML
	ColorPicker couleurEquipePerdante;
	@FXML
	ColorPicker couleurEquipePartieNonJouée;
	@FXML
	ColorPicker couleurPartieFinale;
	@FXML
	ColorPicker couleurPartie;
	@FXML
	ColorPicker couleurPartieeDemiFinale;
	Scene scene;
	Stage dialog;

	@Override
	public void setInSlot(Slot slot, View view) {

	}

	PreferenceAffichage pref;

	@Override
	public void setPreferences(PreferenceAffichage pref) {
		this.pref = pref;
		couleurEquipeGagnante.setValue(convert(pref.getCouleurEquipeGagnante()));
		couleurEquipePerdante.setValue(convert(pref.getCouleurEquipePerdante()));
		couleurEquipePartieNonJouée.setValue(convert(pref.getCouleurEquipePartieNonJouée()));
		couleurPartie.setValue(convert(pref.getCouleurPartie()));
		couleurPartieeDemiFinale.setValue(convert(pref.getCouleurPartieeDemiFinale()));
		couleurPartieFinale.setValue(convert(pref.getCouleurPartieFinale()));
		couleurTexteFinale.setValue(convert(pref.getCouleurTexteTitre()));
		partieHeight.setValue(pref.getPartieHeight());
		partieHeight.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pref.setPartieHeight((Integer) newValue);

			}
		});
		;
	}

	public void onPrefChanged(Event evt) {
		Object src = evt.getSource();
		if (src.equals(couleurEquipeGagnante)) {
			pref.setCouleurEquipeGagnante(decode(couleurEquipeGagnante.getValue()));
		} else if (src.equals(couleurEquipePartieNonJouée)) {
			pref.setCouleurEquipePartieNonJouée(decode(couleurEquipePartieNonJouée.getValue()));
		} else if (src.equals(couleurEquipePerdante)) {
			pref.setCouleurEquipePerdante(decode(couleurEquipePerdante.getValue()));
		} else if (src.equals(couleurPartie)) {
			pref.setCouleurPartie(decode(couleurPartie.getValue()));
		} else if (src.equals(couleurPartieeDemiFinale)) {
			pref.setCouleurPartieeDemiFinale(decode(couleurPartieeDemiFinale.getValue()));
		} else if (src.equals(couleurPartieFinale)) {
			pref.setCouleurPartieFinale(decode(couleurPartieFinale.getValue()));
			pref.setCouleurTexteFinale(decode(couleurPartieFinale.getValue()));
		} else if (src.equals(couleurTexteFinale)) {
			pref.setCouleurTexteTitre(decode(couleurTexteFinale.getValue()));
		}
	}

	private String decode(Color value) {
		return value.toString();
	}

	private Color convert(String couleurEquipeGagnante2) {
		return Color.valueOf(couleurEquipeGagnante2);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scene = new Scene(mainPanel);
		dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		dialog.initModality(Modality.NONE);
		dialog.initOwner(PetanqueManagerApplication.MAIN_STAGE);

		dialog.setScene(scene);
	}

	public void onAppliquerClicked() {
		getUiHandlers().onPreferenceChanged(pref);
	}

	@Override
	public void setShowInDialog() {

		dialog.show();
	}

}
