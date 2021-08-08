(function () {
    'use strict';

    angular
        .module('library', ['ngRoute', 'ngStorage'])
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
            .when('/product-details', {
                templateUrl: 'pages/product-details/product-details.html',
                controller: 'productDetailsController'
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