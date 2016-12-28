/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_login', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_login', {
			templateUrl: 'view_login/view_login.html',
			controller: 'view_login1Ctrl'
		});
	}])

	.controller('view_login1Ctrl', LoginCtrl);

	LoginCtrl.$inject = ['$http'];

	
	function LoginCtrl($http) {
		var vm = this;
		
		var username = parseString(vm.username);
		var password = parseString(vm.password);
		
		alert("login: "+username+", "+password);
	};
	


})();