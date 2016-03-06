(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('tournoiscore', tournoiscore);

	/* @ngInject */
	function tournoiscore(TournoiService, PetanqueDataService) {
		// Usage:
		//
		// Creates:
		//
		var _innerScope;

		var directive = {

			restrict: 'E',
			scope: {
				tournoi: "=tournoi",
				currentTour: '=currenttour',
				principal: '='
			},
			link: link,
			templateUrl: 'directives/tournoi-score-directive.html'

		};
		return directive;

		function link(scope, element, attrs) {
			scope.activeTour = PetanqueDataService.getSelectedTour(scope.principal);
			scope.setActiveTour = setActiveTour;
			//scope.getPartiesReels = getPartiesReels;
			scope.partieterminerfilter = false;
		}

		function setActiveTour(aTour, principal) {
			PetanqueDataService.setSelectedTour(principal, aTour.nbTour);
		}

		/**function getPartiesReels(tour) {
			if (tour === undefined) {
				return;
			}
			var newTour = {};
			newTour.id = tour.id;
			var filteredParties = [];
			newTour.parties = filteredParties;

			angular.forEach(tour.parties, function(item) {
				if (!item.equipe1.fausseEquipe || item.equipe2.fausseEquipe) {
					filteredParties.push(item);
				}
			});

			return newTour;
		}*/

	}
})();