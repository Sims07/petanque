(function() {
	'use strict';

	angular
		.module('Petanque')
		.directive('stats', stats);

	/* @ngInject */
	function stats() {
		// Usage:
		//
		// Creates:
		//
		var directive = {
			link: link,
			restrict: 'E',
			scope: {
				stats: '=stats',
				statsequipe: '=statsequipe'
			},
			templateUrl: 'directives/stats-directive.html'
		};
		return directive;



		function link(scope, element, attrs) {}
	}
})();