angular.module('library').controller('cartController', function ($scope, $http, $routeParams, $location, $localStorage, API_SERVER) {

    $scope.showCart = function () {
        $http.get(API_SERVER + '/cart/' + $localStorage.marketCartUuid)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    };

    $scope.clearCart = function () {
        $http({
            url: API_SERVER + '/cart/clear',
            method: 'POST',
            params: {
                uuid: $localStorage.marketCartUuid
            }
        }).then(function (response) {
            $scope.showCart();
            console.log("Clear OK");
        });
    }

    $scope.createOrder = function () {
        $http.get(API_SERVER + '/orders/create')
            .then(function (response) {

                $scope.showCart();
            });
    }

    $scope.goToOrderSubmit = function () {
        $location.path('/checkout');
    }

    $scope.deleteBookInCartById = function (bookId) {
        $http({
            url: API_SERVER + '/cart',
            method: 'DELETE',
            params: {
                uuid: $localStorage.marketCartUuid,
                book_id: bookId
            }
        }).then(function (response) {
            $localStorage.needToUpdateCart.set(1);
            $scope.showCart();
        });
    }

    $scope.decrementQuantity = function (Cart) {
        if (Cart.quantity == 1) {
            $scope.deleteBookInCartById(Cart.id);
        } else {
            $http.get(API_SERVER + '/cart/dec/' + Cart.id)
                .then(function (response) {
                    $scope.showCart();
                });
        }
    };

    $scope.incrementQuantity = function (bookId) {
        $http({
            url: API_SERVER + '/cart/inc',
            method: 'POST',
            params: {
                uuid: $localStorage.marketCartUuid,
                book_id: bookId
            }
        }).then(function (response) {
            $scope.showCart();
            $localStorage.needToUpdateCart.set(1);
        });
    };

    $scope.showCart();
});
