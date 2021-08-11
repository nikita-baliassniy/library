angular.module('library').controller('bookDetailController', function ($scope, $http, $routeParams) {
    const apiPath = 'http://localhost:8189/lib/api/v1';

    // $scope.bookDetail = NaN;

    $scope.getBookDetailById = function (id) {
        console.log("bookId = " + id);
        $http({
            url: apiPath + '/books/' + id,
            method: 'GET'
        })
            .then(function (response) {
                $scope.bookDetail = response.data;
                // vm.score = $scope.bookDetail.bookInfo.score;
                console.log($scope.bookDetail);
            }, function errorCallback() {
                console.log("----ERROR---")
            }
        )
    }

    $scope.checkBookId = function() {
        let bookID = parseInt($routeParams.bookId);
        if (!isNaN(bookID) && angular.isNumber(bookID)) {
            $scope.getBookDetailById(bookID);
        }
    }

    $scope.getScore = function() {
        return this.bookDetail.bookInfo.score;
    }
    $scope.checkBookId();
    // console.log($routeParams);
});