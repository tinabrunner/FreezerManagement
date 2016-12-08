(function() {
	'use strict';

	angular.module('myApp.shopping_cart', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/shoppingcart', {
			templateUrl: 'shopping_cart/shopping_cart.html',
			controller: 'ShoppingCartCtrl'
	});
	}])
	.controller('ShoppingCartCtrl', ShoppingCartCtrl);

	ShoppingCartCtrl.$inject = [];

	function ShoppingCartCtrl() {
		var vm = this;

	}

})();