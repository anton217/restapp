'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payments', {
                parent: 'site',
                url: '/Payments',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/Payments/payments.html',
                        controller: 'PaymentsController'
                    }
                },
                resolve: {

                }
            });
    });
