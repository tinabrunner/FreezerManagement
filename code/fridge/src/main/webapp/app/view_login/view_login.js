/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_login', ['ngRoute', 'ngCookies'])
	
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_login', {
			templateUrl: 'view_login/view_login.html',
			controller: 'view_login1Ctrl'
		});
	}])
	.controller('view_login1Ctrl', function($scope, $http, $cookies) {
		$scope.Login = function() {
			if (!$scope.username || !$scope.password)
				alert("Please enter username and password!");
			else {
				var objData = {
						username : $scope.username,
						password : $scope.password
		        };
				$http.post(URL_API+'login', objData).then((function(response){
					alert("Passt: "+response.data);
					$cookies.put('username', response.data);
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});
				
			}
	    }
		
	});
	
})();
	
