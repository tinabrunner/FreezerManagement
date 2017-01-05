/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.inventory', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/inventory', {
			templateUrl: 'inventory/inventory.html',
			controller: 'view_inventory1Ctrl'
		});
	}])

	/*.controller('view_inventory1Ctrl', DeleteInvProduct);

	DeleteInvProduct.$inject = ['$http'];

	
	function DeleteInvProduct($http) {
		var vm = this;
		altert("delete this product");
	};*/
	
	.controller('view_inventory1Ctrl', function($scope) {
		
		// OnPageLoad-Method um tabelle zu füllen
		
		$scope.DeleteInvProduct = function() {
			// delete the selected product
			redirectTo: '/home';
	    }
		
	});
	


})();