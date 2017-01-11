/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.view_login', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/view_login', {
			templateUrl: 'view_login/view_login.html',
			controller: 'view_login1Ctrl'
		});
	}])
	.controller('view_login1Ctrl', function($scope, $http) {
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
				
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});
				
			}
	    }
	});
	
	


})();
	
	/*
	.controller('view_login1Ctrl', LoginCtrl);

	LoginCtrl.$inject = ['$http'];

	
	function LoginCtrl($http) {
		var vm = this;
		
		vm.btn_login = function() {
			alert("test12");
			
			if ((vm.username=="") || (vm.password==""))
				alert("please enter username and password!");
			else {
				var username = vm.username;
				var password = vm.password;
				
				alert("login now"+username+", "+password); // +username+", "+password
			}
		};
		
	};
	
	


})();  */