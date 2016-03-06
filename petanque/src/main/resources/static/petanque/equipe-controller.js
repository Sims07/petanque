(function() {
	'use strict';

	angular
		.module('Petanque')
		.controller('EquipeCtrl', EquipeCtrl);

	/* @ngInject */
	function EquipeCtrl(TournoiService, PetanqueDataService, $scope, $rootScope) {
		/*jshint validthis: true */
		var vm = this;
		vm.title = 'EquipeCtrl';
		vm.joueur1 = {};
		vm.joueur2 = {};
		vm.inscritDansLePrincipal = true;
		vm.ajouterEquipe = ajouterEquipe;
		vm.equipes = [];
		vm.principaleEquipes = [];
		vm.complementaireEquipes = [];
		vm.updateFilter = updateFilter;

		vm.finscritDansLePrincipal = true;
		vm.finscritDansLeComplementaire = true;

		function updateFilter() {
			if (vm.finscritDansLePrincipal && vm.finscritDansLeComplementaire) {
				vm.searchFilter.filterPrincipal = 'true';
			} else {
				vm.searchFilter.filterPrincipal = vm.finscritDansLePrincipal + ' ' + vm.finscritDansLeComplementaire;
			}
		}


		vm.searchFilter = {
			'filterPrincipal': 'true'
		};

		activate();

		function activate() {
			$scope.$on('tournoiLoaded', function(event, args) {
				vm.equipes = args.equipes;
				for (let e of vm.equipes) {
					e.filterPrincipal = e.inscritDansLePrincipal + ' ' + e.inscritDansLeComplementaire;
				}
			});
			$("#joueur1").focus();
			$scope.$on('equipeSelected', function(event, args) {
				vm.joueur1.nom = args.joueurs[0].nom;
				vm.joueur2.nom = args.joueurs[1].nom;
				vm.inscritDansLePrincipal = false;
			});
		}

		function ajouterEquipe() {
			var equipeToCreate = {};
			equipeToCreate.inscritDansLePrincipal = vm.inscritDansLePrincipal;
			equipeToCreate.inscritDansLeComplementaire = !vm.inscritDansLePrincipal;
			equipeToCreate.joueurs = [{
				"nom": "" + vm.joueur1.nom
			}, {
				"nom": "" + vm.joueur2.nom
			}];
			if ((equipeToCreate.inscritDansLePrincipal && !PetanqueDataService.getCurrentTournoi().principal.tirageAuSortFait) || (equipeToCreate.inscritDansLeComplementaire && !PetanqueDataService.getCurrentTournoi().complementaire.tirageAuSortFait)) {
				TournoiService.ajouterEquipe(equipeToCreate, PetanqueDataService.getCurrentTournoi().id, function(data, status, headers, config) {
					refreshEquipes(data);
					vm.joueur1 = {};
					vm.joueur2 = {};
				});
				$("#joueur1").focus();
			} else {
				alert('L équipe ne peut être ajouté.Le tournoi a déjà commencé.');
			}

		}


		function refreshEquipes(aTournoi) {
			vm.equipes = aTournoi.equipes;
			$rootScope.$broadcast('equipeStatsUpdate', aTournoi.stats);
			for (let e of vm.equipes) {
				e.filterPrincipal = e.inscritDansLePrincipal + ' ' + e.inscritDansLeComplementaire;
			}
		}
	}
})();