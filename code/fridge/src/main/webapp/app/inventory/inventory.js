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
	
	function InventoryCtrl($http) {
		
		var vm = this;
		vm.products = {};
		vm.deleteInventoryProduct = deleteInventoryProduct;
		vm.init = init;
		
		init();
		
		function init() {
			var data = {
				name : "prod1",
				prodCategoryID : "id001"
			}
			$http.get(URL_API+'inventory').then(function(resp) {
				vm.products = resp.data.products;
		    	//vm.products = resp.data.products;
			}, function(error) {
				console.dir(error);
			});
		}
		
		function deleteInventoryProduct(product) {
			alert('delete '+prod.name);
				//redirectTo: '/home';
		 }
		
		/*$scope.DeleteInvProduct = function(id) { 
			alert('delete '+prod.name);
			
			$http.get(URL_API+'inventory').then(function(resp) {
				alert("product was deleted");
				//vm.products = resp.data.products;
			}, function(error) {
				console.dir(error);
			});
		}*/

		
	}
	
	
		
	//});
	


})();