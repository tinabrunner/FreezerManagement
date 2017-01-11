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
		/* --- SCOPE --- (worauf .html zugreifen kann) */
		var vm = this;
		vm.products = {};
		vm.empty = empty;
		vm.deleteProduct = deleteProduct;
		vm.submit = submitOrder;
		vm.init = init;

		init();

		function init() {
			$http.get(URL_API+'shopping_cart').then(function(resp) {
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}

		function deleteProduct(prod) {
			//this.products = this.products.filter( p => p.id != prod.id );
			for(var i=0; i<vm.products.length; i++) {
				if( vm.products[i] === prod) {
					vm.products.splice(i, 1);
				}
			}
		}

		function submitOrder() {
			console.dir(vm.products); // todo trigger OrderSender
		}

		/* --- --- */
	}

})();