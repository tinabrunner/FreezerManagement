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
		var btn_login = vm.getElementById("btn_login");
		
		btn_login.addEventListener("click", GetLoginParams);
		
	};
	
	function GetLoginParams () {
		var vm = this;
		var username = vm.getElementById("lg_username");
		var password = vm.getElementById("lg_password");
		LoginController.Login(username, password);
	};

})();