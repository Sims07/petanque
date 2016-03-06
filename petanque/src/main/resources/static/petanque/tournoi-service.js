(function() {
	'use strict';

	angular
		.module('Petanque')
		.factory('TournoiService', ['$http', TournoiService]);

	/* @ngInject */
	function TournoiService($http) {
		var service = {
			saveTournoi: saveTournoi,
			ajouterEquipe: ajouterEquipe,
			getTournoiById: getTournoiById,
			tirageAuSort: tirageAuSort,
			supprimerUneEquipe: supprimerUneEquipe,
			refreshTournoi: refreshTournoi,
			getAllTournois: getAllTournois,
			marquerLeScorePartie: marquerLeScorePartie
		};
		return service;

		////////////////

		function tirageAuSort(aTournoiId, isPrincipal, successCallBack) {
			$http.post('http://localhost:8080/petanque/tournois/' + aTournoiId + "/" + isPrincipal + '/tirageAuSort').
			success(function(data, status, headers, config) {
				successCallBack(filteredTours(data), status, headers, config);
			});
		}

		function getTournoiById(aTournoiId, _isPrincipal, successCallBack) {
			$http.get('http://localhost:8080/petanque/tournois/' + aTournoiId + '/' + _isPrincipal).
			success(function(data, status, headers, config) {
				successCallBack(data, status, headers, config);
			});
		}

		function filteredTours(aTournoi) {
			var prTs = aTournoi.principal.tours;
			var lightPrTts = [];
			angular.forEach(prTs, function(tour) {
				var newTour = {};
				newTour.nbTour = tour.nbTour;
				newTour.id = tour.id;
				lightPrTts.push(newTour);
				newTour.parties = [];
				var currentParties = tour.parties;
				angular.forEach(currentParties, function(partie) {
					if ((partie.equipe1 != null && !partie.equipe1.fausseEquipe) || (partie.equipe2 != null && !partie.equipe2.fausseEquipe)) {
						newTour.parties.push(partie);
					}
				});
			});

			var cTs = aTournoi.complementaire.tours;
			var lightCTts = [];
			angular.forEach(cTs, function(tour) {
				var newTourC = {};
				newTourC.nbTour = tour.nbTour;
				newTourC.id = tour.id;
				lightCTts.push(newTourC);
				newTourC.parties = [];
				var currentParties = tour.parties;
				angular.forEach(currentParties, function(partie) {
					if ((partie.equipe1 != null && !partie.equipe1.fausseEquipe) || (partie.equipe2 != null && !partie.equipe2.fausseEquipe)) {
						newTourC.parties.push(partie);
					}
				});
			});
			aTournoi.complementaire.tours = lightCTts;
			aTournoi.principal.tours = lightPrTts;
			return aTournoi;
		}



		function saveTournoi(aTournoi, successCallBack, erroCallBack) {
			$http.post('http://localhost:8080/petanque/tournois', aTournoi).
			success(function(data, status, headers, config) {
				successCallBack(data, status, headers, config);
			}).
			error(function(data, status, headers, config) {
				erroCallBack(data, status, headers, config);
			});
		}

		function ajouterEquipe(aEquipe, aTournoiId, successCallBack) {
			$http.post('http://localhost:8080/petanque/tournois/' + aTournoiId + '/equipes', aEquipe).
			success(function(data, status, headers, config) {
				successCallBack(data, status, headers, config);
			});
		}

		function supprimerUneEquipe(aTournoiId, aEquipeId, successCallBack) {
			$http.delete('http://localhost:8080/petanque/tournois/' + aTournoiId + '/equipes/' + aEquipeId).
			success(function(data, status, headers, config) {
				successCallBack(data, status, headers, config);
			});
		}

		function marquerLeScorePartie(aPartie, aTournoiId, aSousTournoiId, aTourId, aprincipal, successCallBack) {
			aPartie.termine = true;
			$http.post('http://localhost:8080/petanque/tournois/' + aTournoiId + '/soustournoi/' + aprincipal + '/' +
				aSousTournoiId + '/tour/' + aTourId + '/parties/' + aPartie.id, aPartie).
			success(function(data, status, headers, config) {
				successCallBack(filteredTours(data), status, headers, config);
			});
		}

		function refreshTournoi(aTournoiId, successCallBack) {
			$http.get('http://localhost:8080/petanque/tournois/' + aTournoiId).
			success(function(data, status, headers, config) {
				successCallBack(filteredTours(data), status, headers, config);
			});
		}

		function getAllTournois(successCallBack) {
			$http.get('http://localhost:8080/petanque/tournois').
			success(function(data, status, headers, config) {
				successCallBack(data, status, headers, config);
			});
		}

	}
})();