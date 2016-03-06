(function() {
	'use strict';

	angular
		.module('Petanque')
		.controller('tournoiCtrl', ['$modal', '$rootScope', 'PetanqueDataService', 'TournoiService', '$routeParams', '$location', '$scope',
			tournoiCtrl
		]);

	/* @ngInject */
	function tournoiCtrl($modal, $rootScope, PetanqueDataService, TournoiService, $routeParams, $location, $scope) {
		/*jshint validthis: true */
		var vm = this;
		vm.title = 'tournoiCtrl';
		vm.currentTournoi = {};
		vm.tirageAuSort = tirageAuSort;
		vm.principale = {};
		var _tournoiId = $routeParams.tournoiId;

		activate();

		function activate() {
			//load all tournois
			if (_tournoiId != -1) {
				loadTournoi(_tournoiId);
			} else {
				showCreationPopup();
			}
			$scope.$on('tournoiScore', function(event, args) {
				vm.currentTournoi.principal.statistics = args[0].principal.statistics;
				vm.currentTournoi.complementaire.statistics = args[0].complementaire.statistics;
				//principal
				var tour = args[2];
				var newTours;
				var tours;
				if (args[1]) {
					//UPDATE all tour excpet the current
					tours = vm.currentTournoi.principal.tours;
					newTours = args[0].principal.tours;
				} else {
					tours = vm.currentTournoi.complementaire.tours;
					newTours = args[0].complementaire.tours;
				}
				for (var i = 0; i < tours.length; i++) {
					if (tours[i].nbTour != tour.nbTour) {
						tours[i] = newTours[i]
					}
				};
			});

			$scope.$on('equipeStatsUpdate', function(event, args) {
				vm.currentTournoi.stats = args;
			});

		}

		function tirageAuSort(isPrincipal) {
			if (confirm('Voulez vous vraiment lancer le tirage au sort? ')) {
				TournoiService.tirageAuSort(vm.currentTournoi.id, isPrincipal, function(data, status, headers, config) {
					vm.currentTournoi = data;
				});
			}

		}

		function loadTournoi(idTournoi) {
			TournoiService.refreshTournoi(_tournoiId, function(data, status, headers, config) {
				vm.currentTournoi = data;
				PetanqueDataService.setCurrentTournoi(data);
				$rootScope.$broadcast('tournoiLoaded', vm.currentTournoi);
			});
		}

		function showCreationPopup() {
			var popup = $modal.open({
				templateUrl: 'partials/creation_tournoi.html',
				controller: 'CreationTournoiCtrl',
				controllerAs: 'vm',
				backdrop: 'static',
				keyboard: false,
				size: 'lg'
			});
			popup.result.then(function(aTournoi) {
				vm.currentTournoi = aTournoi;
				$location.path('/creationTournoi/' + aTournoi.id);
				PetanqueDataService.setCurrentTournoi(aTournoi);
			});
		}
	}
})();