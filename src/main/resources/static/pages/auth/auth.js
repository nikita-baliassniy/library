angular.module('library').controller('authController', function ($scope, $http, AuthService, API_SERVER) {

    $scope.tryAuth = function () {
        AuthService.login($scope.user.username, $scope.user.pass);
    }

    $scope.register = function () {
        AuthService.register($scope.newUser.name, $scope.newUser.email, $scope.newUser.password);
    }

});
