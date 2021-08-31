angular.module('library').controller('adminController', function ($scope, $http, API_SERVER) {

        $scope.showTab = function (tabName) {
            var element = document.querySelector('#my-accountContent > .active');
            element.classList.remove('active');
            element.classList.remove('show');
            var elementToEnable = document.querySelector('#' + tabName.substring(tabName.lastIndexOf('-') + 1,
                tabName.length));
            elementToEnable.classList.add('active');
            elementToEnable.classList.add('show');
        }

        $scope.getRoles = function () {
            $http.get(API_SERVER + "/roles")
                .then(function (response) {
                    $scope.Roles = response.data;
                })
        }

        $scope.getWorkers = function () {
            $http.get(API_SERVER + "/users/workers")
                .then(function (response) {
                    $scope.Workers = response.data;
                })
        }

        $scope.getUsers = function () {
            $http.get(API_SERVER + "/users/users")
                .then(function (response) {
                    $scope.Users = response.data;
                })
        }

        $scope.getAdvices = function () {
            $http({
                method: 'GET',
                url: API_SERVER + '/books',
                params: {
                    page: 1,
                    count: 999,
                    editorsAdvice: 'true'
                }
            }).then(function (response) {
                $scope.advicesPage = response.data;
                $scope.AdvicesList = $scope.advicesPage.content;
            })
        }

        $scope.getNotAdvices = function () {
            $http({
                method: 'GET',
                url: API_SERVER + '/books',
                params: {
                    page: 1,
                    count: 999,
                    editorsAdvice: 'false'
                }
            }).then(function (response) {
                $scope.notAdvicesPage = response.data;
                $scope.NotAdvicesList = $scope.notAdvicesPage.content;
            })
        }

        $scope.getDiscount = function () {
            $http({
                method: 'GET',
                url: API_SERVER + '/books',
                params: {
                    page: 1,
                    count: 999,
                    discount: 1
                }
            }).then(function (response) {
                $scope.discountPage = response.data;
                $scope.DiscountList = $scope.discountPage.content;
            })
        }

        $scope.getNotDiscount = function () {
            $http({
                method: 'GET',
                url: API_SERVER + '/books',
                params: {
                    page: 1,
                    count: 999,
                    discount: -1
                }
            }).then(function (response) {
                $scope.notDiscountPage = response.data;
                $scope.NotDiscountList = $scope.notDiscountPage.content;
            })
        }

        $scope.updateRole = function (id, role) {
            $http({
                url: API_SERVER + '/users/changeRole',
                method: 'PUT',
                params: {
                    userId: id,
                    roleId: role
                }
            }).then(function () {
                $scope.getUsers();
                $scope.getWorkers();
            });
        }

        $scope.addNewUser = function () {
            let addBtn = document.getElementById('add');
            let saveBtn = document.getElementById('save');
            let saveRow = document.getElementById('new-user');
            addBtn.hidden = true;
            saveBtn.hidden = false;
            saveRow.hidden = false;
        }

        $scope.saveNewUser = function () {
            if ($scope.selectedUser != null) $scope.updateRole($scope.selectedUser.id, $scope.selectedUser.roles[0].id)
            let addBtn = document.getElementById('add');
            let saveBtn = document.getElementById('save');
            let saveRow = document.getElementById('new-user');
            addBtn.hidden = false;
            saveBtn.hidden = true;
            saveRow.hidden = true;
        }

        $scope.addNewAdvice = function () {
            let addBtn = document.getElementById('add-advice');
            let saveBtn = document.getElementById('save-advice');
            let saveRow = document.getElementById('new-advice');
            addBtn.hidden = true;
            saveBtn.hidden = false;
            saveRow.hidden = false;
        }

        $scope.saveNewAdvice = function () {
            if ($scope.selectedBook != null) $scope.changeAdvice($scope.selectedBook.id)
            let addBtn = document.getElementById('add-advice');
            let saveBtn = document.getElementById('save-advice');
            let saveRow = document.getElementById('new-advice');
            addBtn.hidden = false;
            saveBtn.hidden = true;
            saveRow.hidden = true;
        }

        $scope.addNewDiscount = function () {
            let addBtn = document.getElementById('add-discount');
            let saveBtn = document.getElementById('save-discount');
            let saveRow = document.getElementById('new-discount');
            addBtn.hidden = true;
            saveBtn.hidden = false;
            saveRow.hidden = false;
        }

        $scope.saveNewDiscount = function () {
            if ($scope.discountBook != null) $scope.updateDiscount($scope.discountBook.id, $scope.discountBook.discount)
            let addBtn = document.getElementById('add-discount');
            let saveBtn = document.getElementById('save-discount');
            let saveRow = document.getElementById('new-discount');
            addBtn.hidden = false;
            saveBtn.hidden = true;
            saveRow.hidden = true;
        }

        $scope.updateDiscount = function (bookId, discount) {
            $http({
                url: API_SERVER + '/books/updateDiscount',
                method: 'PUT',
                params: {
                    bookId: bookId,
                    discount: discount
                }
            }).then(function () {
                $scope.getDiscount();
                $scope.getNotDiscount();
            });
        }

        $scope.changeAdvice = function (id) {
            $http({
                url: API_SERVER + '/books/changeAdvice',
                method: 'PUT',
                params: {
                    bookId: id
                }
            }).then(function () {
                $scope.getAdvices();
                $scope.getNotAdvices();
            })
        }

        $scope.initUsersPage = function () {
            $scope.getRoles();
            $scope.getWorkers();
            $scope.getUsers();
        }

        $scope.sendMessage = function () {
            $http.post(API_SERVER + '/mail/broadcast', $scope.mail).then(function successCallBack() {
                toastr.success("Сообщение успешно отправлено")
            }, function errorCallBack() {
                toastr.error("Сообщение успешно отправлено")
            })
        }
    }
);