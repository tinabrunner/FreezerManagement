/* 10.01.2017 JD */
(function() {
	'use strict';

	angular.module('fridge.productList', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/productlist', {
				templateUrl: 'productList/productList.html',
				controller: 'ProductListCtrl'
			});
		}])
		.controller('ProductListCtrl', ProductListCtrl);

	ProductListCtrl.$inject = ['$http'];

	function ProductListCtrl($http) {
		var vm = this;
		
		
		
		vm.products = {};
		vm.init = getAllProductsFromSupermarket;	
			
		
		// Load products from Supermarket when "entering"
		getAllProductsFromSupermarket();
		
		function getAllProductsFromSupermarket(){
			$http.get(URL_SUPERMARKET+'productlist').then(function(resp) {				
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		vm.addToShoppingList = function(product) {			
			$http({
				url: URL_API + 'shoppinglist/',
				data: product,
				method: "PUT",
				headers: {
					'Content-type': 'application/json',
					'Accept': 'application/json'
				}
			})
			
		}	
			
	}

})();