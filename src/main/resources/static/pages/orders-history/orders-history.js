angular.module('library').controller('ordersHistoryController', function ($scope, $http, API_SERVER) {

    $scope.getOrders = function () {
        $http.get(API_SERVER + '/orders')
            .then(function (response) {
                $scope.orders = response.data
            });
    };

    $scope.getOrders();

});
