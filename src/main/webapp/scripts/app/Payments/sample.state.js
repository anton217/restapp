'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sample', {
                parent: 'site',
                url: '/sample',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/sample/sample.html',
                        controller: 'SampleController'
                    }
                },
                resolve: {

                }
            });
    });
