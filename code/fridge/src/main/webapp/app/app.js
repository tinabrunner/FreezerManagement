'use strict';

// Declare app level module which depends on views, and components
angular.module('fridge', [
	'ngRoute',
	'fridge.home',
	'fridge.navigation',
	'fridge.view_register',
	'fridge.view_login',
	'fridge.shopping_cart', 
	'fridge.inventory',
	'fridge.invoices'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
$locationProvider.hashPrefix('!');

$routeProvider.otherwise({redirectTo: '/home'});
}]);

var URL_API = 'http://localhost:8080/fridge/api/';
