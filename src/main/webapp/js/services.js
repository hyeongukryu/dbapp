angular.module('dbappApp.services', [])

.factory('DbappService', ['$http', function ($http) {
  var encode = encodeURI;

  var buildQuery = function (data) {
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

  return {
    readAllYears: function () {
      return $http.get('/years');
    },
    readAllMajors: function () {
      return $http.get('/majors');
    },
    searchCatalog: function (data) {
      var query = buildQuery(data);
      return $http.get(query);
    }
  };
}])
