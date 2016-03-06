(function() {
	'use strict';

	angular
		.module('Petanque')
		.factory('PetanqueDataService', PetanqueDataService);

	/* @ngInject */
	function PetanqueDataService() {

		var selectedPrincipalTour = {
			'currentTour': '1'
		};
		var selectedComplementaireTour = {
			'currentTour': '1'
		};
		var currentTournoi = {};
		var service = {
			getCurrentTournoi: getCurrentTournoi,
			setCurrentTournoi: setCurrentTournoi,
			getSelectedTour: getSelectedTour,
			setSelectedTour: setSelectedTour,
			selectedPrincipalTour: selectedPrincipalTour,
			selectedComplementaireTour: selectedComplementaireTour,
			currentTournoi: currentTournoi
		};
		return service;


		////////////////

		function getCurrentTournoi() {
			return this.currentTournoi;
		}

		function setCurrentTournoi(aTournoi) {
			this.currentTournoi = aTournoi;
		}

		function getSelectedTour(principal) {
			return principal ? this.selectedPrincipalTour : this.selectedComplementaireTour;
		}

		function setSelectedTour(principal, aCurrentTour) {
			if (principal) {
				this.selectedPrincipalTour.currentTour = aCurrentTour;
			} else {
				this.selectedComplementaireTour.currentTour = aCurrentTour;
			}
		}
	}
})();