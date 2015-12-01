'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment', {
                parent: 'site',
                url: '/payment',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/Payment/payment.html',
                        controller: 'paymentController'
                    }
                },
                resolve: {

                }
            });
    });
/**
 * Created by Dare on 31/10/2015.
 */
