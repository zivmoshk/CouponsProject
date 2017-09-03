

var app = angular.module('couponSystem', []);
	
	app.controller('companyCtrl', function($scope, $http, $window, $timeout) {
		
	
$http.get("rest/company/getComp_name").success(function(response) { $scope.comp_name = response;});
});