/**
 * 
 */

angular.module ('myApp.navigation')

.directive ("navBar", function() {
	var ret = {
		restrict: "E",
		templateUrl: "navigationBar.html"
	};
	return ret;
});