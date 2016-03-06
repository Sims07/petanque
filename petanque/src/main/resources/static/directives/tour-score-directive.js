(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('tourscore', tourscore);

	/* @ngInject */
	function tourscore(TournoiService, PetanqueDataService, PartieService, $rootScope) {
		// Usage:
		//
		// Creates:
		//
		var vm = "";

		var directive = {
			link: link,
			restrict: 'E',
			scope: {
				tour: "=tour",
				tournoi: "=",
				principal: "=principal",
				tourfilter: '='
			},
			templateUrl: 'directives/tour-score-directive.html'

		};
		return directive;

		function link(scope, element, attrs) {
			scope.marquerLeScorePartie = marquerLeScorePartie;
			scope.lastPartieId = PartieService.getLastPartieSelected(scope.principal);
		}

		function marquerLeScorePartie(aPartie, aprincipal, aTournoi, aTour) {
			//vm.lastPartieId = aPartie.id;
			PartieService.setLasPartieSelected(aPartie.id, aprincipal);
			TournoiService.marquerLeScorePartie(aPartie, PetanqueDataService.getCurrentTournoi().id, aTournoi.id, aTour.id, aprincipal, function(resTournoi, status, headers, config) {
				$rootScope.$broadcast('tournoiScore', [resTournoi, aprincipal, aTour]);
			});
		}

	}
})();