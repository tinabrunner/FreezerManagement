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
	.controller('view_register1Ctrl', function($scope, $http) {
		$scope.Register = function() {
			if (!$scope.username || !$scope.password || !$scope.password_confirm || !$scope.firstname || !$scope.lastname || !$scope.email)
				alert("Please fill all fields!");
			else if ($scope.password != $scope.password_confirm)
				alert("The passwords don't match!");
			else {
				var dada = {
						name : $scope.firstname,
						surname : $scope.lastname,
						username : $scope.username,
						password : $scope.password,
						email : $scope.email,
						userrole : $scope.usertype //('input:checkbox[name="reg_usertype"]:checked')
				}
				$http.post(URL_API+"account", dada).then((function(response){
					if (response.data)
						redirectTo: '/home';
				}), 
				alert("Something went wrong"));
				
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