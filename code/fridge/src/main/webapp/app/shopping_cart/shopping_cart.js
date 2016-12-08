/* 8.12.16 phi */
(function() {
	'use strict';

	angular.module('fridge.shopping_cart', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/shoppingcart', {
				templateUrl: 'shopping_cart/shopping_cart.html',
				controller: 'ShoppingCartCtrl'
			});
		}])
		.controller('ShoppingCartCtrl', ShoppingCartCtrl);

	ShoppingCartCtrl.$inject = ['$http'];

	function ShoppingCartCtrl($http) {
		var vm = this;
		vm.products = {};
		vm.empty = empty;
		vm.deleteProduct = deleteProduct;

		$http.get(URL_API+'shopping_cart').then(function(resp) {
			vm.products = resp.data.products;
		}, function(error) {
			console.dir(error);
		});

		function deleteProduct(prod) {
			alert('delete '+prod.name);
		}
	}

})();