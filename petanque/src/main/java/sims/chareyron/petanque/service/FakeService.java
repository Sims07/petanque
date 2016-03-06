package sims.chareyron.petanque.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Joueur;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;
import sims.chareyron.petanque.model.Tournoi;
import sims.chareyron.petanque.resource.EquipeRestResource;
import sims.chareyron.petanque.resource.TournoiRestResource;

@Service
public class FakeService {

	@Autowired
	PetanqueService petanqueService;

	@Autowired
	private TournoiRestResource tournoiRestResource;
	@Autowired
	private EquipeRestResource equipeRestResource;

	@PostConstruct
	public void init() {
		Tournoi t = new Tournoi();
		t.setNom("Test");
		t = petanqueService.createTournoi(t);
		for (int i = 0; i < 127; i++) {
			Joueur j = new Joueur();
			j.setNom("simon");
			Joueur j2 = new Joueur();
			j2.setNom("marine");
			Equipe e = new Equipe();
			e.getJoueurs().add(j);
			e.getJoueurs().add(j2);
			e.setInscritDansLePrincipal(true);
			petanqueService.addEquipeToTournoi(t.getId(), e);
		}
		for (int i = 0; i < 50; i++) {
			Joueur j = new Joueur();
			j.setNom("simon");
			Joueur j2 = new Joueur();
			j2.setNom("marine");
			Equipe e = new Equipe();
			e.getJoueurs().add(j);
			e.getJoueurs().add(j2);
			e.setInscritDansLeComplementaire(true);
			petanqueService.addEquipeToTournoi(t.getId(), e);
		}

		Tournoi tournoi = petanqueService.tirageAuSort(t.getId(), true);
		// tournoi = petanqueService.tirageAuSort(t.getId(), false);
		petanqueService.jouerLesPartiesForfaits(t.getPrincipal().getId(), true);

		System.out.println(tournoi.getPrincipal().getTours().get(0)
				.getParties());
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
