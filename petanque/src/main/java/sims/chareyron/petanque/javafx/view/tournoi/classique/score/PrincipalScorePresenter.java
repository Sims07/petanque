package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PrincipalScorePresenter extends AbstractScorePresenter {
	@Autowired
	public PrincipalScorePresenter(@Qualifier("principalScore") IScoreView view) {
		super(view);
	}

}
