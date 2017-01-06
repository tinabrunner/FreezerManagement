/**
 * 
 */

(function() {
	'use strict';

	angular.module('fridge.myaccount', ['ngRoute'])

	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/myaccount', {
			templateUrl: 'myaccount/myaccount.html',
			controller: 'myaccountCtrl'
		});
	}])
	.controller('myaccountCtrl', function($scope, $http) {
		$scope.MyText = "Edit Account";
		$scope.checked =true;
		
		$scope.EditAccount = function() {
			if ($scope.MyText == "Edit Account") {
				$scope.checked =false;
				$scope.MyText = "Save Changes";
			}
			else if ($scope.MyText == "Save Changes") {
				$scope.checked =true;
				$scope.MyText = "Edit Account";
			}
			else {
				alert ("Value = "+$scope.MyText);
			}
			
			/*else {
				var dada = {
						name : $scope.firstname, 
						surname : $scope.lastname,
						username : $scope.username,
						password : $scope.password,
						email : $scope.email,
						userrole : $('input:checkbox[name="reg_usertype"]:checked')
				}
				$http.post(URL_API+"account", dada).then((function(response){
					if (response.data)
						redirectTo: '/home';
				}), 
				alert("Something went wrong"));
				
				
			}*/
	    }
	});
	
})();