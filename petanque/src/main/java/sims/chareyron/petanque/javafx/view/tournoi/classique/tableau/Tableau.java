package sims.chareyron.petanque.javafx.view.tournoi.classique.tableau;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;

public class Tableau extends Pane {
	private static double EQUIPE_WIDTH = 100;
	private static final int PY = 5;
	private static final int PX = 20;
	private static final int EQUIPE_HEIGHT = 20;
	private static final Paint EQUIPEGAGNANTE = Color.GREEN;
	private static final Paint EQUIPEPERDANTE = Color.GRAY;
	private static final Paint PARTIENONJOUE = Color.BLUE;
	// map tour -> partieIndex -> rectangle
	private Map<Integer, Map<Integer, Rectangle>> partiesMap = new java.util.HashMap();

	public void drawTableau(SousTournoi sousTournoi) {

		List<Tour> tours = sousTournoi.getTours();
		// dessiner la moitié de chaque tour
		int nbColumns = (tours.size() - 1) * 2;
		EQUIPE_WIDTH = buildPartieWidth(nbColumns);
		int columnIndex = 0;
		int nbT = tours.size();
		for (int i = 0; i < nbT - 1; i++) {
			Tour tour = tours.get(i);
			System.out.println(columnIndex + ":" + tour.getParties().size());
			partiesMap.put(columnIndex, new HashMap<>());
			drawTour(nbColumns, columnIndex, tour, true);
			drawEdge(nbColumns, columnIndex, tour, true);
			columnIndex++;
		}
		int colIndexEdge = columnIndex;
		// draw parties
		for (int i = nbT - 2; i >= 0; i--) {
			Tour tour = tours.get(i);
			System.out.println(columnIndex + "::" + tour.getParties().size());
			partiesMap.put(columnIndex, new HashMap<>());
			drawTour(nbColumns, columnIndex, tours.get(i), false);
			columnIndex++;
		}

		// draw edges
		for (int i = nbT - 2; i >= 0; i--) {
			Tour tour = tours.get(i);
			drawEdge(nbColumns, colIndexEdge, tour, false);
			colIndexEdge++;
		}

	}

	private void drawEdge(int nbColumn, int columnIndex, Tour tour, boolean premiereMoitie) {

		List<Partie> parties = tour.getParties();
		int fromIndex = premiereMoitie ? 0 : parties.size() / 2;
		int toIndex = premiereMoitie ? parties.size() / 2 : parties.size();
		List<Partie> partiesToDisplay = parties.subList(fromIndex, toIndex);
		int partieIndex = 0;
		for (final Partie partie : partiesToDisplay) {
			double x1 = 0;
			double x2 = 0;
			double x3 = 0;
			double y1 = 0;
			double y2 = 0;
			double y3 = 0;
			if (columnIndex != 0) {
				Path originePartie = new Path();
				if (premiereMoitie) {
					Map<Integer, Rectangle> partiesPrecedentes = partiesMap.get(columnIndex - 1);
					int firstRect = partieIndex * 2;
					int lastRect = firstRect + 1;
					Rectangle firstRectShape = partiesPrecedentes.get(firstRect);
					Rectangle lastRectShape = partiesPrecedentes.get(lastRect);
					x1 = firstRectShape.getX();
					y1 = firstRectShape.getY();
					x2 = lastRectShape.getX();
					y2 = lastRectShape.getY();

					Rectangle targetRectShape = partiesMap.get(columnIndex).get(partieIndex);
					x3 = targetRectShape.getX();
					y3 = targetRectShape.getY();
					// se placer sur le rectangle src au milieu
					originePartie.getElements().add(new MoveTo(x1 + EQUIPE_WIDTH, y1 + EQUIPE_HEIGHT / 2));
					// tracer le premier trait horizontal
					originePartie.getElements().add(new LineTo(x1 + EQUIPE_WIDTH + PX / 2, y1 + EQUIPE_HEIGHT / 2));
					// on descend au milieu du rectangle target
					originePartie.getElements().add(new LineTo(x1 + EQUIPE_WIDTH + PX / 2, y3 + EQUIPE_HEIGHT / 2));
					// atteindre le rectangle
					originePartie.getElements().add(new LineTo(x3, y3 + EQUIPE_HEIGHT / 2));

					// se placer sur le 2 eme rectangle src au milieu
					originePartie.getElements().add(new MoveTo(x2 + EQUIPE_WIDTH, y2 + EQUIPE_HEIGHT / 2));
					// tracer le premier trait horizontal
					originePartie.getElements().add(new LineTo(x2 + EQUIPE_WIDTH + PX / 2, y2 + EQUIPE_HEIGHT / 2));
					// on descend au milieu du rectangle target
					originePartie.getElements().add(new LineTo(x2 + EQUIPE_WIDTH + PX / 2, y3 + EQUIPE_HEIGHT / 2));
					// atteindre le rectangle
					originePartie.getElements().add(new LineTo(x3, y3 + EQUIPE_HEIGHT / 2));

				} else if (columnIndex < nbColumn - 1) {
					Map<Integer, Rectangle> partiesSuivantes = partiesMap.get(columnIndex + 1);
					int firstRect = partieIndex * 2;
					int lastRect = firstRect + 1;
					Rectangle firstRectShape = partiesSuivantes.get(firstRect);
					Rectangle lastRectShape = partiesSuivantes.get(lastRect);
					x1 = firstRectShape.getX();
					y1 = firstRectShape.getY();
					x2 = lastRectShape.getX();
					y2 = lastRectShape.getY();

					Rectangle targetRectShape = partiesMap.get(columnIndex).get(partieIndex);
					x3 = targetRectShape.getX();
					y3 = targetRectShape.getY();
					// se placer sur le rectangle src au milieu
					originePartie.getElements().add(new MoveTo(x3 + EQUIPE_WIDTH, y3 + EQUIPE_HEIGHT / 2));
					// tracer le premier trait horizontal
					originePartie.getElements().add(new LineTo(x3 + EQUIPE_WIDTH + PX / 2, y3 + EQUIPE_HEIGHT / 2));
					// on monte au milieu du rectangle target
					originePartie.getElements().add(new LineTo(x3 + EQUIPE_WIDTH + PX / 2, y1 + EQUIPE_HEIGHT / 2));
					// atteindre le rectangle
					originePartie.getElements().add(new LineTo(x1, y1 + EQUIPE_HEIGHT / 2));

					// se placer sur l'origine
					originePartie.getElements().add(new MoveTo(x3 + EQUIPE_WIDTH + PX / 2, y3 + EQUIPE_HEIGHT / 2));
					// tracer le premier trait horizontal
					originePartie.getElements().add(new LineTo(x3 + EQUIPE_WIDTH + PX / 2, y2 + EQUIPE_HEIGHT / 2));
					// atteindre le rectangle
					originePartie.getElements().add(new LineTo(x2, y2 + EQUIPE_HEIGHT / 2));
				}
				getChildren().add(originePartie);
			}

			partieIndex++;
		}
	}

	private void drawTour(int nbColumn, int columnIndex, Tour tour, boolean premiereMoitie) {
		double x = columnIndex == 0 ? PX : columnIndex * EQUIPE_WIDTH + (columnIndex + 1) * PX;

		List<Partie> parties = tour.getParties();

		int fromIndex;
		int toIndex;
		if (tour.getParties().size() == 1) {
			if (premiereMoitie) {
				fromIndex = 0;
				toIndex = 1;
			} else {
				return;
			}
		} else {
			fromIndex = premiereMoitie ? 0 : parties.size() / 2;
			toIndex = premiereMoitie ? parties.size() / 2 : parties.size();
		}
		List<Partie> partiesToDisplay = parties.subList(fromIndex, toIndex);
		int partieIndex = 0;
		int relativeColumn = Math.abs(nbColumn / 2 - columnIndex);
		// double yOffset = premiereMoitie ? (columnIndex * EQUIPE_HEIGHT) :
		// (relativeColumn * EQUIPE_HEIGHT);
		for (Partie partie : partiesToDisplay) {
			double y = 0;
			if (columnIndex != 0) {
				Point originePartie = null;
				if (premiereMoitie) {
					originePartie = findOriginePartieRectangle(columnIndex, partieIndex);
				} else {
					originePartie = findOriginePartieFinRectangle(relativeColumn, columnIndex, partieIndex);
				}
				x = originePartie.getX();
				y = originePartie.getY();
			} else {
				y = (partieIndex) * PY + partieIndex * EQUIPE_HEIGHT;
			}
			Rectangle drawPartie = drawPartie(partie, x, y);
			List<Shape> drawEquipes = drawEquipes(partie, drawPartie);
			partiesMap.get(columnIndex).put(partieIndex, drawPartie);
			getChildren().add(drawPartie);
			getChildren().addAll(drawEquipes);
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

	// 0 -2 1 -4 2 -6
	private Point findOriginePartieFinRectangle(int relativeColumn, int columnIndex, int partieIndex) {

		double y = 0, x = 0;
		// trouver les 2 rectangles precdent
		int previousIndex = columnIndex - 1 - 2 * (relativeColumn);
		Map<Integer, Rectangle> partiesPrecedentesSym = partiesMap.get(previousIndex);
		int synetriqueRectIndex = partieIndex;
		Rectangle symetriqueRect = partiesPrecedentesSym.get(synetriqueRectIndex);
		if (relativeColumn == 0) {
			x = symetriqueRect.getX() + EQUIPE_WIDTH + PX;
			y = symetriqueRect.getY();
		} else {

			Map<Integer, Rectangle> partiesPrecedentes = partiesMap.get(columnIndex - 1);
			Rectangle previousRectShape = partiesPrecedentes.get(0);
			// trouver le milieu

			y = symetriqueRect.getY();

			x = previousRectShape.getX() + EQUIPE_WIDTH + PX;
		}
		return new Point(x, y);
	}

	private double buildPartieWidth(int nbColumn) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		return (primaryScreenBounds.getWidth() / nbColumn) - 1.25 * PX;
	}

	private Rectangle drawPartie(Partie partie, double x, double y) {
		Rectangle partieRect = new Rectangle(x, y, EQUIPE_WIDTH, EQUIPE_HEIGHT);
		partieRect.setFill(Color.WHITESMOKE);
		partieRect.setArcHeight(5);
		partieRect.setArcWidth(5);
		partieRect.setStroke(Color.YELLOWGREEN);
		return partieRect;
	}

	/**
	 * x et y rectangle partie
	 */
	private List<Shape> drawEquipes(Partie partie, Rectangle container) {
		Equipe equipe1Model = partie.getEquipe1();
		Equipe equipe2Model = partie.getEquipe2();
		Circle equipe1 = new Circle();
		equipe1.setCenterX(container.getX() + 0.25 * EQUIPE_WIDTH);
		equipe1.setCenterY(container.getY() + 0.5 * EQUIPE_HEIGHT);
		equipe1.setRadius(0.5 * (EQUIPE_HEIGHT - 5));
		equipe1.setFill(getColorEquipe(partie, true));

		Text equipe1Label = new Text(container.getX() + 0.12 * EQUIPE_WIDTH, container.getY() + 0.7 * EQUIPE_HEIGHT,
				equipe1Model == null ? "" : "" + equipe1Model.getNumero());
		equipe1Label.setFill(getColorEquipe(partie, true));
		equipe1Label.setTextAlignment(TextAlignment.CENTER);

		Circle equipe2 = new Circle();
		equipe2.setCenterX(container.getX() + 0.75 * EQUIPE_WIDTH);
		equipe2.setCenterY(container.getY() + 0.5 * EQUIPE_HEIGHT);
		equipe2.setRadius(0.5 * (EQUIPE_HEIGHT - 5));
		equipe2.setFill(getColorEquipe(partie, false));
		Text equipe2Label = new Text(container.getX() + 0.60 * EQUIPE_WIDTH, container.getY() + 0.7 * EQUIPE_HEIGHT,
				equipe2Model == null ? "" : "" + equipe2Model.getNumero());
		equipe2Label.setFill(getColorEquipe(partie, false));
		return Arrays.asList(equipe2Label, equipe1Label);
	}

	private Paint getColorEquipe(Partie p, boolean isEquipe1) {

		if (p.isTermine()) {
			// determine le gagnant
			if (isEquipe1) {
				return p.getEquipe1Gagnante() ? EQUIPEGAGNANTE : EQUIPEPERDANTE;
			} else {
				return p.getEquipe1Gagnante() ? EQUIPEPERDANTE : EQUIPEGAGNANTE;
			}
		} else {
			return PARTIENONJOUE;
		}
	}
}
