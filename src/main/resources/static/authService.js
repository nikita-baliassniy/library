angular.module('library')
    .service('Auth', function ($http, $location, $q, $window, HOME_SERVER) {
    var Auth = {
        getToken: function () {
            return $window.localStorage.getItem('token');
        },

        setToken: function (token) {
            $window.localStorage.setItem('token', token);
        },

        deleteToken: function () {
            $window.localStorage.removeItem('token');
        },

        login: function (username, password) {
            var deferred = $q.defer();

            $http.post(HOME_SERVER + '/auth', {
                username: username, password: password
            }).success(function(response){
                if (response.token) {
                    Auth.setToken(response.token);
                }
                deferred.resolve(response);
            }).error(function(response) {
                deferred.reject(response);
            });
        }
    };
    return Auth;
});


