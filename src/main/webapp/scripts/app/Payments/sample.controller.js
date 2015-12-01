angular.module('restappApp')
    .controller('SampleController', function($scope) {

        $scope.name = 'Anton';

        $scope.submit = function() {
            console.log('hello');
        };

    });
