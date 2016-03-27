package sims.chareyron.petanque.javafx.view.tournoi.classique.score;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreConfig {
	@Bean(name = "principalScore")
	public IScoreView scorePrincipalView() {
		return new ScorePrincipalView();
	}
}