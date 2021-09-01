angular.module('library').controller('shopListController', function ($scope, $http, $routeParams, $localStorage, $location, API_SERVER) {

    let pageSelector = document.querySelector('.index-selector select');
    let sortSelector = document.querySelector('.sort select');
    let minPrice = document.getElementById('min-price');
    let maxPrice = document.getElementById('max-price');

    pageSelector.options.selectedIndex = 0;
    $scope.size = pageSelector.options[pageSelector.options.selectedIndex.valueOf()].value;

    sortSelector.options.selectedIndex = 0;
    $scope.sort = sortSelector.options[sortSelector.options.selectedIndex.valueOf()].value;

    if ($scope.genre == null) $scope.genre = [];

    pageSelector.addEventListener('change', function () {
        let selectedIndex = pageSelector.options.selectedIndex.valueOf();
        $scope.size = pageSelector.options[selectedIndex].value;
        $scope.fillTable();
    });

    sortSelector.addEventListener('change', function () {
        let sortedIndex = sortSelector.options.selectedIndex.valueOf();
        $scope.sort = sortSelector.options[sortedIndex].value;
        $scope.fillTable();
    });

    maxPrice.addEventListener('change', function () {
        $scope.fillTable();
    })

    minPrice.addEventListener('change', function () {
        $scope.fillTable();
    })

    $scope.fillTableByGenre = function (genre) {
        if ($scope.genre.indexOf(genre) === -1) {
            $scope.genre.push(genre);
        } else {
            $scope.genre.splice($scope.genre.indexOf(genre), 1)
        }
        $scope.fillTable();
    }

    $scope.cleanGenreFilter = function () {
        let items = document.querySelectorAll(".active");
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove("active");
        }
        $scope.genre = [];
        $scope.fillTable();
    }

    $scope.checkGenreId = function () {
        if ($localStorage.redirectedParam != null) {
            let element = document.getElementById('genre_' + $localStorage.redirectedParam);
            delete $localStorage.redirectedParam;
            angular.element(element).triggerHandler('click');
            return true
        }
        return false;
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

    $scope.fillTable = function (pageIndex = 1) {
        if (!$scope.checkGenreId()) {
            $http({
                url: API_SERVER + '/books',
                method: 'GET',
                params: {
                    min_price: $scope.filter ? $scope.filter.min : null,
                    max_price: $scope.filter ? $scope.filter.max : null,
                    genre: $scope.genre ? $scope.genre : null,
                    sort: $scope.sort ? $scope.sort : null,
                    count: $scope.size ? $scope.size : 9,
                    page: pageIndex
                },
            }).then(function (response) {
                $scope.ProductsPage = response.data;
                $scope.ProductsList = $scope.ProductsPage.content;
                let minPageIndex = pageIndex - 2;
                let maxPageIndex = pageIndex + 2;
                if (minPageIndex < 1) minPageIndex = 1;
                if (maxPageIndex > $scope.ProductsPage.totalPages) maxPageIndex = $scope.ProductsPage.totalPages;
                $scope.pageCount = $scope.createPagesArray(minPageIndex, maxPageIndex);
            });
        }
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

    angular.element(document).ready(function () {
        $scope.fillTable();
    })
});