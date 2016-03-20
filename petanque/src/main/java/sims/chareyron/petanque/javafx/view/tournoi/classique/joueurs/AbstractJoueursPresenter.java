package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.model.EquipeModel;

public abstract class AbstractJoueursPresenter extends AbstractWidgetPresenter<IJoueursView>
		implements JoueursPresenter, JoueursUiHandlers {
	protected EquipeModel equipeModel;

	public AbstractJoueursPresenter(JoueursView view) {
		super();
		this.view = view;
	}

	@Override
	public void onBind() {
		equipeModel = new EquipeModel();
		getView().setEquipe(equipeModel);
		getView().setUiHandlers(this);
	}

	@Override
	public void onAjouterEquipeClicked() {
		System.out.println("Ajouter equipe:" + equipeModel.getJoueur1().get());

	}

	@Override
	public void onReveal() {
		// TODO Auto-generated method stub

	}
}
