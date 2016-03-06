package sims.chareyron.petanque.restservice;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sims.chareyron.petanque.filter.View;
import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.Tour;
import sims.chareyron.petanque.model.Tournoi;
import sims.chareyron.petanque.service.PetanqueService;
import sims.chareyron.petanque.service.TournoiWsController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/petanque")
public class PetanqueRestServiceImpl implements PetanqueRestService {

	@Autowired
	private PetanqueService petanqueService;

	@Autowired
	private TournoiWsController tournoiWsController;

	@Override
	@RequestMapping(path = "/tournois", method = { RequestMethod.POST })
	public Tournoi createTournoi(@RequestBody Tournoi aTournoi) {
		return petanqueService.createTournoi(aTournoi);
	}

	@Override
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String isPetanqueRestStarted() {
		return "OK";
	}

	@Override
	@RequestMapping(path = "/tournois/{id}/equipes", method = { RequestMethod.GET })
	public Set<Equipe> getAllTeamByTournoi(@PathVariable("id") Long idTournoi) {
		return petanqueService.getAllTeamByTournoi(idTournoi);
	}

	@Override
	@RequestMapping(path = "/tournois/{id}/equipes", method = { RequestMethod.POST })
	public Tournoi addEquipeToTournoi(@PathVariable("id") Long aIdTournoi,
			@RequestBody Equipe aEquipeToAdd) {
		petanqueService.addEquipeToTournoi(aIdTournoi, aEquipeToAdd);
		return getAndRefreshTournoi(aIdTournoi);
	}

	@Override
	@RequestMapping(path = "/tournois/{id}/{isPrincipal}/tirageAuSort", method = { RequestMethod.POST })
	public Tournoi tirageAuSort(@PathVariable("id") Long aId,
			@PathVariable("isPrincipal") Boolean isPrincipal) {
		petanqueService.tirageAuSort(aId, isPrincipal);
		petanqueService.jouerLesPartiesForfaits(aId, isPrincipal);
		return getAndRefreshTournoi(aId);
	}

	@Override
	@RequestMapping(path = "/tournois/{id}/{isPrincipal}", method = { RequestMethod.GET })
	public Tournoi getTournoiById(@PathVariable("id") Long aId,
			@PathVariable("isPrincipal") Boolean isPrincipal) {
		Tournoi res = petanqueService.getTournoiById(aId);
		if (isPrincipal) {
			res.setComplementaire(null);
			// clear duplicates
			// tours
			// cleanTourDuplicates(res.getPrincipal());
		} else {
			res.setPrincipal(null);
			// cleanTourDuplicates(res.getComplementaire());
		}
		return res;
	}

	@Override
	@RequestMapping(path = "/tournois/{tid}/equipes/{equipeId}", method = { RequestMethod.DELETE })
	public Tournoi removeEquipeToTournoi(@PathVariable("tid") Long aIdTournoi,
			@PathVariable("equipeId") Long aEquipeId) {
		petanqueService.removeEquipeToTournoi(aEquipeId);
		return getAndRefreshTournoi(aIdTournoi);
	}

	@Override
	@RequestMapping(path = "/tournois/{tid}/soustournoi/{principal}/{sid}/tour/{tourid}/parties/{partieId}", method = { RequestMethod.POST })
	public Tournoi marquerLeScoreDeLaPartie(
			@PathVariable("tid") Long aTournoiId,
			@PathVariable("sid") Long aSousTournoiId,
			@PathVariable("tourid") Long aTourId,
			@PathVariable("partieId") Long aPartieId,
			@RequestBody Partie partieAModifier,
			@PathVariable("principal") boolean aIsPrincipal) {
		petanqueService.marquerLeScoreDeLaPartie(aTournoiId, aPartieId,
				partieAModifier.getEquipe1Gagnante(),
				partieAModifier.getScore(), aTourId, aIsPrincipal);
		Tournoi t = getAndRefreshTournoi(aTournoiId);
		if (aIsPrincipal) {
			tournoiWsController.updateTournoi(t.getPrincipal());
		} else {
			tournoiWsController.updateTournoi(t.getComplementaire());
		}

		return t;
	}

	@Override
	@RequestMapping(path = "/tournois/{id}", method = { RequestMethod.GET })
	public Tournoi getTournoiById(@PathVariable("id") Long aId) {
		return getAndRefreshTournoi(aId);
	}

	private Tournoi getAndRefreshTournoi(Long aTournoiId) {
		Tournoi res = petanqueService.getTournoiById(aTournoiId);
		// cleanTourDuplicates(res.getPrincipal());
		// cleanTourDuplicates(res.getComplementaire());
		petanqueService.calculerLesStatistiques(res.getComplementaire(), res);
		petanqueService.calculerLesStatistiques(res.getPrincipal(), res);
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

	@Override
	@RequestMapping(path = "/tournois", method = { RequestMethod.GET })
	@JsonView(View.Light.class)
	public List<Tournoi> getAll() {
		return petanqueService.getAllTournoi();
	}

}
