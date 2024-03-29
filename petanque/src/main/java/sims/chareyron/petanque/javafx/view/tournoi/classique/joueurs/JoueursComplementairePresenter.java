package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javafx.collections.ObservableList;
import sims.chareyron.petanque.javafx.model.EquipeModel;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class JoueursComplementairePresenter extends AbstractJoueursPresenter {
	@Autowired
	public JoueursComplementairePresenter(@Qualifier("complementaire") JoueursView view) {
		super(view);
	}

	@Override
	void ajouterTypeEquipe(EquipeModel equipeModel) {
		equipeModel.setInscritComplementaire(true);

	}

	@Override
	void chargerLesEquipes(ObservableList<EquipeModel> equipes) {
		List<EquipeModel> equipeModels = tournoiFS.getEquipesComplementaire();
		equipes.clear();
		equipes.addAll(equipeModels);
		tirageAuSortEnable
				.set(!tournoiFS.getLoadedTournoi().getComplementaire().isTirageAuSortFait() && !equipeModels.isEmpty());

	}

	@Override
	public void onTirageAuSortClicked() {
		Tournoi tournoi = tournoiFS.tirageAuSortComplementaire();
		tirageAuSortEnable.set(!tournoi.getPrincipal().isTirageAuSortFait());
	}
}
