/**
 * @author Christina Brunner
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
	
	InventoryCtrl.$inject = ['$http', '$route'];
	
	function InventoryCtrl($http, $route) {
		
		var vm = this;
		vm.products = {};
		vm.deleteInventoryProduct = deleteInventoryProduct;
		
		init();
		
		function init() {
			$http.get(URL_API+'inventory').then(function(resp) {
				vm.products = resp.data;
			}, function(error) {
				alert("You have no products in your inventory");
				//console.dir(error);
			});
		}
		
		function deleteInventoryProduct(id) {
			$http.delete(URL_API+'inventory'+'/'+id).then(function(response){
			    	   if (!response.data) {
			    		   $route.reload();
						}
						else {
							alert("Fehler: "+response.data);
						}
			       }, 
			       function(response){
			         alert("Something went wrong - Product could not be deleted. "+response.data);
			       }
			    );
		 }
		
	}
	
	
})();