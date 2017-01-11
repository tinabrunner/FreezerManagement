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
		vm.success = false;
		vm.empty = empty;
		vm.deleteProduct = deleteProduct;
		vm.totalPrice = totalPrice;
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

		function totalPrice() {
			var price = 0;
			for(var i=0; i<vm.products.length; i++) {
				price += vm.products[i].preis * vm.products[i].amount / vm.products[i].verpackungsGroesse;
			}
			return price;
		}

		function submitOrder() {
			$http.post(URL_API+'shopping_cart', vm.products).then(function(resp) {
				vm.success = true;
			}, function(eror) {
				console.dir(error);
			})
		}
	}

})();