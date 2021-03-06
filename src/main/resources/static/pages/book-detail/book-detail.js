angular.module('library').controller('bookDetailController', function ($scope, $http, $routeParams, AuthService, API_SERVER, $localStorage) {
    $scope.newComment = {};
    $scope.newComment.score = 1;

    $scope.getBookDetailById = function (id) {
        // console.log("bookId = " + id);
        $http({
            url: API_SERVER + '/books/' + id,
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

    $scope.setSelectedRating = function (rating) {
        $scope.newComment.score = rating;
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

    $scope.isExist = function () {
        let bookID = parseInt($routeParams.bookId);
        $http.get(API_SERVER + '/orders/' + bookID + '/exist')
            .then(function (response) {
                $scope.exist = response.data;
            });
    }

    $scope.addNewComment = function () {
        $scope.newComment.createdAt = new Date();
        $scope.newComment.user.id = AuthService.getUserId();
        if (!$scope.newComment.score) {
            $scope.newComment.score = 1;
        }
        $http.put(API_SERVER + '/books/' + $scope.bookDetail.id + '/comment', $scope.newComment)
            .then(function (response) {
                $scope.newComment.score = 1;
                $scope.newComment.text = '';
                $scope.newComment.user.id = AuthService.getUserId();  //todo переделать на нормального пользователя
                $scope.bookDetail = response.data;
            });
    }

    $scope.checkBookId = function () {
        let bookID = parseInt($routeParams.bookId);
        if (!isNaN(bookID) && angular.isNumber(bookID)) {
            $scope.getBookDetailById(bookID);
        }
    }

    $scope.getScore = function () {
        return this.bookDetail.bookInfo.score;
    }
    $scope.checkBookId();
    // console.log($routeParams);
});
