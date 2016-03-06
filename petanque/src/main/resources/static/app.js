(function() {
	'use strict';

	angular
		.module('Petanque', ['ngResource', 'ngRoute',
			'tournoiCtrl', 'EquipeCtrl', 'CreationTournoiCtrl', 'TournoiAfficheurCtrl', 'ui.bootstrap', 'PetanqueFilters'
		]);

	angular
		.module('tournoiCtrl', []);
	angular
		.module('EquipeCtrl', []);
	angular
		.module('CreationTournoiCtrl', []);
	angular
		.module('TournoiAfficheurCtrl', []);
	angular
		.module('NavBarCtrl', []);
	angular
		.module('PetanqueFilters', []);


	angular
		.module('Petanque').config(['$routeProvider',
			function($routeProvider) {
				$routeProvider.
				when('/creationTournoi/:tournoiId', {
					templateUrl: 'partials/gerer_tournoi.html',
					controller: 'tournoiCtrl',
					controllerAs: 'vm'
				}).
				when('/tableauJoueursTournoi/:tournoiId/:isPrincipal', {
					templateUrl: 'partials/tableau_tournoi.html',
					controller: 'TournoiAfficheurCtrl',
					controllerAs: 'vm'
				}).
				when('/tableauManagerTournoi/:tournoiId/:isPrincipal', {
					templateUrl: 'partials/tableau_tournoi.html',
					controller: 'TournoiAfficheurCtrl',
					controllerAs: 'vm'
				}).
				otherwise({
					redirectTo: '/creationTournoi/-1'
				});
			}
		]);
})();