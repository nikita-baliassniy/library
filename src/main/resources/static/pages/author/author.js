angular.module('library').controller('authorController', function ($scope, $http, $routeParams, AuthService, API_SERVER) {

    $scope.title = 'Книги автора';

    $scope.getAuthorDetailById = function (id) {
        $http({
            url: API_SERVER + '/authors/' + id,
            method: 'GET'
        })
            .then(function (response) {
                    $scope.authorDetail = response.data;
                    $scope.bookList = response.data.books;
                    console.log($scope.bookDetail);
                }, function errorCallback() {
                    console.log("----ERROR---")
                }
            )
    }

    $scope.checkAuthorId = function() {
        let authorID = parseInt($routeParams.authorId);
        if (!isNaN(authorID) && angular.isNumber(authorID)) {
            $scope.getAuthorDetailById(authorID);
        }
    }

    $scope.checkAuthorId();
    // console.log($routeParams);
});
