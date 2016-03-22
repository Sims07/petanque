package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javafx.collections.ObservableList;
import sims.chareyron.petanque.javafx.model.EquipeModel;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class JoueurPrincipalPresenter extends AbstractJoueursPresenter {

	@Autowired
	public JoueurPrincipalPresenter(@Qualifier("principal") JoueursView view) {
		super(view);
	}

	@Override
	void ajouterTypeEquipe(EquipeModel equipeModel) {
		equipeModel.setInscritPrincipal(true);

	}

	@Override
	void chargerLesEquipes(ObservableList<EquipeModel> equipes) {
		List<EquipeModel> equipeModels = tournoiFS.getEquipesPrincipal();
		equipes.clear();
		equipes.addAll(equipeModels);
		tirageAuSortEnable.set(!equipeModels.isEmpty());
	}

	@Override
	public void onTirageAuSortClicked() {
		Tournoi tournoi = tournoiFS.tirageAuSortPrincipal();
		tirageAuSortEnable.set(!tournoi.getPrincipal().isTirageAuSortFait());
	}

}
