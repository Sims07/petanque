package sims.chareyron.petanque.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sims.chareyron.petanque.model.Equipe;
import sims.chareyron.petanque.model.EquipeStats;
import sims.chareyron.petanque.model.Partie;
import sims.chareyron.petanque.model.Partition;
import sims.chareyron.petanque.model.PreferenceAffichage;
import sims.chareyron.petanque.model.SousTournoi;
import sims.chareyron.petanque.model.StatisticModel;
import sims.chareyron.petanque.model.Tour;
import sims.chareyron.petanque.model.Tournoi;
import sims.chareyron.petanque.resource.ComplementaireResource;
import sims.chareyron.petanque.resource.EquipeRestResource;
import sims.chareyron.petanque.resource.PartieResource;
import sims.chareyron.petanque.resource.PreferenceAffichageResource;
import sims.chareyron.petanque.resource.PrincipalResource;
import sims.chareyron.petanque.resource.TourResource;
import sims.chareyron.petanque.resource.TournoiRestResource;

@Service
@Transactional()
public class PetanqueServiceImpl implements PetanqueService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PetanqueService.class);
	@Autowired
	private TournoiRestResource tournoiResource;
	@Autowired
	private PrincipalResource principalResource;
	@Autowired
	private PreferenceAffichageResource preferenceAffichageResource;
	@Autowired
	private ComplementaireResource complementaireResource;
	@Autowired
	private TourResource tourResource;
	@Autowired
	private EquipeRestResource equipeResource;
	@Autowired
	private PartieResource partieResource;

	@Override
	public Tournoi createTournoi(Tournoi aTournoi) {
		return tournoiResource.save(aTournoi);
	}

	@Override
	public List<Tournoi> getAllTournoi() {
		return (List<Tournoi>) tournoiResource.findAll();
	}

	@Override
	public String isPetanqueRestStarted() {
		return "OK!";
	}

	@Override
	public Set<Equipe> getAllTeamByTournoi(Long idTournoi) {
		Tournoi tournoi = tournoiResource.findOne(idTournoi);
		return tournoi.getEquipes();
	}

	@Override
	public Equipe addEquipeToTournoi(Long aIdTournoi, Equipe aEquipeToAdd) {
		Tournoi tournoi = tournoiResource.findOne(aIdTournoi);
		if (aEquipeToAdd.isInscritDansLeComplementaire()) {
			aEquipeToAdd.setNumero(tournoi.getNextNumeroEquipeC());
			tournoi.setNextNumeroEquipeC(tournoi.getNextNumeroEquipeC() + 1);
		} else {
			aEquipeToAdd.setNumero(tournoi.getNextNumeroEquipe());
			tournoi.setNextNumeroEquipe(tournoi.getNextNumeroEquipe() + 1);
		}
		equipeResource.save(aEquipeToAdd);
		tournoi.getEquipes().add(aEquipeToAdd);
		return aEquipeToAdd;
	}

	@Override
	public Tournoi tirageAuSort(Long tournoiId, boolean principal) {
		// recuperer les equipes
		Tournoi tournoi = tournoiResource.findOne(tournoiId);
		int nbEquipes = getNbEquipes(tournoi.getEquipes(), principal);// tournoi.getEquipes().size();
		int nbEquipesMaxTournoi = buildNbParties(nbEquipes);
		int nbTours = buildNbTours(nbEquipes);
		List<Partition> partitions = buildPartitions(nbEquipes);
		LOGGER.info(partitions.toString());
		SousTournoi sousTournoi = principal ? tournoi.getPrincipal() : tournoi.getComplementaire();
		sousTournoi.setTirageAuSortFait(true);
		// repartir les fausses equipes
		repartirFausseEquipes(partitions, sousTournoi, nbEquipesMaxTournoi);
		// repartir vraies equipes dans les trous
		repartirAleatoirementLesInscrits(tournoi.getEquipes(), sousTournoi, principal);
		// generer toutes les parties
		creerLesAutresTours(sousTournoi, nbTours);
		genererLesParties(sousTournoi);
		sousTournoi.setActiveNbTour(1);
		return tournoi;
	}

	private void cleanTourDuplicates(SousTournoi sousTournoi) {
		LinkedHashSet<Tour> noDuplicates = new LinkedHashSet<Tour>(sousTournoi.getTours());
		sousTournoi.getTours().clear();
		sousTournoi.getTours().addAll(noDuplicates);
		for (Tour tour : noDuplicates) {
			cleanPartiesDuplicates(tour.getParties());
		}
	}

	private Tournoi cleanTournoi(Tournoi tournoi) {
		// cleanTourDuplicates(tournoi.getPrincipal());
		// cleanTourDuplicates(tournoi.getComplementaire());
		return tournoi;
	}

	@Override
	public void jouerLesPartiesForfaits(final Long aIdSousTournoi, final boolean isPrincipal) {
		Tournoi tournoi = tournoiResource.findOne(aIdSousTournoi);
		tournoi = cleanTournoi(tournoi);
		SousTournoi sousTournoi = isPrincipal ? tournoi.getPrincipal() : tournoi.getComplementaire();
		int nbTours = sousTournoi.getTours().size();
		for (int tour = 1; tour <= nbTours; tour++) {
			List<String> tourPartiesIds = new ArrayList<String>();
			final int currentTour = tour;
			sousTournoi.getTours().stream().filter(t -> {
				return t.getNbTour() == currentTour;
			}).forEach(t -> {
				t.getParties().stream().filter(p -> {
					return p.getEquipe1Gagnante() == null && p.getEquipe1() != null && p.getEquipe2() != null
							&& p.getEquipe1().isFausseEquipe() && p.getEquipe2().isFausseEquipe();
				}).forEach(p -> {
					tourPartiesIds.add(p.getId() + ":" + t.getId());
				});
			});

			for (String partieIds : tourPartiesIds) {
				String partiIdSplit[] = partieIds.split(":");
				marquerLeScoreDeLaPartie(sousTournoi.getId(), Long.valueOf(partiIdSplit[0]), true, "",
						Long.valueOf(partiIdSplit[1]), isPrincipal);
			}
		}

	}

	private int getNbEquipes(Set<Equipe> equipes, boolean principal) {
		return (int) equipes.stream()
				.filter((e) -> principal ? e.isInscritDansLePrincipal() : e.isInscritDansLeComplementaire()).count();
	}

	private void creerLesAutresTours(SousTournoi sousTournoi, int nbTours) {
		for (int i = nbTours - 1; i > 0; i--) {
			Tour t = new Tour();
			t.setNbTour(nbTours - i + 1);
			int nbParties = getNbEquipeParTour(i) / 2;
			createEmptyParties(nbParties, t);
			tourResource.save(t);
			sousTournoi.getTours().add(t);
		}

	}

	private void createEmptyParties(int nbParties, Tour t) {
		for (int i = 0; i < nbParties; i++) {
			Partie p = new Partie();
			p.setEnAttente(true);
			t.getParties().add(p);
		}
	}

	private int getNbEquipeParTour(int nbTour) {
		return (int) Math.pow(2, nbTour);
	}

	private void genererLesParties(SousTournoi tournoi) {
		List<Tour> tours = tournoi.getTours();
		Collections.sort(tours, ((t1, t2) -> {
			return Integer.valueOf(t1.getNbTour()).compareTo(Integer.valueOf(t2.getNbTour()));
		}));
		// creer toutes les parties
		for (Tour tour : tours) {
			if (tour.getNbTour() == 1) {
				// construire les parties a pertir des equipes
				List<Equipe> equipesTourPremier = tour.getEquipes();
				int nbEquipe = equipesTourPremier.size();
				int equipe1Index = 0;
				int equipe2Index = 1;
				while (equipe2Index < nbEquipe) {
					Partie partie = new Partie();
					partie.setEquipe1(equipesTourPremier.get(equipe1Index));
					partie.setEquipe2(equipesTourPremier.get(equipe2Index));
					if (partie.getEquipe1() == null) {
						System.out.println("bug");
					}
					equipe1Index += 2;
					equipe2Index += 2;
					tour.getParties().add(partie);
				}
			}
		}
	}

	private void repartirAleatoirementLesInscrits(Set<Equipe> equipesTournoi, SousTournoi sousTournoi,
			final boolean principal) {

		List<Equipe> equipesARepartir = new ArrayList<Equipe>(equipesTournoi);
		// filtre les equipes
		equipesARepartir = equipesARepartir.stream()
				.filter((e) -> principal ? e.isInscritDansLePrincipal() : e.isInscritDansLeComplementaire())
				.collect(Collectors.toList());
		// parcourir la lsite des equipe a creer
		Tour premierTour = sousTournoi.getTours().get(0);
		List<Equipe> equipesPremierTour = premierTour.getEquipes();
		int equipeIndex = 0;
		Random random = new Random();
		for (Equipe equipe : equipesPremierTour) {
			if (equipe == null) {
				// ajouter une vraie equipe
				equipesPremierTour.set(equipeIndex, tirageAuSort(equipesARepartir, random));
			}
			equipeIndex++;
		}
		LOGGER.info("Repartition premier tour apres tirage au sort:" + premierTour.getEquipes());
	}

	private Equipe tirageAuSort(List<Equipe> equipesARepartir, Random random) {
		Equipe tireeAuSort = equipesARepartir.get(random.nextInt(equipesARepartir.size()));
		equipesARepartir.remove(tireeAuSort);
		return tireeAuSort;
	}

	private void repartirFausseEquipes(List<Partition> partitions, SousTournoi principal, int nbEquipes) {
		List<Tour> tours = principal.getTours();
		Tour premierTour = new Tour();
		premierTour.setNbTour(1);
		tours.add(0, premierTour);

		premierTour.setEquipes(new ArrayList<Equipe>(nbEquipes));
		List<Equipe> equipesACreer = premierTour.getEquipes();
		for (int j = 0; j < nbEquipes; j++) {
			equipesACreer.add(null);
		}
		int positionPartition = 0;
		for (Partition partition : partitions) {
			int nbPartitions = partition.getNbPartition();
			for (int i = 0; i < nbPartitions; i++) {
				positionPartition += partition.getNbEquipe();
				repartirFausseEquipes(equipesACreer, positionPartition, partition.getNbEquipe());
				// laisser autant de place pour les vraies equipes
				positionPartition += partition.getNbEquipe();
			}
		}
		tourResource.save(premierTour);
		LOGGER.info("Repartition premier tour:" + premierTour.getEquipes());
	}

	private void repartirFausseEquipes(List<Equipe> equipesACreer, int positionPartition, int nbEquipe) {
		List<Equipe> faussesEquipes = construireFausseEquipes(nbEquipe);
		for (Equipe equipe : faussesEquipes) {

			equipesACreer.set(positionPartition++, equipe);
		}
	}

	private List<Equipe> construireFausseEquipes(int nbEquipe) {
		List<Equipe> res = new ArrayList<Equipe>();
		Equipe e = null;
		for (int i = 0; i < nbEquipe; i++) {
			e = new Equipe();
			e.setFausseEquipe(true);
			e.setNumero(0);
			res.add(e);
		}
		return res;
	}

	@Override
	public List<Partition> buildPartitions(int nbEquipes) {
		List<Partition> partition = new ArrayList<Partition>();

		int nbEquipesMaxTournoi = buildNbParties(nbEquipes);
		int nbTours = buildNbTours(nbEquipes);
		int nbForfait = nbEquipesMaxTournoi - nbEquipes;
		LOGGER.info("Nb tours:" + nbTours);
		LOGGER.info("Format du tableau:" + nbEquipesMaxTournoi);
		LOGGER.info("Nb equipe forfait:" + nbForfait);
		// repartir les forfaits en partition
		int debutTour = nbTours - 1;
		int partitionSize = (int) Math.pow(2, debutTour - 1);
		while (nbForfait > 0) {
			partitionSize = (int) Math.pow(2, debutTour);
			// partir de la puissance seconde inferieur
			int nbPartition = Math.floorDiv(nbForfait, partitionSize);
			if (nbPartition > 0) {
				Partition p = new Partition();
				p.setNbEquipe(partitionSize);
				p.setNbPartition(nbPartition);
				partition.add(p);
				nbForfait = nbForfait - nbPartition * partitionSize;
			}
			debutTour--;
		}
		return partition;
	}

	private int buildNbParties(int nbEquipes) {
		return (int) Math.pow(2, buildNbTours(nbEquipes));
	}

	private int buildNbTours(int nbEquipes) {
		return (int) Math.ceil((Math.log(nbEquipes) / Math.log(2)));
	}

	@Override
	@Transactional(readOnly = true)
	public Tournoi getTournoiById(Long aId) {
		return tournoiResource.findOne(aId);
	}

	@Override
	public void removeEquipeToTournoi(Long aEquipeId) {
		equipeResource.delete(aEquipeId);
	}

	@Override
	public Partie marquerLeScoreDeLaPartie(Long aTournoiId, Long aPartieId, boolean isEquipe1Gagnante, String aScore,
			Long aTourId, boolean aIsPrincipal) {
		Partie partieAModifier = partieResource.findOne(aPartieId);
		partieAModifier.setEnAttente(false);
		partieAModifier.setScore(aScore);
		partieAModifier.setTermine(true);
		partieAModifier.setEquipe1Gagnante(isEquipe1Gagnante);

		// update next partie
		Tour tour = tourResource.findOne(aTourId);
		SousTournoi ssTournoi = null;
		if (aIsPrincipal) {
			ssTournoi = principalResource.findOne(aTournoiId);
		} else {
			ssTournoi = complementaireResource.findOne(aTournoiId);
		}
		List<Partie> partiesCurrentTour = tour.getParties();
		cleanPartiesDuplicates(partiesCurrentTour);
		int partieIndex = partiesCurrentTour.indexOf(partieAModifier) + 1;
		// divided by 2 for finding next partie index in the next tour
		double nextPartieDIndex = partieIndex / 2.0;
		int nextPartieIndex = (int) Math.ceil(nextPartieDIndex);
		int nextTourIndex = tour.getNbTour() + 1;
		// get next tour
		Optional<Tour> nextTourOpt = ssTournoi.getTours().stream().filter(t -> t.getNbTour() == nextTourIndex)
				.findFirst();
		if (nextTourOpt.isPresent()) {
			Tour nextTour = nextTourOpt.get();
			// select the next partie
			cleanPartiesDuplicates(nextTour.getParties());
			Partie nextPartie = nextTour.getParties().get(nextPartieIndex - 1);
			// set l'equipe gagnante
			if (nextPartieDIndex < nextPartieIndex) {
				// equipe1
				nextPartie.setEquipe1(getEquipeGagnante(partieAModifier));
			} else {
				nextPartie.setEquipe2(getEquipeGagnante(partieAModifier));
			}
		}

		return partieAModifier;
	}

	private void cleanPartiesDuplicates(List<Partie> parties) {
		LinkedHashSet<Partie> noDuplicates = new LinkedHashSet<Partie>(parties);
		parties.clear();
		parties.addAll(noDuplicates);

	}

	private Equipe getEquipeGagnante(Partie partieAModifier) {
		return partieAModifier.getEquipe1Gagnante() ? partieAModifier.getEquipe1() : partieAModifier.getEquipe2();
	}

	@Override
	public void calculerLesStatistiques(SousTournoi aSousTournoi, Tournoi aTournoi) {
		// cleanTourDuplicates(aSousTournoi);
		int[] nbPartiesTermine = aSousTournoi.getTours().stream().mapToInt(t -> {
			return (int) t.getParties().stream().filter(p -> {
				return p.isTermine();
			}).count();
		}).toArray();
		int tourIndex = 0;
		int nbTours = nbPartiesTermine.length;
		List<StatisticModel> stats = new ArrayList<StatisticModel>();
		while (tourIndex < nbTours) {
			StatisticModel st = new StatisticModel();
			st.setNbPartiesTermine(nbPartiesTermine[tourIndex]);
			st.setTour(tourIndex + 1);
			st.setNbTotalPartieDsLeTour(getNbEquipeParTour(nbTours - tourIndex) / 2);
			stats.add(st);
			tourIndex++;
		}
		aSousTournoi.setStatistics(stats);
		EquipeStats statsEquipe = new EquipeStats();
		aTournoi.setStats(statsEquipe);
		statsEquipe.setNbEquipesP((int) aTournoi.getEquipes().stream().filter(e -> {
			return !e.isFausseEquipe() && e.isInscritDansLePrincipal();
		}).count());
		statsEquipe.setNbEquipesC((int) aTournoi.getEquipes().stream().filter(e -> {
			return !e.isFausseEquipe() && e.isInscritDansLeComplementaire();
		}).count());
	}

	@Override
	@Transactional
	public PreferenceAffichage updatePref(PreferenceAffichage pref) {
		return preferenceAffichageResource.save(pref);
	}
}
