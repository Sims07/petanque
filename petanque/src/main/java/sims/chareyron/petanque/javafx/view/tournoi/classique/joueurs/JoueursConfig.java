package sims.chareyron.petanque.javafx.view.tournoi.classique.joueurs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JoueursConfig {
	@Bean(name = "principal")
	public JoueursView principal() {
		return new JoueursPrincipalView();
	}

	@Bean(name = "complementaire")
	public JoueursView complementaire() {
		return new JoueursComplementaireView();
	}
}
