(function() {
	'use strict';

	angular
		.module('Petanque')
		.factory('PartieService', PartieService);

	/* @ngInject */
	function PartieService() {
		var _selectedComplementairePartie = {
			'lastSelected': '-1'
		};
		var _selectedPrincipalPartie = {
			'lastSelected': '-1'
		};
		var service = {
			getLastPartieSelected: getLastPartieSelected,
			setLasPartieSelected: setLasPartieSelected
		};
		return service;

		////////////////

		function getLastPartieSelected(isPrincipal) {
			return isPrincipal ? _selectedPrincipalPartie : _selectedComplementairePartie;
		}

		function setLasPartieSelected(aPartieId, isPrincipal) {
			if (isPrincipal) {
				_selectedPrincipalPartie.lastSelected = aPartieId;
			} else {
				_selectedComplementairePartie.lastSelected = aPartieId;
			}
		}
	}
})();