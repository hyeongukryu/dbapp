angular.module('dbappApp',
  [
    'dbappApp.services',
    'dbappApp.controllers',
    'dbappApp.directives',
    'ui.router',
    'ngRoute',
    'ui.bootstrap'
  ]
)

.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', '$locationProvider',
  function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider) {
  
  $stateProvider
    .state('dbapp', {
      url: '',
      abstract: true,
      views: {
        '@': {
          templateUrl: 'partials/dbapp.html'
        },
        'nav@dbapp': {
          templateUrl: 'partials/nav.html'
        }
      }
    })
    .state('dbapp.home', {
      url: '/home',
      views: {
        content: {
          templateUrl: 'partials/home.html',
          controller: 'HomeController'
        }
      }
    })
    .state('dbapp.student', {
      url: '/student',
      views: {
        content: {
          templateUrl: 'partials/student.html',
          controller: 'StudentController'
        }
      }
    })
    .state('dbapp.admin', {
      url: '/admin',
      views: {
        content: {
          templateUrl: 'partials/admin.html',
          controller: 'AdminController'
        }
      }
    })

  $urlRouterProvider.otherwise('/home');
}])
