angular.module('restappApp')
    .factory('PaymentItemFactory', function($http) {
        var url = 'api/payment';

        return {
            menuItems: function(callback) {
                $http.get(url).success(callback);
            }
        }
    });
