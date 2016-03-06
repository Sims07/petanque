(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('tournoi', tournoi);

	/* @ngInject */
	function tournoi() {


		// Usage:
		//
		// Creates:
		//

		var directive = {
			link: link,
			restrict: 'E',
			scope: {
				tours: '='
			},
			templateUrl: 'directives/tournoi-directive.html'
		};
		return directive;

		function link(scope, element, attrs) {
			scope.getMarginLeft = getMarginLeft;
		}

		function getMarginLeft(tour) {
			if (tour == 1) {
				return 22;
			} else {
				return 2 * getMarginLeft(tour - 1);
			}
		}
	}
})();