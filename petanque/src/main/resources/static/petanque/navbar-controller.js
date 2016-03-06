(function() {
	'use strict';

	angular
		.module('Petanque')
		.controller('NavBarCtrl', NavBarCtrl);

	/* @ngInject */
	function NavBarCtrl($scope, $location) {
		/*jshint validthis: true */
		var vm = this;
		vm.title = 'NavBarCtrl';
		vm.tournoiId;
		vm.isManagerView = isManagerView;
		vm.tournoiName = '';

		activate();

		function isManagerView() {
			return $location.$$url.indexOf('tableauJoueur') < 0;
		}

		function activate() {
			$scope.$on('tournoiLoaded', function(event, args) {
				vm.tournoiId = args.id;
				vm.tournoiName = args.nom;
			})

		}
	}
})();