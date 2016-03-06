(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('partie', partie);

	/* @ngInject */
	function partie() {
		// Usage:
		//
		// Creates:
		//
		var directive = {
			link: link,
			restrict: 'E',
			scope: {
				partie: '=partie',
				tour: '=tour',
				activetour: '=',
				index: '=index'
			},
			templateUrl: 'directives/partie-directive.html'
		};
		return directive;

		function link(scope, element, attrs) {
			scope.getMarginLeft = getMarginLeft;
			scope.partieWidth = 20;
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