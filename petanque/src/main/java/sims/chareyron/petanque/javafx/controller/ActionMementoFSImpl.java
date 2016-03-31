package sims.chareyron.petanque.javafx.controller;

import java.util.Arrays;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sims.chareyron.petanque.javafx.model.Action;
import sims.chareyron.petanque.javafx.model.NextEvent;
import sims.chareyron.petanque.javafx.model.PetanqueEvent;
import sims.chareyron.petanque.javafx.model.PreviousEvent;

@Service
public class ActionMementoFSImpl implements ActionMementoFS {
	@Autowired
	private ApplicationEventPublisher publisher;

	private BooleanProperty previousEnabled = new SimpleBooleanProperty(false);
	private BooleanProperty nextEnabled = new SimpleBooleanProperty(false);
	private Stack<Action> actionPrecedentes = new Stack<>();
	private Stack<Action> actionSuivantes = new Stack<>();

	private void resfreshState() {
		previousEnabled.set(!actionPrecedentes.isEmpty());
		nextEnabled.set(!actionSuivantes.isEmpty());
	}

	@Override
	public void ajouterAction(Action action) {
		actionPrecedentes.add(action);
		resfreshState();
	}

	@Override
	public void executerPreviousAction() {
		try {
			Action actionPrecedente = actionPrecedentes.pop();
			actionPrecedente.rollback();

			// enlever toutes les actions avec le même id
			String idRollBack = actionPrecedente.idRollback();
			Object[] actionsToRemove = actionPrecedentes.stream().filter(a -> {
				return a.idRollback().equals(idRollBack);
			}).toArray();
			actionPrecedentes.removeAll(Arrays.asList(actionsToRemove));

			actionSuivantes.add(actionPrecedente);
			resfreshState();
			publisher.publishEvent(new PetanqueEvent<PreviousEvent>(this));
		} catch (Exception e) {
			//
		} finally {
			resfreshState();
		}
	}

	@Override
	public void executerNextAction() {
		try {
			Action actionSuivante = actionSuivantes.pop();
			actionSuivante.execute();

			// enlever toutes les actions avec le même id
			String idExecute = actionSuivante.idExecute();
			Object[] actionsToRemove = actionPrecedentes.stream().filter(a -> {
				return a.idRollback().equals(idExecute);
			}).toArray();
			actionSuivantes.removeAll(Arrays.asList(actionsToRemove));

			actionPrecedentes.add(actionSuivante);
			resfreshState();
			publisher.publishEvent(new PetanqueEvent<NextEvent>(this));
		} catch (Exception e) {
			//
		} finally {
			resfreshState();
		}

	}

	@Override
	public BooleanProperty isPreviousEnabled() {
		return previousEnabled;
	}

	@Override
	public BooleanProperty isNextEnabled() {
		return nextEnabled;
	}

	@Override
	public void clear() {
		previousEnabled.set(false);
		nextEnabled.set(false);
		actionPrecedentes.clear();
		actionSuivantes.clear();

	}

}
