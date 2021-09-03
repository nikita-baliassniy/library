angular.module('library').controller('recommendationController', function ($scope, $http, $routeParams, $localStorage, $location, API_SERVER) {

    let pageSelector = document.querySelector('.index-selector select');
    let sortSelector = document.querySelector('.sort select');
    let minPrice = document.getElementById('min-price');
    let maxPrice = document.getElementById('max-price');
    $scope.compilationName = $routeParams.compilationName;

    pageSelector.options.selectedIndex = 0;
    $scope.size = pageSelector.options[pageSelector.options.selectedIndex.valueOf()].value;

    sortSelector.options.selectedIndex = 0;
    $scope.sort = sortSelector.options[sortSelector.options.selectedIndex.valueOf()].value;

    if ($scope.genre == null) $scope.genre = [];

    pageSelector.addEventListener('change', function () {
        console.log("pageSelector")
        let selectedIndex = pageSelector.options.selectedIndex.valueOf();
        $scope.size = pageSelector.options[selectedIndex].value;
        $scope.fillTable();
    });

    sortSelector.addEventListener('change', function () {
        console.log("sortSelector")
        let sortedIndex = sortSelector.options.selectedIndex.valueOf();
        $scope.sort = sortSelector.options[sortedIndex].value;
        $scope.fillTable();
    });

    maxPrice.addEventListener('change', function () {
        console.log("maxPrice")
        $scope.fillTable();
    })

    minPrice.addEventListener('change', function () {
        console.log("minPrice")
        $scope.fillTable();
    })

    $scope.fillTableByGenre = function (genre) {
        if ($scope.genre.indexOf(genre) === -1) {
            $scope.genre.push(genre);
        } else {
            $scope.genre.splice($scope.genre.indexOf(genre), 1)
        }
        console.log("fillTableByGenre")
        $scope.fillTable();
    }

    $scope.cleanGenreFilter = function () {
        let items = document.querySelectorAll(".active");
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove("active");
        }
        $scope.genre = [];
        console.log("cleanGenreFilter")
        $scope.fillTable();
    }

    $scope.fillTable = function (pageIndex = 1) {
            $http({
                url: API_SERVER + '/books/recommend/',
                method: 'GET',
                params: {
                    min_price: $scope.filter ? $scope.filter.min : null,
                    max_price: $scope.filter ? $scope.filter.max : null,
                    userId : $scope.userId,
                    sort: $scope.sort ? $scope.sort : null,
                    genre: $routeParams.genreId,
                    title: $scope.filter ? $scope.filter.title : null,
                    count: $scope.size ? $scope.size : 3,
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
                console.log("fillTable")
            });
    };

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

    $scope.findProductById = function () {
        $http.get(API_SERVER + '/books/' + $scope.filter.id)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                $scope.PageArray = [1];
            });
        console.log("findProductById")
    };

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
        console.log("createPageArray")
    };

    $scope.cleanFilter = function () {
        $scope.filter ? $scope.filter.title = null : null;
        $scope.fillTable();
        console.log("cleanFilter")
    };

 //   $scope.fillTable();
});