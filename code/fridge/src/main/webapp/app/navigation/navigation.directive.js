/**
 * 
 */

angular.module('fridge.navigation', [])

.directive ("navBar", function() {
	var ret = {
		restrict: "E",
		templateUrl: "navigation/navigationBar.html",
		controller: NavigationController,
		controllerAs: 'navigation',
		bindToController: true
	};
	return ret;
});

NavigationController.$inject = ['$scope', '$location', '$cookies', '$http']; //  nicht unbedingt notwendig
function NavigationController($scope, $location, $cookies, $http) {
	var vm = this;

	vm.getClass = function (path) {
		var subpath = $location.path().substr(0, path.length);
		return (subpath === path) ? 'active' : '';
	};
	
	$scope.logout = function () {
		var token = $cookies.get("token");
		// Check if user is logged in
		if(token) {
			// Delete token & username from UserSessionStore
			$http.delete(URL_API+'login'+'/'+token).then(function(response){
		    	   if (!response.data) {
		    		   alert("Successfully logged out!");
		    		   $location.path("/login");
					}
					else {
						alert("Fehler: "+response.data);
					}
		       }, 
		       function(response){
		         alert("Something went wrong - "+response.data);
		       }
		    );
			// Delete token from cookies
			$cookies.remove("token");
		}
	};
	
}
