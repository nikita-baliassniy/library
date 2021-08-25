angular.module('library').controller('shopListController', function ($scope, $http, API_SERVER) {

    let pageSelector = document.querySelector('.index-selector select');
    pageSelector.options.selectedIndex = 0;
    $scope.size = pageSelector.options[pageSelector.options.selectedIndex.valueOf()].value;
    if ($scope.genre == null) $scope.genre = [];

    pageSelector.addEventListener('change', function () {
        let selectedIndex = pageSelector.options.selectedIndex.valueOf();
        $scope.size = pageSelector.options[selectedIndex].value;
        $scope.findAllProducts();
    });

    $scope.fillTable = function (pageIndex) {
        $scope.findAllProducts(pageIndex);
    };

    $scope.findAllProducts = function (pageIndex = 1) {
        $http({
            url: API_SERVER + '/books',
            method: 'GET',
            params: {
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                title: $scope.filter ? $scope.filter.title : null,
                count: $scope.size ? $scope.size : 9,
                page: pageIndex
            },
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            $scope.ProductsList = $scope.ProductsPage.content;
            if ($scope.ProductsPage.empty && pageIndex !== 1) {
                $scope.fillTable(pageIndex--);
            }
            let minPageIndex = pageIndex - 2;
            let maxPageIndex = pageIndex + 2;
            if (minPageIndex < 1) minPageIndex = 1;
            if (maxPageIndex > $scope.ProductsPage.totalPages) maxPageIndex = $scope.ProductsPage.totalPages;
            $scope.pageCount = $scope.createPagesArray(minPageIndex, maxPageIndex);
        });
    };

    $scope.findProductById = function () {
        $http.get(API_SERVER + '/books/' + $scope.filter.id)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                $scope.PageArray = [1];
            });
    };

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
    };

    $scope.cleanFilter = function () {
        $scope.filter ? $scope.filter.title = null : null;
        $scope.filter ? $scope.filter.min = null : null;
        $scope.filter ? $scope.filter.max = null : null;
        $scope.fillTable();
    };

    $scope.fillTable();
});
