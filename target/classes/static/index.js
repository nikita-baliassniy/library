angular.module('lib', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/lib/api/v1';

    $scope.fillCatalog = function () {
        $http({
            url: contextPath + '/books',
            method: 'GET'
        }).then(function (response) {
            $scope.BooksCatalog = response.data;
            console.log($scope.BooksCatalog);
        });
    };

    $scope.fillCatalog();
});