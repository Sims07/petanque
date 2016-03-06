package sims.chareyron.petanque;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Joueur;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;
import sims.chareyron.petanque.model.Tournoi;
import sims.chareyron.petanque.service.PetanqueService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PetanqueManagerApplication.class)
public class PetanqueManagerApplicationTests {
	@Autowired
	private PetanqueService petanqueService;

	@Test
	public void contextLoads() {
		Tournoi t = new Tournoi();
		t.setNom("Test");
		t = petanqueService.createTournoi(t);
		Equipe e = null;
		for (int i = 0; i < 66; i++) {
			Joueur j = new Joueur();
			j.setNom("simon");
			Joueur j2 = new Joueur();
			j2.setNom("marine");
			e = new Equipe();
			e.getJoueurs().add(j);
			e.getJoueurs().add(j2);
			e.setInscritDansLePrincipal(true);
			petanqueService.addEquipeToTournoi(t.getId(), e);
		}
		petanqueService.removeEquipeToTournoi(e.getId());
		Tournoi tournoi = petanqueService.tirageAuSort(t.getId(), true);
		System.out.println(tournoi.getPrincipal().getTours().get(0)
				.getParties());
		petanqueService
				.calculerLesStatistiques(tournoi.getPrincipal(), tournoi);
		System.out.println(tournoi.getPrincipal().getStatistics());
		List<Tournoi> tournois = petanqueService.getAllTournoi();
		tournoi = getAndRefreshTournoi(tournoi.getId());
		System.out.println(tournoi.getPrincipal().getTours().get(0)
				.getParties());
		System.out.println(tournois);
	}

	private Tournoi getAndRefreshTournoi(Long aTournoiId) {
		Tournoi res = petanqueService.getTournoiById(aTournoiId);
		cleanTourDuplicates(res.getPrincipal());
		cleanTourDuplicates(res.getComplementaire());
		return res;
	}

	private void cleanTourDuplicates(SousTournoi sousTournoi) {
		LinkedHashSet<Tour> noDuplicates = new LinkedHashSet<Tour>(
				sousTournoi.getTours());
		sousTournoi.getTours().clear();
		sousTournoi.getTours().addAll(noDuplicates);
		Collections.sort(sousTournoi.getTours(), new Comparator<Tour>() {

			@Override
			public int compare(Tour arg0, Tour arg1) {
				// TODO Auto-generated method stub
				return arg0.getNbTour() - arg1.getNbTour();
			}

		});
		for (Tour tour : sousTournoi.getTours()) {
			cleanPartiesDuplicates(tour.getParties());
		}
	}

	private void cleanPartiesDuplicates(List<Partie> parties) {
		LinkedHashSet<Partie> noDuplicates = new LinkedHashSet<Partie>(parties);
		parties.clear();
		parties.addAll(noDuplicates);

	}

}
