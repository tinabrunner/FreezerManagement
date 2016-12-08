(function() {
	'use strict';

	angular.module('fridge.view2', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view2', {
			templateUrl: 'view2/view2.html',
			controller: 'View2Ctrl'
	});
	}])
	.controller('View2Ctrl', View2Ctrl);

	View2Ctrl.$inject = [];

	function View2Ctrl() {
		var vm = this;
		vm.blaaa = blaaa;

		function blaaa() {
			alert("blaaa");
		}
	}

})();