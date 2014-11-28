angular.module('dbappApp.directives',
  [
  ]
)

.directive('refreshIcon', function () {
  return {
    restrict: 'E',
    template: '<div class="text-center"><p><span class="glyphicon glyphicon-refresh refresh-icon"></span></p>불러오는 중</div>'
  };
})

.directive('showRefresh', ['$animate', function ($animate) {
  return {
    priority: 1001,
    restrict: 'A',
    multiElement: true,
    compile: function (element) {
      var refreshIcon = angular.element('<refresh-icon></refresh-icon>');
      element.after(refreshIcon);
      return {
        post: function (scope, element, attr) {
          scope.$watch(attr.showRefresh, function (value) {
            $animate[value ? 'removeClass' : 'addClass'](element, 'ng-hide');
            $animate[!value ? 'removeClass' : 'addClass'](refreshIcon, 'ng-hide');
          });
        }
      };
    }
  };
}])
