var sticureApp = angular.module('sticureApp', [ 'ngMap' ]);

sticureApp.controller('securityEventConteroller', function($scope, $http,
		$interval, NgMap) {
	$scope.ordersSummary = {};
	$scope.test = "test text";
	$scope.checkedEvents = [];
	$interval(function() {
		$scope.getSecurityEvent();
	}, 5000);

	NgMap.getMap().then(function(evtMap) {
		$scope.map = evtMap;
	});

	$scope.getSecurityEvent = function(e, prospect) {
		$http.get('/getSecurityEvents').then(function(response) {
			$scope.markerData = response.data.events;
			console.log($scope.markerData);

			for (var index = 0; index < $scope.markerData.length; index++){				
				if ($scope.checkedEvents.indexOf($scope.markerData[index].id) < 0) {				
					console.log($scope.markerData[index].id+' new point');
				} else {	
					var k = index+1;
					console.log('change animation for key='+k);					
					//$scope.map.markers[k].setAnimation(null);
				}
			}

		}, function(error) {
			alert("Error loading getSecurityEvent: " + error.statusText);
		});
	};
	
	$scope.dummyReportEvent = function(eventType){
		$http.post('/dummyReportEvent/' + eventType).
        then(function(response) {
        }, function(error) {
        	alert("Error calling dummyReportEvent: " + error.statusText);
        });
	};
	
	$scope.sendFire = function(){
		$scope.dummyReportEvent("F");
	};
	
	$scope.sendGun = function(){
		$scope.dummyReportEvent("G");
	};

	$scope.toggleBounce = function(e, prospect) {
//		$http.post('/setEventChecked/' + eventId).
//        then(function(response) {
//        }, function(error) {
//        	alert("Error calling setEventChecked: " + error.statusText);
//        });
		
		for ( var key in $scope.map.markers) {
			var mid = parseInt(key);
			var m = $scope.map.markers[key];
			if (mid == this.id) {
				m.setAnimation(null);
				$scope.map.showInfoWindow('myInfoWindow' + m.id, this);
				$scope.checkedEvents.push(this.id);
				console.log('key='+key+' | marker='+$scope.map.markers[key]);

			} else {
				$scope.map.hideInfoWindow('myInfoWindow' + m.id, this);
			}
		}
	};
	
	$scope.getAnimation = function(isChecked){
		if (! isChecked){
			return "BOUNCE";
		} else{
			return "";
		}
	};
});