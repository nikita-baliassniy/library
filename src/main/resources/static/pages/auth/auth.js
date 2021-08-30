angular.module('library').controller('authController', function ($scope, $http, AuthService) {

    $scope.tryAuth = function () {
        AuthService.login($scope.user.username, $scope.user.pass);
    }

});
