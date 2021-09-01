angular.module('library').controller('homeController', function ($scope, $http, $routeParams, $localStorage, $location, API_SERVER) {
    const rootPath = "/lib/api/v1"
    const apiPath = rootPath + '/books'

    $scope.getNovelties = function () {
        $http({
            method: 'GET',
            url: apiPath,
            params: {
                page: 1,
                count: 5,
                sort: 'createdAt,desc'
            }
        }).then(function (response) {
            $scope.noveltiesPage = response.data;
            $scope.noveltiesList = $scope.noveltiesPage.content;
        })
    }

    $scope.getSale = function () {
        $http({
            method: 'GET',
            url: apiPath,
            params: {
                page: 1,
                count: 5,
                sort: 'discount,desc'
            }
        }).then(function (response) {
            $scope.salePage = response.data;
            $scope.saleList = $scope.salePage.content;
        })
    }

    $scope.getAdvices = function () {
        $http({
            method: 'GET',
            url: apiPath,
            params: {
                page: 1,
                count: 5,
                sort: 'editorsAdvice,desc'
            }
        }).then(function (response) {
            $scope.advancesPage = response.data;
            $scope.advancesList = $scope.advancesPage.content;
        })
    }

    $scope.addToCartJS = function (bookId) {
        $http({
            url: API_SERVER + '/cart/add',
            method: 'POST',
            params: {
                uuid: $localStorage.marketCartUuid,
                book_id: bookId
            }
        }).then(function (response) {
            $localStorage.needToUpdateCart.set(1);
        });
    };

});