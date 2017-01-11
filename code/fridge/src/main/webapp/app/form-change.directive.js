/**
 * User: phi
 * Date: 11.01.17
 .___.
 {o,o}
 /)___)
 --"-"--
 */
(function() {
	'use strict';

	angular.module('fridge.formChange', [])
		.directive("formChange", function($parse) {
			return {
				require: "form",
				link: function(scope, element, attrs){
					var cb = $parse(attrs.formChange);
					element.on("change", function(){
						cb(scope);
					});
				}
			}
		});
})();
