package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;

public abstract class AbstractScoreView extends AbstractViewWithUiHandlers<ScoreUiHandlers>
		implements IScoreView, Initializable {

	@FXML
	protected Pagination tours;

	@FXML
	protected TitledPane equipesForm;

	@FXML
	protected TitledPane partiesForm;

	@FXML
	protected Label scoreTitleLabel;

	@FXML
	protected javafx.scene.control.ScrollPane partiesPanel;

	@Override
	public void setSousTournoi(SousTournoi ssTournoi) {

		VBox parties = new VBox();
		boolean equipeVisible = true;
		boolean partieVisible = true;
		if (ssTournoi.getActiveNbTour() <= 0) {
			scoreTitleLabel.setText("Tirage au sort non effectué");
			equipeVisible = false;
			partieVisible = false;
		} else {
			int pageIndex = ssTournoi.getActiveNbTour() - 1;
			tours.setMaxPageIndicatorCount(ssTournoi.getTours().size());
			tours.setCurrentPageIndex(pageIndex);
			displayParties(ssTournoi, parties, pageIndex);
		}
		equipesForm.setVisible(equipeVisible);
		partiesForm.setVisible(partieVisible);
		partiesPanel.setContent(parties);
	}

	@Override
	public void setTour(int currentPageIndex, SousTournoi currentSousTournoi) {
		VBox parties = new VBox();
		displayParties(currentSousTournoi, parties, currentPageIndex);
		partiesPanel.setContent(parties);
	}

	public void onTourClicked(int pageIndex) {
		getUiHandlers().onTourClicked(pageIndex);
	}

	public void initialize(URL location, ResourceBundle resources) {
		tours.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				int currentPageIndex = newValue.intValue();
				onTourClicked(currentPageIndex);
			}
		});
	}

	private void displayParties(SousTournoi ssTournoi, VBox parties, int pageIndex) {
		Tour currentTour = ssTournoi.getTours().get(pageIndex);
		AtomicInteger index = new AtomicInteger(0);
		currentTour.getParties().forEach(p -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Partie.fxml"));
				Node res = loader.load();
				PartieView partieView = loader.getController();
				partieView.setPartie(p, String.valueOf(index.getAndIncrement()));
				parties.getChildren().add(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
