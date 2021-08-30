angular.module('library').controller('bookByGenreController', function ($routeParams, $localStorage, $location) {
    if (!isNaN($routeParams.genreId)) {
        $localStorage.redirectedParam = $routeParams.genreId;
        console.log("redirect-down")
        $location.path("/shop-list");
    }
})
