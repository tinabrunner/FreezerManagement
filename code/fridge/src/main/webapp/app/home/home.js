(function() {
	'use strict';

	angular.module('fridge.home', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/home', {
			templateUrl: 'home/home.html',
			controller: 'HomeCtrl'
		});
	}])

	.controller('HomeCtrl', HomeCtrl);

	HomeCtrl.$inject = [];

	function HomeCtrl($http) {
		var vm = this;

		
	}

})();