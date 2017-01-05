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

NavigationController.$inject = ['$scope', '$location']; //  nicht unbedingt notwendig
function NavigationController($scope, $location) {
	var vm = this;

	vm.getClass = function (path) {
		var subpath = $location.path().substr(0, path.length);
		return (subpath === path) ? 'active' : '';
	}
}