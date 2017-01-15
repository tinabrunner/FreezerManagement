/**
 * @author Christina Brunner
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
	.controller('view_login1Ctrl', function($scope, $http, $cookies, $location, $window) {
		$scope.Login = function() {
			if (!$scope.username || !$scope.password)
				alert("Please enter username and password!");
			else {
				var objData = {
						username : $scope.username,
						password : $scope.password
		        };
				$http.post(URL_API+'login', objData).then((function(response){
					if (response.data) {
						$cookies.put('token', response.data);
						$location.path("/home");
					}
					else
						alert("Login failed");
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});	
			}
	    };
	    
	    $scope.Register = function() {
	    	$location.path("/view_register");
	    }
		
	});
	
})();
	
