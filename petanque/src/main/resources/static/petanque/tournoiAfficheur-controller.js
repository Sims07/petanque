(function() {
	'use strict';

	angular
		.module('Petanque')
		.controller('TournoiAfficheurCtrl', TournoiAfficheurCtrl);

	/* @ngInject */
	function TournoiAfficheurCtrl(TournoiService, $routeParams, $scope) {
		/*jshint validthis: true */
		var vm = this;
		var stompClient = null;
		vm.title = 'TournoiAfficheurCtrl';

		vm.sousTournoi = {};
		vm.tournoiId = $routeParams.tournoiId;
		vm.isManager = $routeParams.tournoiId;
		vm.connect = connect;
		vm.hide = false;
		vm.disconnect = disconnect;
		vm.connected = {};
		var _isPrincipal = $routeParams.isPrincipal;
		activate();

		function activate() {
			TournoiService.getTournoiById(vm.tournoiId, _isPrincipal, function(data, status, headers, config) {

				vm.sousTournoi = _isPrincipal == 'true' ? data.principal : data.complementaire;
			});
			disconnect();
		}



		function connect() {
			var socket = new SockJS('/hello');
			stompClient = Stomp.over(socket);
			stompClient.connect({}, function(frame) {
				console.log('Connected: ' + frame);
				setConnect(true);
				stompClient.subscribe('/topic/updateTournoi', function(greeting) {
					updateTournoi(greeting);

				});
			});
		}

		function setConnect(connected) {
			$scope.$apply(vm.connected = connected);
		}

		function disconnect() {
			if (stompClient != null) {
				stompClient.disconnect();
			}
			vm.connected = false;
			console.log("Disconnected");
		}



		function updateTournoi(message) {
			var tmpTournoi = angular.fromJson(message.body);
			if (tmpTournoi.id == vm.tournoiId) {
				$scope.$apply(vm.sousTournoi = tmpTournoi);

				/**TournoiService.getTournoiById(vm.tournoiId, _isPrincipal, function(data, status, headers, config) {

					vm.sousTournoi = _isPrincipal == 'true' ? data.principal : data.complementaire;
				});*/
			}
		}
	}
})();