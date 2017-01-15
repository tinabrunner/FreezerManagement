'use strict';

// Declare app level module which depends on views, and components
angular.module('fridge', [
	'ngRoute',
	'fridge.home',
	'fridge.navigation',
	'fridge.productList',
	'fridge.view_register',
	'fridge.view_login',
	'fridge.shopping_cart', 
	'fridge.inventory',
	'fridge.invoices',
	'fridge.myaccount',
	'fridge.shoppingList',
	'fridge.settings',
	'fridge.formChange'
]).
config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
$locationProvider.hashPrefix('!');

$routeProvider.otherwise({redirectTo: '/home'});
$httpProvider.interceptors.push('authInterceptor');
}])


.factory('authInterceptor', function($cookies, $location, $window) {
	return {
    request: function(config) {
        // config.headers = config.headers || {};
        var token = $cookies.get('token');
        if (!token && !angular.equals($location.path(), "/view_register"))
        	$location.path("/view_login");
         //config.headers['token'] = $cookies['token'];
        //$httpProvider.defaults.headers.common["token"] = token;
        return config;
      }
    };
  }
);


var URL_SUPERMARKET = 'http://localhost:38611/';
var URL_API = 'http://localhost:8080/fridge/api/';
var URL_SUPERMARKET_API = URL_SUPERMARKET + 'supermarket/api/';
