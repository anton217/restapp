'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('checkout', {
                parent: 'site',
                url: '/checkout',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/another-page/checkout.html',
                        controller: 'CheckoutController'
                    }
                },
                resolve: {

                }
            });
    });
