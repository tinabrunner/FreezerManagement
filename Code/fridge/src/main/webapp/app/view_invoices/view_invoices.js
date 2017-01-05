(function() {
	'use strict';

	angular.module('fridge.invoices', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/invoices', {
			templateUrl: 'view_invoices/view_invoices.html',
			controller: 'view_invoices1Ctrl'
		});
	}])

	/*.controller('view_inventory1Ctrl', DeleteInvProduct);

	DeleteInvProduct.$inject = ['$http'];

	
	function DeleteInvProduct($http) {
		var vm = this;
		altert("delete this product");
	};*/
	
//	.controller('view_invoices1Ctrl', function($scope) {
		
		// OnPageLoad-Method um tabelle zu füllen
		
		//$scope.DeleteInvProduct = function() {
			// delete the selected product
//			redirectTo: '/view1';
//	    }
//		
//	});
	


})();