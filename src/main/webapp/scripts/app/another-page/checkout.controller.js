angular.module('restappApp')
    .controller('CheckoutController', function($scope) {

        $scope.name = 'Anton';

        $scope.submit = function() {
            console.log('hello');
        };

    });
