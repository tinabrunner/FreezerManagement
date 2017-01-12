/* 10.01.2017 JD */
(function() {
	'use strict';

	angular.module('fridge.shoppingList', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/shoppinglist', {
				templateUrl: 'ShoppingList/shoppingList.html',
				controller: 'ShoppingListCtrl',
				require: 'ngModel'
			});
		}])		
		.controller('ShoppingListCtrl', ShoppingListCtrl);

	ShoppingListCtrl.$inject = ['$http'];

	function ShoppingListCtrl($http) {
		var vm = this;
		
		/*
		 * Storage of all products retrieved from HTTP-Service
		 */
		vm.products = {};
		
		vm.init = getAllProductsFromShoppingList;	
		
		/*
		 * Load Products from ShoppingList over HTTP
		 */
		getAllProductsFromShoppingList();
		
		/**
		 * HTTP/GET to /shoppinglist
		 * @param none
		 * @return all Products from ShoppingList as JSON 
		 */
		function getAllProductsFromShoppingList(){
			$http.get(URL_API+'shoppinglist').then(function(resp) {
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		/**
		 * HTTP/DELETE to /shoppinglist
		 * @param prodId; represents the Id of Product to delete in Database
		 * @return boolean, TRUE if it was successfull
		 */
		function deleteProduct(prodId) {
								
			$http.delete(URL_API+'shoppinglist/' + prodId).then(function(resp){				
				if (resp.data == true){
					getAllProductsFromShoppingList();					
				} 
			}, function(error){
				console.dir(error);
			})
		}
		
		/**
		 * Internal call to deleteProduct(prodId);
		 */
		vm.deleteProduct = function(product) {
			deleteProduct(product);
		}
		
		function addOrUpdateShoppingList(product) {
			$http({
                url: URL_API + 'shoppinglist/',
                data: product,
                method: "PUT",
                headers: {
                    'Content-type': 'application/json',
                    'Accept': 'application/json'
                }
            })
		}
		
		/**
		 * 
		 */
		vm.setRegulary = function(product, isRegelmaessig) {
			product.regelmaessig = !isRegelmaessig;
			addOrUpdateShoppingList(product);
		}
		
		function checkBestand(min, max) {
			return min < max;
		}
		
		function getBestandById(id){
			return document.getElementById(id).value;
		}
		
		function setBestandById(id, value){
			document.getElementById(id).value = value;
		}
		
		vm.updateMindestBestand = function(product){
			var value = getBestandById('edt_bestandMin_' + product.id);
			product.mindestBestand = value;
			addOrUpdateShoppingList(product);
		}
		
		vm.updateHoechstBestand = function(product){			
			var value = getBestandById('edt_bestandMax_' + product.id);
			var value_min = getBestandById('edt_bestandMin_' + product.id);
			product.hoechstBestand = value;			
			document.getElementById('edt_bestandMin_' + product.id).max = value;
			if (!checkBestand(value_min, value)) {
				setBestandById('edt_bestandMin_' + product.id, value);
				product.mindestBestand = value;				
			};
			
			addOrUpdateShoppingList(product);			
		}
	}

})();