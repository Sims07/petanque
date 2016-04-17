package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sims.chareyron.petanque.javafx.framework.mvp.AbstractViewWithUiHandlers;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.Principal;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;

public abstract class AbstractScoreView extends AbstractViewWithUiHandlers<ScoreUiHandlers>
		implements IScoreView, Initializable {

	private Map<Long, PartieView> cacheParties = new HashMap<>();
	private Map<Long, Node> cacheNodeParties = new HashMap<>();
	@FXML
	protected TextField numeroEquipeFilter;
	@FXML
	protected Pagination tours;

	@FXML
	protected TitledPane equipesForm;

	@FXML
	protected TitledPane partiesForm;

	@FXML
	protected Label scoreTitleLabel;
	@FXML
	private CheckBox filtrePartieEnded;
	@FXML
	protected javafx.scene.control.ScrollPane partiesPanel;
	private List<PartieView> partiesDisplay;

	private SimpleStringProperty filter;

	private SimpleBooleanProperty filterDisplayPartieEnded;
	private List<Partie> parties;

	@Override
	public void setSousTournoi(SousTournoi ssTournoi, int tourIndex) {

		VBox parties = new VBox();

		boolean equipeVisible = true;
		boolean partieVisible = true;
		if (!ssTournoi.isTirageAuSortFait()) {
			scoreTitleLabel.setText("Tirage au sort non effectué");
			equipeVisible = false;
			partieVisible = false;
		} else {
			scoreTitleLabel.setText(ssTournoi instanceof Principal ? "Principal" : "Complémentaire");
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

	private List<PartieView> displayParties(SousTournoi ssTournoi, VBox partiesB, int pageIndex) {
		cacheParties.clear();
		cacheNodeParties.clear();

		List<PartieView> resl = new ArrayList<>();
		Tour currentTour = ssTournoi.getTours().get(pageIndex);
		AtomicInteger index = new AtomicInteger(0);
		parties = currentTour.getParties();
		parties.stream().filter(p -> {
			return filterDisplayPartieEnded.get() ? true : !p.isTermine();
		}).forEach(p -> {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("Partie.fxml"));
				Node res = loader.load();
				PartieView partieView = loader.getController();
				partieView.setUiHandlers(getUiHandlers());
				cacheParties.put(p.getId(), partieView);
				cacheNodeParties.put(p.getId(), res);
				partieView.setPartie(p, String.valueOf(index.getAndIncrement()));
				partiesB.getChildren().add(res);
				resl.add(partieView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return resl;
	}

	protected Boolean updateContentFilterPartie(List<Partie> parties2) {

		VBox parties = new VBox();
		partiesPanel.setContent(new Label("Recherche..."));
		displayPartiesFiltered(parties, parties2);
		partiesPanel.setContent(parties);

		return true;
	}

	private void displayPartiesFiltered(Pane partiesB, List<Partie> parties2) {

		parties2.forEach(p -> {

			try {

				Node res = cacheNodeParties.get(p.getId());
				partiesB.getChildren().add(res);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
	}

	protected Boolean filterPartie(Partie p) {
		String filtre = filter.get();
		boolean ok = false;
		if (filtre.isEmpty()) {
			ok = true;
		} else {

			if (p.getEquipe2() != null) {
				ok = ok || p.getEquipe2() != null && String.valueOf(p.getEquipe2().getNumero()).contains(filtre);
			}
			if (p.getEquipe1() != null) {
				ok = ok || p.getEquipe1() != null && String.valueOf(p.getEquipe1().getNumero()).contains(filtre);
			}
		}
		boolean afficherPartiesTerminees = filterDisplayPartieEnded.get();
		if (ok && !afficherPartiesTerminees) {
			ok = !p.isTermine();
		}
		return ok;

	}

	@Override
	public void setScore(Partie partie) {
		PartieView partieViewToUpdate = cacheParties.get(partie.getId());
		parties.parallelStream().filter(p -> {
			return p.getId().equals(partie.getId());
		}).findFirst().get().setTermine(partie.isTermine());
		partieViewToUpdate.updatePartie(partie);

	}

	@Override
	public void setFilter(SimpleStringProperty filter, SimpleBooleanProperty filterDisplayPartieEnded) {
		this.filter = filter;
		this.filterDisplayPartieEnded = filterDisplayPartieEnded;
		numeroEquipeFilter.textProperty().bindBidirectional(filter);
		filtrePartieEnded.selectedProperty().bindBidirectional(filterDisplayPartieEnded);

	}

	public void rechercher() {
		updateContentFilterPartie(parties.parallelStream().filter(p -> {
			return filterPartie(p);
		}).collect(Collectors.toList()));
	}

}
