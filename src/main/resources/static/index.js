(function ($localStorage) {
    'use strict';

    angular
        .module('library', ['ngRoute', 'ngStorage', 'ngMaterial'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');
        $routeProvider
            .when('/admin', {
                templateUrl: 'pages/admin/admin.html',
                controller: 'adminController'
            })
            .when('/auth', {
                templateUrl: 'pages/auth/auth.html',
                controller: 'authController'
            })
            .when('/register', {
                templateUrl: 'pages/register/register.html',
                controller: 'registerController'
            })
            .when('/contacts', {
                templateUrl: 'pages/contacts/contacts.html',
                controller: 'contactsController'
            })
            .when('/authors/:authorId', {
                templateUrl: 'pages/author/author.html',
                controller: 'authorController'
            })
            .when('/cart', {
                templateUrl: 'pages/cart/cart.html',
                controller: 'cartController'
            })
            .when('/checkout', {
                templateUrl: 'pages/checkout/checkout.html',
                controller: 'checkoutController'
            })
            .when('/', {
                templateUrl: 'pages/home/home.html',
                controller: 'homeController'
            })
            .when('/orders-history', {
                templateUrl: 'pages/orders-history/orders-history.html',
                controller: 'ordersHistoryController'
            })
            .when('/books/:bookId', {
                templateUrl: 'pages/book-detail/book-detail.html',
                controller: 'bookDetailController'
            })
            .when('/shop-list', {
                templateUrl: 'pages/shop-list/shop-list.html',
                controller: 'shopListController'
            })
            .when('/shop-list/genre/:genreId', {
                templateUrl: 'pages/home/home.html',
                controller: 'bookByGenreController'
            })
            .when('/compilation/:genreId/:compilationName', {
                templateUrl: 'pages/book-compilation/book-compilation.html',
                controller: 'bookCompilationController'
            })
            .when('/find-results/:findRequest', {
                templateUrl: 'pages/find-results/find-results.html',
                controller: 'findResultsController'
            })
            .when('/userDetails', {
                templateUrl: 'pages/user-details/user-details.html',
                controller: 'userDetailsController'
            })
            .when('/my-account', {
                templateUrl: 'pages/my-account/my-account.html',
                controller: 'myAccountController'
            })
            .when('/recommendation', {
                templateUrl: 'pages/recommendation/recommendation.html',
                controller: 'recommendationController'
            })
            .when('/admin', {
                templateUrl: 'pages/admin/admin.html',
                controller: 'adminController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage, API_SERVER) {

//        if (!$localStorage.needToUpdateCart) {
            $localStorage.needToUpdateCart = new Counter1();
//        }
        // if ($localStorage.authUser) {
        //     $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.authUser.token;
        // }
        if (!$localStorage.marketCartUuid) {
            $http.post(API_SERVER + '/cart')
                .then(function (response) {
                    $localStorage.marketCartUuid = response.data;
                    console.log($localStorage.marketCartUuid);
                });
        }
    }
})();

angular.module('library').constant('API_SERVER', 'http://localhost:8189/lib/api/v1');
angular.module('library').constant('HOME_SERVER', 'http://localhost:8189/lib');
angular.module('library').controller('indexController', function ($scope, $http, $location, $localStorage, AuthService, API_SERVER) {

    let searchField = document.getElementById('search-field');
    let helperPane = document.getElementById('helperPane');


    $scope.getCart = function () {
        $http.get(API_SERVER + '/cart/' + $localStorage.marketCartUuid)
            .then(function (response) {
                $scope.Cart = response.data;
            })
    }

    $localStorage.needToUpdateCart.registerListener(function (val) {
        $scope.getCart();
    });

    $scope.deleteBookInCartById = function (bookId) {
        $http({
            url: API_SERVER + '/cart',
            method: 'DELETE',
            params: {
                uuid: $localStorage.marketCartUuid,
                book_id: bookId
            }
        }).then(function (response) {
            $localStorage.needToUpdateCart.set(1);
        });
    }

    $scope.findResults = function () {
        let parameter = searchField.value;
        $scope.clearSearchField();
        $location.path("/find-results/" + parameter);
    }

    searchField.addEventListener('input', function () {
        helperPane.hidden = false;
        $scope.findByTitle();
        $scope.findByAuthor();
    });

    helperPane.addEventListener('mouseleave', function () {
        helperPane.hidden = true;
    })

    $scope.alias = AuthService.getAlias();

    $scope.findByTitle = function () {
        if (searchField.value !== "") {
            $http({
                url: API_SERVER + '/books',
                method: 'GET',
                params: {
                    title: searchField.value,
                    page: 1,
                    count: 100,
                    sort: "title,asc"
                }
            }).then(function (response) {
                $scope.HelperBookList = response.data.content;
            })
        }
    }

    $scope.findByAuthor = function () {
        if (searchField.value !== "") {
            $http({
                url: API_SERVER + '/authors',
                method: 'GET',
                params: {
                    name: searchField.value,
                    sort: "name,asc"
                }
            }).then(function (response) {
                $scope.HelperAuthorList = response.data;
                console.log($scope.HelperAuthorList);
            })
        }
    }

    $scope.clearSearchField = function () {
        delete $scope.HelperAuthorList;
        delete $scope.HelperBookList
        searchField.value = null;
    }

    $scope.checkAuth = function () {
        $scope.userId = AuthService.getUserId();
        return AuthService.isAuthorized();
    }

    $scope.checkSubNewsletter = function () {
        return AuthService.getNewsletterSub();
    }

    $scope.checkToken = function () {
        AuthService.checkTokenExpired();
    }

    $scope.doLogout = function () {
        AuthService.logout();
    }

    $scope.getGenres = function () {
        $http.get(API_SERVER + '/genres').then(function successCallBack(response) {
            $scope.genres = response.data;
        })
    }

    $scope.sub = function () {
        $http.get(API_SERVER + '/mail/sub').then(function successCallBak(response) {
            AuthService.setNewsletterSub(true);
            toastr.success("Вы успешно подписались на рассылку новостей")
        }, function errorCallBack() {
            toastr.error("Ошибка сервера при оформлении подписки на новостную рассылку")
        });
    }

    $scope.unsub = function () {
        $http.get(API_SERVER + '/mail/unsub').then(function successCallBak(response) {
            AuthService.setNewsletterSub(false);
            toastr.success("Вы отписались от рассылки новостей")
        }, function errorCallBack() {
            toastr.error("Ошибка сервера при отписке от новостной рассылки")
        });
    }

    $scope.getGenres();
    $scope.getCart();
});

angular.module('library').directive('starRating', function () {
    return {
        restrict: 'A',
        template: '<span ng-repeat="star in stars" ng-class="star" class="fas" ng-click="toggle($index)"></span>',
        scope: {
            ratingValue: '=',
            max: '=',
            clicked: '@',
            onRatingSelected: '&'
        },
        link: function (scope, elem, attrs) {

            var updeteStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    if ((scope.ratingValue - i) >= 1) {
                        scope.stars.push('fa-star star_on ' + i);
                    } else if (((scope.ratingValue - i) < 1) && ((scope.ratingValue - i) > 0)) {
                        scope.stars.push('fa-star-half-alt star_on ' + i);
                    } else {
                        scope.stars.push('fa-star ' + i);
                    }
                }
            }

            scope.toggle = function (index) {
                if (scope.clicked === '') {
                    scope.ratingValue = index + 1;
                    scope.onRatingSelected({
                        rating: index + 1
                    });
                }
            };

            scope.$watch('ratingValue', function (newVal, oldVal) {
                if (newVal !== undefined) {
                    updeteStars();
                }
            });

        }
    }
});
