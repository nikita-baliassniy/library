angular.module('library').controller('bookDetailController', function ($scope, $http, $routeParams) {
    const apiPath = 'http://localhost:8189/lib/api/v1';
    $scope.newComment = {};
    $scope.newComment.score = 1;

    $scope.getBookDetailById = function (id) {
        console.log("bookId = " + id);
        $http({
            url: apiPath + '/books/' + id,
            method: 'GET'
        })
            .then(function (response) {
                $scope.bookDetail = response.data;
                console.log($scope.bookDetail);
            }, function errorCallback() {
                console.log("----ERROR---")
            }
        )
    }

    $scope.setSelectedRating = function(rating) {
        $scope.newComment.score = rating;
    }

    $scope.addNewComment = function() {
        $scope.newComment.createdAt = new Date();
        if (!$scope.newComment.score) {
            $scope.newComment.score = 1;
        }
        // if (!$scope.newComment.user) {
        //     $scope.newComment.user.id = 1;
        // }
        $http.put(apiPath + '/books/' + $scope.bookDetail.id + '/comment', $scope.newComment)
            .then(function (response) {
                $scope.newComment.score = 1;
                $scope.newComment.text = '';
                $scope.newComment.user.id = 1;  //todo переделать на нормального пользователя
                $scope.bookDetail = response.data;
            });
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
