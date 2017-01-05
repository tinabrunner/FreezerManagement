(function() {
	'use strict';

	angular.module('fridge.invoices', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/invoices', {
			templateUrl: 'view_invoices/view_invoices.html',
			controller: 'InvoiceCtrl'
		});
	}])

	.controller('InvoiceCtrl', InvoiceCtrl);

	InvoiceCtrl.$inject = ['$http'];

	function InvoiceCtrl($http) {
		/* --- SCOPE --- (worauf .html zugreifen kann) */
		var vm = this;
		vm.invoices = {};
		vm.init = init;

		init();

		function init() {
			$http.get(URL_API+'invoices').then(function(resp) {
				vm.invoices = resp.data.list;
			}, function(error) {
				console.dir(error);
			});
		}


		/* --- --- */
	}


})();