angular.module('library')
    .service('AuthService', function ($http, $location, $q, $window, HOME_SERVER, API_SERVER) {
        var Auth = {

            isAuthorized: function () {
                // console.log('getToken-----------');
                //  console.log($window.localStorage.getItem('token'));
                //  console.log($window.localStorage.getItem('isAuth'));
                return $window.localStorage.getItem('isAuth') === 'true';
            },

            setIsAuthorized: function (value) {
                $window.localStorage.setItem('isAuth', value);
            },

            getToken: function () {
                return $window.localStorage.getItem('token');
            },

            setToken: function (token) {
                $window.localStorage.setItem('token', token);
            },

            deleteToken: function () {
                $window.localStorage.removeItem('token');
            },

            getAlias: function () {
                return $window.localStorage.getItem('alias');
            },

            setAlias: function (alias) {
                $window.localStorage.setItem('alias', alias);
            },

            getUserId: function () {
                return $window.localStorage.getItem('userId');
            },

            getAdmin: function () {
                $http.get(API_SERVER + "/users/isAdmin")
                    .then(function successCallBack(response) {
                        $window.localStorage.isAdmin = response.data;
                    }, function errorCallBack() {
                        $window.localStorage.isAdmin = false;
                    })
            },

            isAdmin: function () {
                return $window.localStorage.isAdmin === 'true';
            },

            setUserId: function (userId) {
                $window.localStorage.setItem('userId', userId);
            },

            setNewsletterSub: function (subNewsletter) {
                $window.localStorage.setItem('subNewsletter', subNewsletter)
            },

            getNewsletterSub: function () {
                return $window.localStorage.getItem('subNewsletter') === 'true';
            },

            deleteAlias: function () {
                $window.localStorage.removeItem('alias');
            },

            deleteNewsletterSub: function () {
                $window.localStorage.removeItem('subNewsletter');
            },
            deleteIsAdmin: function () {
                $window.localStorage.removeItem('isAdmin');
            },

            checkTokenExpired: function () {
                if (this.isAuthorized()) {
                    $http.get(API_SERVER + '/users/self').then(function successGetInfo(response) {
                        // Auth.setAlias(response.data.name);
                        console.log('VALID TOKEN------');
                        Auth.setIsAuthorized('true');
                    }, function errorGetInfo(response) {
                        console.log("!!!!-----INVALID-------!!!!!");
                        Auth.setIsAuthorized('false');
                    });
                }
            },

            login: function (username, password) {
                var deferred = $q.defer();

                $http.post(HOME_SERVER + '/auth', {
                    username: username, password: password
                }).then(function successCallBack(response) {
                    if (response.data.token) {
                        Auth.setToken(response.data.token);
                        Auth.setIsAuthorized('true');
                        $http.get(API_SERVER + '/users/self').then(function successGetInfo(response) {
                            Auth.setAlias(response.data.name);
                            Auth.setNewsletterSub(response.data.subscribeNews);
                            console.log(response.data)
                            $location.path('/');
                            console.log('ALIAS------' + Auth.getAlias());
                            Auth.setUserId(response.data.id);
                            Auth.getUserId();
                            Auth.getAdmin();
                            toastr.success('?????????? ???????????????????? ' + Auth.getAlias());
                        }, function errorGetInfo(response) {
                            console.log("ERROR get self info");
                        });
                    }
                    deferred.resolve(response);

                }, function errorCallBack(response) {
                    Auth.setIsAuthorized('false');
                    toastr.error(response.data.message, '???????????? ??????????????????????')
                    deferred.reject(response);
                });
                return deferred.promise;
            },
            register: function (username, email, password) {
                var deferred = $q.defer();
                $http.put(HOME_SERVER + '/auth', {
                    name: username, email: email, password: password
                }).then(function successCallBack(response) {
                    console.log('OK REGISTER');
                    console.log(Auth);
                    Auth.getAdmin()
                    toastr.success('???? ???????????????????????????????? ?????? ?????????????? ' + email, '?????????????????????? ??????????????');
                    Auth.login(email, password).then(function successCallBack(response) {
                        console.log('OK LOGIN');
                        // Auth.setAlias(username);
                    });
                    deferred.resolve(response);
                }, function errorCallBack(response) {
                    console.log('ERROR REGISTER');
                    console.log(response);
                    console.log(response.data);
                    deferred.reject(response);
                    toastr.error(response.data.message, '???????????? ??????????????????????');
                });
            },
            logout: function () {

                Auth.deleteToken();
                Auth.deleteAlias();
                Auth.deleteNewsletterSub();
                Auth.setIsAuthorized('false');
            }
        };
        return Auth;
    });

angular.module('library')
    .service('AuthInterceptor', function ($injector, $location, $q) {
        var AuthInterceptor = {
            request: function (config) {
                var Auth = $injector.get('AuthService');
                var token = Auth.getToken();

                if (token) {
                    config.headers['Authorization'] = 'Bearer ' + token;
                }
                return config;
            },

            response: function (response) {
                var Auth = $injector.get('AuthService');
                // console.log("isvalidtokendate ------ CHECK ---- " + response.headers().isvalidtokendate);
                if (response.headers().isvalidtokendate === 'true') {
                    // console.log("isvalidtokendate ------ TRUE ---- " + response.headers().isvalidtokendate);
                    Auth.setIsAuthorized('true');
                } else if (response.headers().isvalidtokendate === 'false') {
                    // console.log("isvalidtokendate ------ FALSE ---- " + response.headers().isvalidtokendate);
                    Auth.deleteToken();
                    Auth.deleteAlias();
                    Auth.setIsAuthorized('false');
                }
                return response;
            },

            responseError: function (response) {
                var deferred = $q.defer();
                if (response.status === 403) {
                    console.log('******FORBIDDEN*********');
//                    var Auth = $injector.get('AuthService');

//                    $location.path('/auth');
                }
                if (response.status === 401) {
                    console.log('******UNAUTHORIZED*********');
                    var Auth = $injector.get('AuthService');
                    // var defer = $q.defer();
                    Auth.deleteToken();
                    Auth.deleteAlias();
                    Auth.setIsAuthorized('false');
//                    $location.path('/auth');
                }
                deferred.reject(response);
                return deferred.promise;
                // return response;
            }
        };
        return AuthInterceptor;
    });


