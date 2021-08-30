angular.module('library').controller('findResultsController', function ($scope, $http, $routeParams, API_SERVER) {
    $scope.findRequest = $routeParams.findRequest;

    let pageSelector = document.querySelector('.index-selector select');
    $scope.size = pageSelector.options[pageSelector.options.selectedIndex.valueOf()].value;

    pageSelector.addEventListener('change', function () {
        let selectedIndex = pageSelector.options.selectedIndex.valueOf();
        $scope.size = pageSelector.options[selectedIndex].value;
        $scope.findBooks();
    });

    pageSelector.options.selectedIndex = 0;
    $scope.size = pageSelector.options[pageSelector.options.selectedIndex.valueOf()].value;

    $scope.findBooks = function (pageIndex = 1) {
        $http({
            url: API_SERVER + '/books',
            method: 'GET',
            params: {
                sort: $scope.sort ? $scope.sort : null,
                title: $scope.findRequest,
                count: $scope.size ? $scope.size : 9,
                page: pageIndex
            }
        }).then(function (response) {
            $scope.BookPage = response.data;
            $scope.BookList = response.data.content;
            let minPageIndex = pageIndex - 2;
            let maxPageIndex = pageIndex + 2;
            if (minPageIndex < 1) minPageIndex = 1;
            if (maxPageIndex > $scope.BookPage.totalPages) maxPageIndex = $scope.BookPage.totalPages;
            $scope.pageCount = $scope.createPagesArray(minPageIndex, maxPageIndex);
        })
    }

    $scope.findAuthors = function () {
        $http({
            url: API_SERVER + '/authors',
            method: 'GET',
            params: {
                name: $scope.findRequest,
                sort: "name,asc"
            }
        }).then(function (response) {
            $scope.AuthorList = response.data;
        })
    }

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
    };
});