angular.module('dbappApp.services', [])

.factory('DbappService', ['$http', function ($http) {
  var encode = encodeURI;

  var buildSearchQuery = function (data) {
    var query = '/searchCatalog';
    if (!data.majorId || !data.year || !data.schoolYear) {
      throw new Error();
    }
    query += '?majorId={0}&year={1}&schoolYear={2}';
    query = query
      .split('{0}').join(encode(data.majorId))
      .split('{1}').join(encode(data.year))
      .split('{2}').join(encode(data.schoolYear));

    var addOptional = function (key) {
      if (data[key]) {
        query += ('&' + key + '={0}').split('{0}').join(encode(data[key]));
      }
    };
    addOptional('lectureNumber');
    addOptional('subjectId');
    addOptional('subjectTitle');
    addOptional('instructorName');
    return query;
  };

  var buildCreateQuery = function (data) {
    var query = '/create';
    if (!data.majorId || !data.year || !data.schoolYear ||
      !data.lectureNumber || !data.subjectId || !data.instructorName) {
      throw new Error();
    }

    query += '?majorId={0}&year={1}&schoolYear={2}&lectureNumber={3}&subjectId={4}&instructorName={5}';
    query = query
      .split('{0}').join(encode(data.majorId))
      .split('{1}').join(encode(data.year))
      .split('{2}').join(encode(data.schoolYear))
      .split('{3}').join(encode(data.lectureNumber))
      .split('{4}').join(encode(data.subjectId))
      .split('{5}').join(encode(data.instructorName));
    return query;
  };

  var studentId = null;

  var service = {
    setStudentId: function (id) {
      studentId = id;
    },
    getStudentId: function () {
      return studentId;
    },
    readAllStudents: function () {
      return $http.get('/students');
    },
    readAllYears: function () {
      return $http.get('/years');
    },
    statistics1: function (year) {
      return $http.get('/statistics1?year=' + year);
    },
    statistics2: function (year) {
      return $http.get('/statistics2?year=' + year);
    },
    statistics3: function (year) {
      return $http.get('/statistics3?year=' + year);
    },
    drop: function (lectureId) {
      return $http.get('/drop?lectureId=' + lectureId);  
    },
    readAllMajors: function () {
      return $http.get('/majors');
    },
    searchCatalog: function (data) {
      var query = buildSearchQuery(data);
      return $http.get(query);
    },
    registrations: function () {
      return $http.get('/registrations?studentId=' + studentId);
    },
    registrationTimes: function () {
      return $http.get('/registrationTimes?studentId=' + studentId);
    },
    registrationYears: function () {
      return $http.get('/registrationYears?studentId=' + studentId);
    },
    register: function (lectureId) {
      return $http.get('/register?studentId=' + studentId + '&lectureId=' + lectureId);
    },
    registerNumber: function (lectureNumber) {
      return $http.get('/registerNumber?studentId=' + studentId + '&lectureNumber=' + lectureNumber);
    },
    clear: function () {
      return $http.get('/clear?studentId=' + studentId);
    },
    unregister: function (lectureId) {
      return $http.get('/unregister?studentId=' + studentId + '&lectureId=' + lectureId);
    },
    create: function (data) {
      var query = buildCreateQuery(data);
      return $http.get(query);
    }
  };

  var composeTimetable = function (registrations, times, years) {
    var timetable = [];
    _.forEach(years, function (year) {
      var list = [];
      _.forEach(registrations, function (reg) {
        if (reg.year != year) {
          return;
        }
        reg.times = [];
        _.forEach(times, function (time) {
          if (reg.lectureId != time.lectureId) {
            return;
          }
          var lectureTime = {
            period: time.period,
            startTime: time.startTime ? moment(time.startTime).add(9, 'hours') : null,
            endTime: time.endTime ? moment(time.endTime).add(9, 'hours') : null
          };
          reg.times.push(lectureTime);
        });
        list.push(reg);
      });
      timetable.push({
        year: year,
        registrations: list
      });
    });
    return timetable;
  };

  service.timetableYear = function (success, error) {
    var registrations = service.registrations();
    var registrationTimes = service.registrationTimes();
    var registrationYears = service.registrationYears();
    registrations.then(function (registrations) {
      registrationTimes.then(function (times) {
        registrationYears.then(function (years) {
          var result = composeTimetable(registrations.data, times.data, years.data);
          success(result);
        }, error);
      }, error);
    }, error);
  };
  return service;
}])
