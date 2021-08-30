angular.module('library').controller('registerController', function ($scope, $http, AuthService) {

    $scope.register = function () {
        AuthService.register($scope.newUser.name, $scope.newUser.email, $scope.newUser.password);
    }

});
