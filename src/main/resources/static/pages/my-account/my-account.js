angular.module('library').controller('myAccountController', function ($scope, $http, API_SERVER) {

    $scope.showTab = function (tabName) {
        var element = document.querySelector('#my-accountContent > .active');
        element.classList.remove('active');
        element.classList.remove('show');
        var elementToEnable = document.querySelector('#' + tabName.substring(tabName.lastIndexOf('-') + 1,
            tabName.length));
        elementToEnable.classList.add('active');
        elementToEnable.classList.add('show');
    }
});