(function () {
    'use strict';

    angular
        .module('library', ['ngRoute', 'ngStorage', 'ngMaterial'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/admin', {
                templateUrl: 'pages/admin/admin.html',
                controller: 'adminController'
            })
            .when('/auth', {
                templateUrl: 'pages/auth/auth.html',
                controller: 'authController'
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
            .when('/userDetails', {
                templateUrl: 'pages/user-details/user-details.html',
                controller: 'userDetailsController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.authUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.authUser.token;
        }
    }
})();

angular.module('library').controller('indexController', function ($scope, $http, $localStorage) {

});

angular.module('library').directive('starRating', function () {
    return {
        restrict: 'A',
        template: '<span ng-repeat="star in stars" ng-class="star" class="fas" ng-click="toggle($index)"></span>',
        scope: {
            ratingValue: '=',
            max: '=',
            clicked: '@'
        },
        link: function (scope, elem, attrs) {

            var updeteStars = function() {
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

            scope.toggle = function(index) {
                if (scope.clicked === '') {
                    scope.ratingValue = index + 1;
                     scope.onRatingSelected({
                        rating: index + 1
                    });
                }
            };

            scope.$watch('ratingValue', function(newVal, oldVal) {
                if (newVal !== undefined) {
                    updeteStars();
                }
            });

        }
    }
});
