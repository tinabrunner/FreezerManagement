/**
 * @author Christina Brunner
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
	
	.controller('myaccountCtrl', MyAccountCtrl);
	
	MyAccountCtrl.$inject = ['$scope', '$http', '$cookies', '$route', '$window'];
	
	function MyAccountCtrl($scope, $http, $cookies, $route, $window) {
		
		var vm = this;
		// Bind Functions to Buttons
		vm.editAccount = editAccount;
		vm.deleteAccount = deleteAccount;
		vm.cancel = cancel;
		vm.user = {};
		var userdata;

		init ();
		
		// Show field "password_confirmation" if password gets edited
		$scope.passwordChanged = function() {
			$scope.checked_PwConfirm = false;
		};
		
		function init() {
			$scope.textEditButton = "Edit Account";
			$scope.checked = true;
			$scope.checked_PwConfirm = true;
			var token = $cookies.get('token');
			$http.get(URL_API+'account'+'/'+token).then(function(response) {
				if (!response.data) {
						alert("Fehler: "+response.data);
					}
					else {
						userdata = response.data[0];
						vm.user = response.data[0];
					}
			}, function(error) {
				console.dir(error);
			});
		};
		
		function editAccount() {
			if ($scope.textEditButton == "Edit Account") {
				$scope.checked = false;
				$scope.textEditButton = "Save Changes";
			}
			else if ($scope.textEditButton == "Save Changes") {
				var newUserData = {
						firstName : vm.user.firstName,
						lastName : vm.user.lastName,
						username : vm.user.username,
						password : vm.user.password,
						email : vm.user.email,
						role : "user"
				};
				
				if (!$scope.checked_PwConfirm) {
					if (!(vm.user.password === vm.password_confirm)  || !vm.user.username || !vm.user.password || 
							!vm.user.firstName || !vm.user.lastName || !vm.user.email) {
						if (!(vm.user.password === vm.password_confirm))
							alert("Confirmation of the password is wrong!");
						else
							alert("Please fill all fields!");
					}
					else
						safeDataAndReset(newUserData);
				}
				else {
					safeDataAndReset(newUserData);
				}
			}
	    };
		
		function deleteAccount(username) {
			// Show confirmation-alert
			if ($window.confirm("Are you sure?")) {
                // Delete token
				$cookies.remove("token");
                // Delete User from DB
				$http.delete(URL_API+'account'+'/'+username).then(function(response){
			    	   if (!response.data) {
			    		   $route.reload();
						}
						else {
							alert("Fehler: "+response.data);
						}
			       }, 
			       function(response){
			         alert("Something went wrong - "+response.data);
			       }
			    );
            } 
			
			
		};
		
		function cancel() {
			$scope.checked_PwConfirm = true;
			$scope.checked = true;
			vm.user = userdata;
			$scope.textEditButton = "Edit Account";
			$route.reload()
		};

		var safeDataAndReset = function(newUserData) {
			$http.put(URL_API+'account', newUserData).then((function(response){
				if (response.data) {
					alert("Fehler: "+response.data);
				}
				else {
				// evtl einfach reload?
					$scope.checked_PwConfirm = true;
					$scope.checked = true;
					$scope.textEditButton = "Edit Account";
				}
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});
		};
	}
	
})();