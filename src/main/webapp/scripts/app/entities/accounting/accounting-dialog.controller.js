'use strict';

angular.module('restappApp').controller('AccountingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Accounting',
        function($scope, $stateParams, $modalInstance, entity, Accounting) {

        $scope.accounting = entity;
        $scope.load = function(id) {
            Accounting.get({id : id}, function(result) {
                $scope.accounting = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:accountingUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.accounting.id != null) {
                Accounting.update($scope.accounting, onSaveFinished);
            } else {
                Accounting.save($scope.accounting, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
