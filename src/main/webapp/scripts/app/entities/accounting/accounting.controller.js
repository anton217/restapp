'use strict';

angular.module('restappApp')
    .controller('AccountingController', function ($scope, Accounting) {
        $scope.accountings = [];
        $scope.loadAll = function() {
            Accounting.query(function(result) {
               $scope.accountings = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Accounting.get({id: id}, function(result) {
                $scope.accounting = result;
                $('#deleteAccountingConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Accounting.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAccountingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.accounting = {
                dailysalestotal: null,
                dailytipstotal: null,
                addcouponvalue: null,
                id: null
            };
        };
    });
