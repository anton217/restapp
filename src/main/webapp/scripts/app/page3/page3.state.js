'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('page3', {
                parent: 'site',
                url: '/page3',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/page3/page3.html',
                        controller: 'Page3Controller'
                    }
                },
                resolve: {

                }
            });
    });
