angular.module('dbappApp',
  [
    'dbappApp.services',
    'dbappApp.controllers',
    'dbappApp.directives',
    'ui.router',
    'ngRoute',
    'ui.bootstrap',
    'ui.calendar'
  ]
)

.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', '$locationProvider',
  function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider) {
  
  moment.locale('ko-KR');

  $stateProvider
    .state('dbapp', {
      url: '',
      abstract: true,
      views: {
        '@': {
          templateUrl: 'partials/dbapp.html'
        },
        'nav@dbapp': {
          templateUrl: 'partials/nav.html',
          controller: 'NavController'
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
    .state('dbapp.timetable', {
      url: '/timetable',
      views: {
        content: {
          templateUrl: 'partials/timetable.html',
          controller: 'TimetableController'
        }
      }
    })
    .state('dbapp.registrations', {
      url: '/registrations',
      views: {
        content: {
          templateUrl: 'partials/registrations.html',
          controller: 'RegistrationsController'
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
    .state('dbapp.statistics', {
      url: '/statistics',
      views: {
        content: {
          templateUrl: 'partials/statistics.html',
          controller: 'StatisticsController'
        }
      }
    })

  $urlRouterProvider.otherwise('/home');
}])
