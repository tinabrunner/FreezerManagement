(function() {
	'use strict';

	angular.module('myApp.view1', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view1', {
			templateUrl: 'view1/view1.html',
			controller: 'View1Ctrl'
		});
	}])

	.controller('View1Ctrl', View1Ctrl);

	View1Ctrl.$inject = ['$http'];

	function View1Ctrl($http) {
		var vm = this;
		vm.blubbiblub = "testiii2";
		/*
		$http.get('http://localhost:8080/freezers/api/freezer').then(function(resp) {
			vm.blubbiblub = resp.data.test;
		}, function(error) {
			console.dir(error);
			alert("err");
		});
		*/
	}

})();