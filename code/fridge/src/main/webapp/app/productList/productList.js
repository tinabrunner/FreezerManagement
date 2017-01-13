/* 10.01.2017 JD */
(function() {
	'use strict';

	angular.module('fridge.productList', ['ngRoute'])

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/productlist', {
				templateUrl: 'productList/productList.html',
				controller: 'ProductListCtrl'
			});
		}])
		.controller('ProductListCtrl', ProductListCtrl);

	ProductListCtrl.$inject = ['$http'];

	function ProductListCtrl($http) {
		var vm = this;
		
		
		vm.product = {};
		vm.products = {};
		vm.init = getAllProductsFromSupermarket;	
		
		var namingProductId = "newProductId";
		var namingProductName = "newProductName";
		var namingProductSize = "newProductSize";
		var namingProductPrice = "newProductPrice";
		var namingProductCalories = "newProductCalories";
		var namingButtonSave = "btnProductSave";
		
		vm.isEditing = false;
		vm.idError = false;
		vm.errorTextId = ""
		vm.isValidationError = false;
		vm.errorText = "";
		vm.isUserAdmin = false;
		
		// Load products from Supermarket when "entering"
		getAllProductsFromSupermarket();
		
		function getAllProductsFromSupermarket(){
			$http.get(URL_SUPERMARKET+'productlist').then(function(resp) {				
				vm.products = resp.data;
			}, function(error) {
				console.dir(error);
			});
		}
		
		vm.addToShoppingList = function(product) {			
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
		
		function getValueFromField(aName){
			return document.getElementById(aName).value;
		}
		
		function setValueToField(aName, aValue){
			document.getElementById(aName).value = aValue;
		}
		
		function clearInputFields(){
			document.getElementById(namingProductId).value = "";
			document.getElementById(namingProductName).value = "";
			document.getElementById(namingProductSize).value = "1";
			document.getElementById(namingProductPrice).value = "0";
			document.getElementById(namingProductCalories).value = "0";
		}
				
		
		vm.getErrorText = function(){
			return vm.errorText;
		}
		
		vm.abortEditing = function(){
			clearInputFields();
			vm.isEditing = false;
			vm.idError = false;
			vm.isValidationError = false;
		}
		
		vm.isIdError = function(){
			return vm.idError;
		}
			
		function validateInput(){
			return (
				(getValueFromField(namingProductId).trim().length > 0) &&
				(getValueFromField(namingProductName).trim().length > 0) &&
				(getValueFromField(namingProductSize).trim().length > 0) &&
				(getValueFromField(namingProductPrice).trim().length > 0) &&
				(getValueFromField(namingProductCalories).trim().length > 0));						
		}
		
		vm.editProduct = function(product){
			vm.isEditing = true;
			setValueToField(namingProductId, product.id);
			setValueToField(namingProductName, product.name);
			setValueToField(namingProductSize, product.verpackungsGroesse);
			setValueToField(namingProductPrice, product.preis);
			setValueToField(namingProductCalories, product.calories);
		}
		
		
		/*
		 * 	HTTP-COMMUNICATION
		 */
		
		vm.addProductToSupermarket = function(){
								
			if (!validateInput()){
				vm.isValidationError = true;
				vm.errorText = "Please enter valid product information.";
				return false;
			} 
			
			vm.isValidationError = false;
			
			var product = {};
			product.id = getValueFromField(namingProductId);
			product.name = getValueFromField(namingProductName);
			product.verpackungsGroesse = getValueFromField(namingProductSize);
			product.preis = getValueFromField(namingProductPrice);
			product.preis = product.preis.replace(",", ".");
			product.calories = getValueFromField(namingProductCalories);
			
			$http({
				url: URL_SUPERMARKET + 'productlist/',
				data: product,
				method: "PUT",
				headers: {
					'Content-type': 'application/json',
					'Accept': 'application/json'
				}
			}).then(function(resp){				
				getAllProductsFromSupermarket();
				vm.isEditing = false;
			});
					
			clearInputFields();			
		}
			
		vm.deleteFromStore = function(prodId){
			$http.delete(URL_SUPERMARKET+'productlist/' + prodId).then(function(resp){
				if (resp.data == true){
					getAllProductsFromSupermarket();					
				} 
			}, function(error){
				console.dir(error);
			})
		}
		

		vm.checkId = function(){
			var productId = getValueFromField(namingProductId);
			productId = productId.trim();		
			if (productId.trim() != ''){
				$http.get(URL_SUPERMARKET + 'productlist/checkId/' + productId).then(function(resp){
					vm.idError = resp.data;
					if (vm.idError){
						vm.errorText = "Id already exists. Please enter a new one.";
					}
				});
			} else {
				vm.idError = false;				
			}			
		}
					
		vm.switchUserRole = function(){
			vm.isUserAdmin = !vm.isUserAdmin;
		}
						
	}

})();