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
		vm.login_submit = function() {
			alert("click");
		};
		
		
	}

})();