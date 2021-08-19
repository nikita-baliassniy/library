angular.module('library').controller('similarController', function ($scope, $http, AuthService, API_SERVER) {

    $scope.title = 'Похожие книги';

    $scope.$watch('$parent.bookDetail', function(newValue, oldValue) {
        if (newValue !== undefined) {
            $scope.getSimilarBook(newValue.id)
        }
    });

    $scope.getSimilarBook = function (bookId) {
        $http.get(API_SERVER + '/books/similar/' + bookId).then(function successCallBack(response) {
            $scope.bookList = response.data;
        })
    };

});
