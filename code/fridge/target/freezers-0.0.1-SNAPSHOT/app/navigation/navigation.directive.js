/**
 * 
 */

angular.module('myApp.navigation', [])

.directive ("navBar", function() {
	var ret = {
		restrict: "E",
		templateUrl: "navigation/navigationBar.html",
	};
	return ret;
});