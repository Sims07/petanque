(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('equipe', equipe);

	/* @ngInject */
	function equipe(TournoiService, $rootScope, PetanqueDataService) {
		// Usage:
		//
		// Creates:
		//
		var directive = {
			link: link,
			restrict: 'E',
			scope: {
				equipe: '=equipemodel'
			},
			templateUrl: 'directives/equipe-directive.html'
		};
		return directive;

		function link(scope, element, attrs) {
			scope.supprimerEquipe = supprimerEquipe;
			scope.selectEquipe = function selectEquipe(equipe) {
				scope.$emit('equipeSelected', equipe);
			};
		}

		function supprimerEquipe(equipe) {
			if (confirm('Voulez vous vraiment suprimer l equipe ' + equipe.numero + ' ? ')) {
				TournoiService.supprimerUneEquipe(PetanqueDataService.getCurrentTournoi().id, equipe.id, function(resTournoi, status, headers, config) {
					$rootScope.$broadcast('tournoiLoaded', resTournoi);
				});
			}
		}


	}
})();