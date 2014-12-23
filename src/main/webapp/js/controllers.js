var error = function () {
  swal('오류가 발생했습니다.', null, 'error');
};

angular.module('dbappApp.controllers', [])

.controller('NavController', ['DbappService', '$scope', function (DbappService, $scope) {
  $scope.studentId = function () {
    return DbappService.getStudentId();
  };
}])

.controller('HomeController', ['$scope', 'DbappService', function ($scope, DbappService) {
  $scope.students = null;
  DbappService.readAllStudents().success(function (data) {
    $scope.students = data;
  }).error(error);
  $scope.setStudentId = function (studentId) {
    DbappService.setStudentId(studentId);
  };
  $scope.studentId = function () {
    return DbappService.getStudentId();
  };
  $scope.registerFast = function (lectureNumber) {
    DbappService.registerNumber(lectureNumber).then(function (result) {
      if (result.data.result === 'success') {
        swal('성공적으로 신청했습니다.', null, 'success');
      } else {
        swal('신청하지 못했습니다.', null, 'error');
      }
    }, error);
  };
}])

.controller('StudentController', ['$scope', 'DbappService', '$state', function ($scope, DbappService, $state) {

  if (DbappService.getStudentId() === null) {
    $state.go('dbapp.home');
    return;
  }

  $scope.years = null;
  $scope.majors = null;
  DbappService.readAllYears().success(function (data) {
    $scope.years = data;
    if (data.length) {
      $scope.year = data[data.length - 1];
    }
  });
  DbappService.readAllMajors().success(function (data) {
    $scope.majors = data;
    if (data.length) {
      $scope.major = data[0];
    }
  });
  $scope.schoolYear = 3;

  var updateRegistrations = function () {
    DbappService.registrations().then(function (result) {
      var data = result.data;
      _.forEach($scope.lectures, function (lecture) {
        lecture.registered = false;
        _.forEach(data, function (registration) {
          if (lecture.lectureId === registration.lectureId) {
            lecture.registered = true;
          }
        });
      });
    }, error);
  };

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

      DbappService.searchCatalog(query).then(function (result) {
        var data = result.data;
        $scope.lectures = data.lectures;
        _.forEach($scope.lectures, function (lecture) {
          lecture.timeTable = [];
          _.forEach(data.timeTable, function (time) {
            if (lecture.lectureId != time.lectureId) {
              return;
            }
            var lectureTime = {
              period: time.period,
              startTime: time.startTime ? moment(time.startTime).add(9, 'hours') : null,
              endTime: time.endTime ? moment(time.endTime).add(9, 'hours') : null
            };
            lecture.timeTable.push(lectureTime);
          });
        });
        updateRegistrations();
      }, error);
    } catch (e) {
      swal('검색 조건이 잘못되었습니다.', null, 'error');
    }
  };

  $scope.register = function (lectureId) {
    DbappService.register(lectureId).then(function (result) {
      if (result.data.result === 'success') {
        swal('성공적으로 신청했습니다.', null, 'success');
      } else {
        swal('신청하지 못했습니다.', null, 'error');
      }
      updateRegistrations();
    }, function () {
      error();
      updateRegistrations();
    });
  };

  $scope.unregister = function (lectureId) {
    DbappService.unregister(lectureId).then(function (result) {
      if (result.data.result === 'success') {
        swal('성공적으로 취소했습니다.', null, 'success');
      } else {
        swal('취소하지 못했습니다.', null, 'error');
      }
      updateRegistrations();
    }, function () {
      error();
      updateRegistrations();
    });
  };
}])

.controller('TimetableController', ['$scope', '$state', 'DbappService', function ($scope, $state, DbappService) {
  if (DbappService.getStudentId() === null) {
    $state.go('dbapp.home');
    return;
  }

  $scope.calendarConfig = {
    height: 600,
    editable: false,
    lang: 'ko',
    header: {
      left: '',
      center: '',
      right: ''
    },
    defaultView: 'agendaWeek',
    defaultDate: '1900-01-01',
    columnFormat: {
      week: 'dddd'
    },
    eventLimit: true
  };

  $scope.data = null;
  $scope.times = [];
  var times = [];

  DbappService.timetableYear(function (timetables) {
    _.forEach(timetables, function (year) {
      if (year.year != 2014) {
        return;
      }
      _.forEach(year.registrations, function (reg) {
        _.forEach(reg.times, function (t) {
          if (!t.startTime || !t.endTime) {
            return;
          }
          times.push({
            title: reg.subjectTitle,
            start: t.startTime,
            end: t.endTime
          });
        });
      });
    });
    $scope.times = [times];
    $scope.data = {};
  }, error);
}])

.controller('RegistrationsController', ['$scope', 'DbappService', '$state', function ($scope, DbappService, $state) {
  if (DbappService.getStudentId() === null) {
    $state.go('dbapp.home');
    return;
  }

  var updateAll = function () {
    $scope.data = null;
    DbappService.timetableYear(function (timetables) {
      $scope.data = timetables;
    }, error);
  };

  $scope.clear = function () {
    swal({
      title: '이번 연도 신청 내역 초기화',
      text: '정말로 이번 연도 신청 내역 초기화 작업을 하시겠습니까?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: '진행',
      cancelButtonText: '취소',
      closeOnConfirm: false
    }, function () {
      DbappService.clear().then(function (result) {
        if (result.data.result === 'success') {
          swal('성공적으로 초기화했습니다.', null, 'success');
        } else {
          swal('초기화하지 못했습니다.', null, 'error');
        }
        updateAll();
      }, function () {
        error();
        updateAll();
      });
    });
  };

  $scope.unregister = function (lectureId) {
    DbappService.unregister(lectureId).then(function (result) {
      if (result.data.result === 'success') {
        swal('성공적으로 취소했습니다.', null, 'success');
      } else {
        swal('취소하지 못했습니다.', null, 'error');
      }
      updateAll();
    }, function () {
      error();
      updateAll();
    });
  };

  updateAll();
}])

.controller('AdminController', ['$scope', 'DbappService', function ($scope, DbappService) {
  $scope.years = null;
  $scope.majors = null;
  DbappService.readAllYears().success(function (data) {
    $scope.years = data;
    if (data.length) {
      $scope.year = data[data.length - 1];
    }
  });
  DbappService.readAllMajors().success(function (data) {
    $scope.majors = data;
    if (data.length) {
      $scope.major = data[0];
    }
  });
  $scope.schoolYear = 3;

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

      DbappService.searchCatalog(query).then(function (result) {
        var data = result.data;
        $scope.lectures = data.lectures;
        _.forEach($scope.lectures, function (lecture) {
          lecture.timeTable = [];
          _.forEach(data.timeTable, function (time) {
            if (lecture.lectureId != time.lectureId) {
              return;
            }
            var lectureTime = {
              period: time.period,
              startTime: time.startTime ? moment(time.startTime).add(9, 'hours') : null,
              endTime: time.endTime ? moment(time.endTime).add(9, 'hours') : null
            };
            lecture.timeTable.push(lectureTime);
          });
        });
      }, error);
    } catch (e) {
      swal('검색 조건이 잘못되었습니다.', null, 'error');
    }
  };

  $scope.create = function () {
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
      query.instructorName = $('#instructorName').val();

      if (!query.lectureNumber || !query.subjectId || !query.instructorName) {
        throw new Error();
      }

      DbappService.create(query).then(function (result) {
        if (result.data.result === 'success') {
          swal('성공적으로 설강했습니다.', null, 'success');
        } else {
          swal('설강하지 못했습니다.', null, 'error');
        }
        $scope.search();
      }, function () {
        error();
        $scope.search();
      });
    } catch (e) {
      console.log(e);
      swal('입력이 잘못되었습니다.', null, 'error');
    }
  };

  $scope.drop = function (lectureId) {
    var doDrop = function () {
      DbappService.drop(lectureId).then(function (result) {
        if (result.data.result === 'success') {
          swal('성공적으로 폐강했습니다.', null, 'success');
        } else {
          swal('폐강하지 못했습니다.', null, 'error');
        }
        $scope.search();
      }, function () {
        error();
        $scope.search();
      });
    };

    swal({
      title: '폐강 확인',
      text: '수강 학생이 있는 수업은 폐강할 수 없으며, 또한 이 작업은 되돌릴 수 없습니다. 정말로 폐강 하시겠습니까?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: '진행',
      cancelButtonText: '취소',
      closeOnConfirm: false
    }, doDrop);
  };
}])

.controller('StatisticsController', ['$scope', 'DbappService', function ($scope, DbappService) {
  
  $scope.complete = false;
  var count = 3;
  var end = function () {
    if (--count === 0) {
      $scope.complete = true;
    }
  };

  $scope.s1y = [];
  $scope.s2y = [];
  $scope.s3y = [];
  $scope.s1 = null;
  $scope.s2 = null;
  $scope.s3 = null;

  DbappService.statistics1(0).success(function (data) {
    $scope.s1 = data;
    end();
  }).error(error);
  DbappService.statistics2(0).success(function (data) {
    $scope.s2 = data;
    end();
  }).error(error);
  DbappService.statistics3(0).success(function (data) {
    $scope.s3 = data;
    end();
  }).error(error);

  DbappService.readAllYears().success(function (years) {
    count += years.length * 3;
    _.forEach(years, function (year) {
      DbappService.statistics1(year).success(function (data) {
        $scope.s1y.push({ year: year, data: data });
        end();
      }).error(error);
      DbappService.statistics2(year).success(function (data) {
        $scope.s2y.push({ year: year, data: data });
        end();
      }).error(error);
      DbappService.statistics3(year).success(function (data) {
        $scope.s3y.push({ year: year, data: data });
        end();
      }).error(error);
    });
  }).error(error);
}])
