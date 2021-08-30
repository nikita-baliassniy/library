angular.module('library').controller('contactsController', function ($scope, $http, API_SERVER) {
    $scope.sendMessage = function () {
        $http.post(API_SERVER + '/mail/feedback', $scope.mail).then(function successCallBack() {
            toastr.success("Сообщение успешно отправлено")
        }, function errorCallBack() {
            toastr.error("Сообщение успешно отправлено")
        })
    }
});