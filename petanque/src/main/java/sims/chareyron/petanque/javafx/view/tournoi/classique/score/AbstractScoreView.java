package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;

public abstract class AbstractScoreView extends AbstractViewWithUiHandlers<ScoreUiHandlers> implements IScoreView {
	@FXML
	protected Label scoreTitleLabel;

	@FXML
	protected javafx.scene.control.ScrollPane partiesPanel;

	@Override
	public void setSousTournoi(SousTournoi ssTournoi) {
		VBox parties = new VBox();
		Tour currentTour = ssTournoi.getTours().get(ssTournoi.getActiveNbTour() - 1);
		AtomicInteger index = new AtomicInteger(0);
		currentTour.getParties().forEach(p -> {
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("Partie.fxml"));
				Node res = loader.load();
				PartieView partieView = loader.getController();
				partieView.setPartie(p, String.valueOf(index.getAndIncrement()));
				parties.getChildren().add(res);
			} catch (Exception e) {
				System.err.println(e);
			}
		});
		partiesPanel.setContent(parties);
	}

}
