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
/*
	.controller('InventoryCtrl', function($scope) {
		
		$scope.Inventory = function() {
			$scope.products = {};
			$scope.deleteInventoryProduct = deleteInventoryProduct;
			$scope.init = init;
			
			init();
			
			function init() {
				var data = {
					name : "prod1",
					prodCategoryID : "id001"
				}
				$http.get(URL_API+'inventory').then(function(resp) {
					$scope.products = resp.data.products;
			    	//vm.products = resp.data.products;
				}, function(error) {
					console.dir(error);
				});
			}
			
			function deleteInventoryProduct(product) {
				alert('delete '+prod.name);
					//redirectTo: '/home';
			 }
	    }
	});*/
	
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
				alert("geht bis vor vm.products");
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		function deleteInventoryProduct(name, id) {
			alert('delete product '+name+' with id '+id);
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