/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_register', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_register', {
			templateUrl: 'view_register/view_register.html',
			controller: 'view_register1Ctrl'
		});
	}])

	.controller('view_register1Ctrl', RegisterCtrl);

	RegisterCtrl.$inject = ['$http'];

	
	function RegisterCtrl($http) {
		var vm = this;

	};
	


})();