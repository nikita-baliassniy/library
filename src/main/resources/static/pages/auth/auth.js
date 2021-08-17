angular.module('library').controller('authController', function ($scope, $http, AuthService, API_SERVER) {

    $scope.tryAuth = function () {
        AuthService.login($scope.user.username, $scope.user.pass);
    }

});
