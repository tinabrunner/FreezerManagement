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
					setCookie(response.data);
					
				}), 
				function(response) { 
					alert("Something went wrong: "+response.data);
				});
				
			}
	    }
		
		
		// Funktions für Cookie Management
		function setCookie(cname) {
		    //var d = new Date();
		    // d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
		    //var expires = "expires="+d.toUTCString();
		    document.cookie = cname + ";path=/";  // + "=" + cvalue + ";" + expires 
		}

		function getCookie(cname) {
		    var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i = 0; i < ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0) == ' ') {
		            c = c.substring(1);
		        }
		        if (c.indexOf(name) == 0) {
		            return c.substring(name.length, c.length);
		        }
		    }
		    return "";
		}

		function checkCookie() {
		    var user = getCookie("username");
		    if (user != "") {
		        alert("Welcome again " + user);
		    } else {
		        user = prompt("Please enter your name:", "");
		        if (user != "" && user != null) {
		            setCookie("username", user, 365);
		        }
		    }
		}
	});
	
	


})();
	
