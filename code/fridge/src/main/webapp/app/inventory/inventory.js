/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.inventory', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/inventory', {
			templateUrl: 'inventory/inventory.html',
			controller: 'InventoryCtrl'
		});
	}])
	
	.controller('InventoryCtrl', InventoryCtrl);
	
	InventoryCtrl.$inject = ['$http'];
	
	function InventoryCtrl($http, products) {
		
		var vm = this;
		vm.products = {};
		vm.deleteInventoryProduct = deleteInventoryProduct;
		vm.init = init;
		
		init();
		
		function init() {
			$http.get(URL_API+'inventory').then(function(resp) {
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		function deleteInventoryProduct(name, id) {
			alert('delete product '+name+' with id '+id);
			// TODO: delete product from DB and reload
		 }
		
	}
	
	
})();