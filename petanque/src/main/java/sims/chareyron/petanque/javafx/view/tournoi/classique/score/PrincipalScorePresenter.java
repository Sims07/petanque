package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tournoi;

@Component
public class PrincipalScorePresenter extends AbstractScorePresenter {
	@Autowired
	public PrincipalScorePresenter(@Qualifier("principalScore") IScoreView view) {
		super(view);
	}

	@Override
	protected boolean isPrincipal() {
		return true;
	}

	@Override
	protected SousTournoi getSousTournoi(Tournoi tournoi) {
		return tournoi.getPrincipal();
	}

}
