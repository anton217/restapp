'use strict';

angular.module('restappApp')
    .controller('AccountingDetailController', function ($scope, $rootScope, $stateParams, entity, Accounting) {
        $scope.accounting = entity;
        $scope.load = function (id) {
            Accounting.get({id: id}, function(result) {
                $scope.accounting = result;
            });
        };
        var unsubscribe = $rootScope.$on('restappApp:accountingUpdate', function(event, result) {
            $scope.accounting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
