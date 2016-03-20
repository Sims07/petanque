package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JoueursComplementairePresenter extends AbstractJoueursPresenter {
	@Autowired
	public JoueursComplementairePresenter(@Qualifier("complementaire") JoueursView view) {
		super(view);
	}

}
