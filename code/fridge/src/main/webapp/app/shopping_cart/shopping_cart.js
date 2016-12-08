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

		//$http.get('')
	}

})();