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
	'fridge.formChange',
	'ngCookies'
]).
config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
$locationProvider.hashPrefix('!');
$httpProvider.interceptors.push('authInterceptor');
$routeProvider.otherwise({redirectTo: '/home'});
}])

.factory('authInterceptor', function($cookies) {
	return {
    request: function(config) {
        config.headers = config.headers || {};
        
        // config.headers.Authorization = $cookies.get('username');
        config.headers['token'] = $cookies['token'];
        return config;
      }
    };
  });

var URL_API = 'http://localhost:8080/fridge/api/';
var URL_SUPERMARKET = 'http://localhost:8080/supermarket/api/';
