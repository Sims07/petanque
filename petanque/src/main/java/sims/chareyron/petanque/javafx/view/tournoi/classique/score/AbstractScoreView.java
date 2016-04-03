package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import sims.chareyron.petanque.model.Partie;
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
	private List<PartieView> partiesDisplay;

	@Override
	public void setSousTournoi(SousTournoi ssTournoi, int tourIndex) {

		VBox parties = new VBox();

		boolean equipeVisible = true;
		boolean partieVisible = true;
		if (tourIndex < 0) {
			scoreTitleLabel.setText("Tirage au sort non effectué");
			equipeVisible = false;
			partieVisible = false;
		} else {

			tours.setMaxPageIndicatorCount(ssTournoi.getTours().size());
			tours.setCurrentPageIndex(tourIndex);
			long b = System.currentTimeMillis();
			partiesDisplay = displayParties(ssTournoi, parties, tourIndex);
			long e = System.currentTimeMillis();
			System.out.println("display:" + (e - b));
		}
		partiesPanel.setContent(parties);
		equipesForm.setVisible(equipeVisible);
		partiesForm.setVisible(partieVisible);

	}

	@Override
	public void setTour(int currentPageIndex, SousTournoi currentSousTournoi) {
		VBox parties = new VBox();
		partiesDisplay = displayParties(currentSousTournoi, parties, currentPageIndex);
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

	private List<PartieView> displayParties(SousTournoi ssTournoi, VBox parties, int pageIndex) {
		List<PartieView> resl = new ArrayList<>();
		Tour currentTour = ssTournoi.getTours().get(pageIndex);
		AtomicInteger index = new AtomicInteger(0);
		currentTour.getParties().forEach(p -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Partie.fxml"));
				Node res = loader.load();
				PartieView partieView = loader.getController();
				partieView.setUiHandlers(getUiHandlers());
				partieView.setPartie(p, String.valueOf(index.getAndIncrement()));
				parties.getChildren().add(res);
				resl.add(partieView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return resl;
	}

	@Override
	public void setScore(Partie partie) {
		PartieView partieViewToUpdate = partiesDisplay.parallelStream().filter(p -> {
			return p.getPartie().equals(partie);
		}).findFirst().get();
		partieViewToUpdate.updatePartie(partie);

	}
}
