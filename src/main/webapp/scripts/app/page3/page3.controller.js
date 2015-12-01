angular.module('restappApp')
    .controller('Page3Controller', function($scope, ShoppingCartCache) {

        $scope.shoppingCart = ShoppingCartCache.get('CART');
        $scope.getTotal = function(){
            var total = 0;
            for(var i = 0; i < $scope.shoppingCart.length; i++){
                var product = $scope.shoppingCart[i];
                total = (product.count * product.cost);
            }
            return total;
        }
        $scope.sumTotal = function(){
            var sumtotal = 0;
            for(var i = 0; i < $scope.shoppingCart.length; i++){
                var product = $scope.shoppingCart[i];
                sumtotal += (product.count * product.cost);
            }
            return sumtotal;
        }
       //   var onSaveFinished = function (result) {
         //               $scope.$emit('restappApp:myOrderDetailsUpdate', result);
           //             $modalInstance.close(result);
            //        };

//                    $scope.save = function () {
                      //  if ($scope.myOrderDetails.id != null) {
  //                      shoppingCart.save($scope.shoppingCart, onSaveFinished);
                       // } else {
                        //    MyOrderDetails.save($scope.myOrderDetails, onSaveFinished);
                        //}
    //                };
    });
