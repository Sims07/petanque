(function() {
	'use strict';

	angular
		.module('Petanque')
		.factory('Equipes', ['$resource',
			function($resource) {
				return $resource('/equipes/:id', {}, {
					query: {
						method: 'GET',
						isArray: false
					}
				});
			}
		]);


})();