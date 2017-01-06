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

		vm.createOrder = function() {
			alert(123);
		}
	}

})();