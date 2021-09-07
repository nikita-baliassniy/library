angular.module('library').controller('checkoutController', function ($scope, $http, $location, $localStorage, API_SERVER) {

    $scope.isPaid = false;

    $scope.getCart = function () {
        $http.get(API_SERVER + '/cart/' + $localStorage.marketCartUuid)
            .then(function successCallBack(response) {
                $scope.Cart = response.data;
            });
    };

    $scope.setPaid = function() {
        $scope.isPaid = !$scope.isPaid;
    }

    $scope.createOrder = function() {
        $http({
            url: API_SERVER + '/orders/' + $localStorage.marketCartUuid,
            method: 'POST',
        }).then(function successCallBack(response) {
            toastr.success("Номер заказа: " + response.data.id, "Заказ успено создан!");
            $localStorage.needToUpdateCart.set(1);
            $location.path('/orders-history');
        }, function errorCallBack(response) {
            toastr.error(response.message, "Ошибка создания заказа");
        });
    }

    $scope.getCart();
});
