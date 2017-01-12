/**
 * User: phi
 * Date: 11.01.17
 .___.
 {o,o}
 /)___)
 --"-"--
 */
(function() {
	'use strict';

	angular.module('fridge.settings', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/settings', {
				templateUrl: 'settings/settings.html',
				controller: 'SettingsCtrl'
			});
		}])
		.controller('SettingsCtrl', SettingsCtrl);

	SettingsCtrl.$inject = ['$http', '$timeout'];

	function SettingsCtrl($http, $timeout) {
		var vm = this;
		vm.config = {};
		vm.success = false;
		vm.init = init;
		vm.change = change;
		vm.days = [
			{ 'id': 1, 'name':'Monday'},
			{ 'id': 2, 'name':'Tuesday'},
			{ 'id': 3, 'name':'Wednesday'},
			{ 'id': 4, 'name':'Thursday'},
			{ 'id': 5, 'name':'Friday'},
			{ 'id': 6, 'name':'Saturday'},
			{ 'id': 7, 'name':'Sunday'}
		];

		init();

		function init() {
			$http.get(URL_API+"settings").then(function(resp) {
				vm.config = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}

		function change() {
			$http.post(URL_API+"settings", vm.config).then(function(resp) {
				/* show "saved" for 1,5 sek */
				vm.success = true;
				$timeout(function() {
					vm.success = false;
				}, 1500)
			}, function(error) {
				console.dir(error);
			});
		}
	}

})();