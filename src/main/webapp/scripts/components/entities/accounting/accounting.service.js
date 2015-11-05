'use strict';

angular.module('restappApp')
    .factory('Accounting', function ($resource, DateUtils) {
        return $resource('api/accountings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
