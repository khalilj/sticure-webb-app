var sticureApp = angular.module('sticureApp', ['ngMap']);

sticureApp.controller('securityEventConteroller' , function ($scope, $http, $interval, NgMap) {
    $scope.ordersSummary = {};
    $scope.test="test text";
        
    
    NgMap.getMap().then(function(evtMap) {
        $scope.map = evtMap;
        
      });
    $scope.markerData=[
      {"id":1, "latitude":32.696002,"longitude":35.301039,"title":"BigFashion Nazareth on fire!!!"},
      {"id":2,"latitude":32.707126,"longitude":35.301647,"title":"Dinner in St. Gabriel"},
      {"id":3,"latitude":32.693525,"longitude":35.303422,"title":"Crime in el mashhadawe, EL KOL BYISHHAD!!!"}];
    
    $scope.getSecurityEvent = function () {
    	$http.get('/getSecurityEvents').
        then(function(response) {             	
            //$scope.markerData = response.data;
        }, function(error) {
            alert("Error loading getSecurityEvent: " + error.statusText);
        });
    };
    
    
    $scope.toggleBounce = function (e, prospect) {    	
        for (var key in $scope.map.markers) {        	
            var mid = parseInt(key);
            var m = $scope.map.markers[key];
            if (mid == this.id) {
                m.setAnimation(null);               
                $scope.map.showInfoWindow('myInfoWindow',this);  
                
            }
        }

    };
       
});