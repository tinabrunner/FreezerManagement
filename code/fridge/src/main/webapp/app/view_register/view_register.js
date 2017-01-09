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
	.controller('view_register1Ctrl', function($scope, $http, $location) {
		$scope.Register = function() {
			
			if (!$scope.username || !$scope.password || !$scope.password_confirm || !$scope.firstName || !$scope.lastName || !$scope.email || !$scope.role)
				alert("Please fill all fields!");
			else if ($scope.password != $scope.password_confirm)
				alert("The passwords don't match!");
			else {
				var objData = {
						firstName : $scope.firstName,
						lastName : $scope.lastName,
						username : $scope.username,
						password : $scope.password,
						email : $scope.email,
						role : $scope.role
		        };
				$http.post(URL_API+'account', objData).then((function(response){
				if (response.data) {
					alert("Fehler: "+response.data);
				}
				else {
					// TODO: Login !!!
					$location.path("/home");
				}
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});
				
			}
	    }
	});
	
})();
