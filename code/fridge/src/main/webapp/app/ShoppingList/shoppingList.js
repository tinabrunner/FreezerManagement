/* 8.12.16 phi */
(function() {
	'use strict';

	angular.module('fridge.shoppingList', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/shoppinglist', {
				templateUrl: 'ShoppingList/shoppingList.html',
				controller: 'ShoppingListCtrl'
			});
		}])
		.controller('ShoppingListCtrl', ShoppingListCtrl);

	ShoppingListCtrl.$inject = ['$http'];

	function ShoppingListCtrl($http) {
		var vm = this;
		
		vm.products = {};
		vm.init = getAllProductsFromShoppingList;	
			
		
		// Load products from ShoppingList when "entering"
		getAllProductsFromShoppingList();
		
		function getAllProductsFromShoppingList(){
			$http.get(URL_API+'shoppinglist').then(function(resp) {
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		function deleteProduct(prodId) {
								
			$http.delete(URL_API+'shoppinglist/' + prodId).then(function(resp){				
				if (resp.data == true){
					getAllProductsFromShoppingList();					
				} 
			}, function(error){
				console.dir(error);
			})
		}
		
		vm.deleteProduct = function(product) {
			deleteProduct(product);
		}
		
		vm.addProduct = function() {
			alert("Redirect this button to inventory/productlist");
		}
	}

})();