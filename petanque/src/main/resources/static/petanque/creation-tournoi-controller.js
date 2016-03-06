(function() {
	'use strict';

	angular
		.module('Petanque')
		.controller('CreationTournoiCtrl', ['$modalInstance', 'TournoiService', '$scope', CreationTournoiCtrl]);

	/* @ngInject */
	function CreationTournoiCtrl($modalInstance, TournoiService, $scope) {
		/*jshint validthis: true */
		var vm = this;
		vm.tournoi = {};
		vm.creerTournoi = creerTournoi;
		vm.selectTournoi = selectTournoi;
		vm.tournois = {};
		vm.selectedTournoi = {};
		activate();

		function activate() {
			$scope.$on('currentTournoi', function(aData) {
				vm.currentTournoi = aData;
			});
			loadAllTournoi();

		}

		function loadAllTournoi() {
			var callBack = function(resTournoi, status, headers, config) {
				vm.tournois = resTournoi;
			}
			TournoiService.getAllTournois(callBack);
		}

		function creerTournoi() {
			var callBack = function(resTournoi, status, headers, config) {
				$modalInstance.close(resTournoi);
			}
			var errorCallBack = function(resTournoi, status, headers, config) {
				$modalInstance.close(resTournoi);
			}
			TournoiService.saveTournoi(vm.tournoi, callBack, errorCallBack);
		}

		function selectTournoi() {
			$modalInstance.close(vm.selectedTournoi);
		}
	}
})();