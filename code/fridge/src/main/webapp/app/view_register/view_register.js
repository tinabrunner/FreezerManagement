/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_register', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_register', {
			templateUrl: 'view_register/view_register.html',
			controller: 'view_register1Ctrl'
		});
	}])
	.controller('view_register1Ctrl', function($scope) {
		$scope.Register = function() {
			if (!$scope.username || !$scope.password || !$scope.password_confirm || !$scope.firstname || !$scope.lastname || !$scope.email)
				alert("Please fill all fields!");
			else if ($scope.password != $scope.password_confirm)
				alert("The passwords don't match!");
			else {
				var username = $scope.username;
				var password = $scope.password;
				var password_confirm = $scope.password_confirm;
				var firstname = $scope.firstname;
				var lastname = $scope.lastname;
				var email = $scope.email;
				alert("You will be registered now!");
								
				// kommunikation mit java controller !!
				// --> wenn Register-Methode TRUE zurück gibt: einloggen und redirectTo: '/view1';
				// --> wenn FALSE zur erneuten Eingabe auffordern
				
				// var accountController = new java.controller.AccountController();
			}
	    }
	});
	
})();

/*
	.controller('view_register1Ctrl', RegisterCtrl);

	RegisterCtrl.$inject = ['$http'];

	
	function RegisterCtrl($http) {
		var vm = this;

	};
})();
*/