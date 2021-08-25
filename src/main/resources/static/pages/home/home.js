angular.module('library').controller('homeController', function ($scope, $http) {
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
            console.log(response.data);
        })
    }
});