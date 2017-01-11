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

	SettingsCtrl.$inject = ['$http'];

	function SettingsCtrl($http) {
		var vm = this;

		vm.init = init;

		init();

		function init() {

		}
	}

})();