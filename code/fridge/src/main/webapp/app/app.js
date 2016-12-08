'use strict';

// Declare app level module which depends on views, and components
angular.module('fridge', [
	'ngRoute',
	'fridge.view1',
	'fridge.view2',
	'fridge.navigation',
	'fridge.view_login',
	'fridge.shopping_cart'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
$locationProvider.hashPrefix('!');

$routeProvider.otherwise({redirectTo: '/view1'});
}]);

var URL_API = 123;
