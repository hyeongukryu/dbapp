angular.module('dbappApp.controllers', [])

.controller('HomeController', [function () {
}])

.controller('StudentController', ['$scope', 'DbappService', function ($scope, DbappService) {

  $scope.years = null;
  $scope.majors = null;
  DbappService.readAllYears().success(function (data) {
    $scope.years = data;
    if (data.length) {
      $scope.year = data[0];
    }
  });
  DbappService.readAllMajors().success(function (data) {
    $scope.majors = data;
    if (data.length) {
      $scope.major = data[0];
    }
  });
  $scope.schoolYear = 3;

  moment.locale('ko-KR');

  $scope.search = function () {
    try {
      var query = {};
      if (!$scope.major || !$scope.year || !$scope.schoolYear) {
        throw new Error();
      }

      query.majorId = $scope.major.id;
      query.year = $scope.year;
      query.schoolYear = $scope.schoolYear;

      query.lectureNumber = $('#lectureNumber').val();
      query.subjectId = $('#subjectId').val();
      query.subjectTitle = $('#subjectTitle').val();
      query.instructorName = $('#instructorName').val();

      DbappService.searchCatalog(query).success(function (data) {
        $scope.lectures = data.lectures;
        _.forEach($scope.lectures, function (lecture) {
          lecture.timeTable = [];
          _.forEach(data.timeTable, function (time) {
            if (lecture.lectureId == time.lectureId) {
              var lectureTime = {
                period: time.period,
                startTime: time.startTime ? moment(time.startTime) : null,
                endTime: time.endTime ? moment(time.endTime) : null
              };
              lecture.timeTable.push(lectureTime);
            }
          });
        });
      }).error(function () {
        swal('오류가 발생했습니다.', null, 'error');
      });
    } catch (e) {
      swal('검색 조건이 잘못되었습니다.', null, 'error');
    }
  };
}])

.controller('AdminController', [function () {
}])