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
		$scope.EditAccount = function() {
			if (this.getElementById("btn_edit ").value=="Edit Account")
				this.getElementById("btn_edit ").value=="Save Changes"
			else if (this.getElementById("btn_edit ").value=="Safe Changes")
				this.getElementById("btn_edit ").value=="Edit Account";
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