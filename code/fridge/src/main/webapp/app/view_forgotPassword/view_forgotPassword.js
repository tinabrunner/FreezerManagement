/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_forgotPassword', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_forgotPassword', {
			templateUrl: 'view_forgotPassword/view_forgotPassword.html',
			controller: 'view_fp1Ctrl'
		});
	}])

	.controller('view_fp1Ctrl', FpCtrl);

	FpCtrl.$inject = ['$http'];

	
	function FpCtrl($http) {
		var vm = this;

	};
	


})();