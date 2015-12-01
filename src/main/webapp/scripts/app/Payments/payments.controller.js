angular.module('restappApp')
    .controller('PaymentsController', function($scope) {

        $scope.name = 'Anton';

        $scope.submit = function() {
            console.log('hello');
        };

    });
