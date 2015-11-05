'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('accounting', {
                parent: 'entity',
                url: '/accountings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Accountings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/accounting/accountings.html',
                        controller: 'AccountingController'
                    }
                },
                resolve: {
                }
            })
            .state('accounting.detail', {
                parent: 'entity',
                url: '/accounting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Accounting'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/accounting/accounting-detail.html',
                        controller: 'AccountingDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Accounting', function($stateParams, Accounting) {
                        return Accounting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('accounting.new', {
                parent: 'accounting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/accounting/accounting-dialog.html',
                        controller: 'AccountingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dailysalestotal: null,
                                    dailytipstotal: null,
                                    addcouponvalue: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('accounting', null, { reload: true });
                    }, function() {
                        $state.go('accounting');
                    })
                }]
            })
            .state('accounting.edit', {
                parent: 'accounting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/accounting/accounting-dialog.html',
                        controller: 'AccountingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Accounting', function(Accounting) {
                                return Accounting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('accounting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
