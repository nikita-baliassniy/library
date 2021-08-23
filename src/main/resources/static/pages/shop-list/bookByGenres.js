angular.module('library').controller('bookByGenreController', function ($scope, $http, $routeParams, API_SERVER) {

    $scope.getBooksByGenreId = function(genreId, pageIndex = 1) {
        $http.get(API_SERVER + '/books/genre/' + genreId).then(
            function successCallBack(response) {
                $scope.ProductsPage = response.data;
                $scope.ProductsList = $scope.ProductsPage.content;
                console.log($scope.ProductsPage);
                console.log($scope.ProductsList);
                if ($scope.ProductsPage.empty && pageIndex !== 1) {
                    $scope.fillTable(pageIndex--);
                }
                let minPageIndex = pageIndex - 2;
                let maxPageIndex = pageIndex + 2;
                if (minPageIndex < 1) minPageIndex = 1;
                if (maxPageIndex > $scope.ProductsPage.totalPages) maxPageIndex = $scope.ProductsPage.totalPages;
                $scope.pageCount = $scope.createPagesArray(minPageIndex, maxPageIndex);
            }
        )
    }

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
    };

    $scope.changePagination = function () {
        let selectElement = document.getElementById("pagination").options.selectedIndex;
        let val = document.getElementById("pagination").options[selectElement].value;
        if ($scope.value !== val) {
            $scope.value = val;
            $scope.pageIndex = 1;
            $scope.checkGenreId();
        }
    };

    $scope.checkGenreId = function () {
        let genreID = parseInt($routeParams.genreId);
        if (!isNaN(genreID) && angular.isNumber(genreID)) {
            $scope.getBooksByGenreId(genreID);
        }
    }

    $scope.checkGenreId();

})
