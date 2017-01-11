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

	SettingsCtrl.$inject = ['$http', '$scope'];

	function SettingsCtrl($http, $scope) {
		var vm = this;
		vm.config = {};
		vm.init = init;

		init();

		function init() {
			/** deep watch config & PUT on change */
			$scope.$watch(function() {
				return vm.config;
			}, function() {

			}, true);
		}
	}

})();