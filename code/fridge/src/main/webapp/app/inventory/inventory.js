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

	.controller('view_inventory1Ctrl', InventoryCtrl);

	InventoryCtrl.$inject = ['$http'];

	
	function InventoryCtrl($http) {
		var vm = this;

	};
	


})();