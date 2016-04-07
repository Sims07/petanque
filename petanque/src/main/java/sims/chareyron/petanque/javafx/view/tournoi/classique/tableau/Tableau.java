package sims.chareyron.petanque.javafx.view.tournoi.classique.tableau;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;

public class Tableau extends Pane {
	private static final int EQUIPE_WIDTH = 100;
	private static final int PY = 5;
	private static final int PX = 20;
	private static final int EQUIPE_HEIGHT = 20;
	// map tour -> partieIndex -> rectangle
	private Map<Integer, Map<Integer, Rectangle>> partiesMap = new java.util.HashMap();

	public void drawTableau(SousTournoi sousTournoi) {
		List<Tour> tours = sousTournoi.getTours();
		// dessiner la moitié de chaque tour
		int nbColumns = tours.size() * 2;
		int columnIndex = 0;
		for (Tour tour : tours) {
			partiesMap.put(columnIndex, new HashMap<>());
			drawTour(nbColumns, columnIndex, tour, true);
			columnIndex++;
		}
		/*
		 * int nbT = tours.size(); for (int i = nbT - 1; i >= 0; i--) {
		 * drawTour(nbColumns, columnIndex, tours.get(i), false); columnIndex++;
		 * }
		 */

	}

	private void drawTour(int nbColumn, int columnIndex, Tour tour, boolean premiereMoitie) {
		double x = columnIndex == 0 ? PX : columnIndex * EQUIPE_WIDTH + (columnIndex + 1) * PX;

		List<Partie> parties = tour.getParties();
		int fromIndex = premiereMoitie ? 0 : parties.size() / 2;
		int toIndex = premiereMoitie ? parties.size() / 2 : parties.size();
		List<Partie> partiesToDisplay = parties.subList(fromIndex, toIndex);
		int partieIndex = 0;
		int relativeColumn = nbColumn - columnIndex;
		// double yOffset = premiereMoitie ? (columnIndex * EQUIPE_HEIGHT) :
		// (relativeColumn * EQUIPE_HEIGHT);
		for (Partie partie : partiesToDisplay) {
			double y = 0;
			if (columnIndex != 0) {
				Point originePartie = findOriginePartieRectangle(columnIndex, partieIndex);
				x = originePartie.getX();
				y = originePartie.getY();
			} else {
				y = (partieIndex) * PY + partieIndex * EQUIPE_HEIGHT;
			}
			Rectangle drawPartie = drawPartie(partie, x, y);
			partiesMap.get(columnIndex).put(partieIndex, drawPartie);
			getChildren().add(drawPartie);
			partieIndex++;
		}
	}

	class Point {
		double x, y;

		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

	}

	private Point findOriginePartieRectangle(int columnIndex, int partieIndex) {
		// trouver les 2 rectangles precdent
		Map<Integer, Rectangle> partiesPrecedentes = partiesMap.get(columnIndex - 1);
		int firstRect = partieIndex * 2;
		int lastRect = firstRect + 1;
		Rectangle firstRectShape = partiesPrecedentes.get(firstRect);
		Rectangle lastRectShape = partiesPrecedentes.get(lastRect);
		// trouver le milieu
		double yMilieu = (firstRectShape.getY() + lastRectShape.getY() + EQUIPE_HEIGHT) / 2;
		double y = yMilieu - 0.5 * EQUIPE_HEIGHT;
		double x = firstRectShape.getX() + EQUIPE_WIDTH + PX;
		return new Point(x, y);
	}

	private Rectangle drawPartie(Partie partie, double x, double y) {
		Rectangle partieRect = new Rectangle(x, y, EQUIPE_WIDTH, EQUIPE_HEIGHT);
		partieRect.setFill(Color.CADETBLUE);
		partieRect.setArcHeight(5);
		partieRect.setArcWidth(5);
		return partieRect;
	}
}
