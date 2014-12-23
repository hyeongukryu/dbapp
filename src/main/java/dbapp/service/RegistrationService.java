package dbapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dbapp.domain.LectureTime;
import dbapp.persistence.LectureMapper;
import dbapp.persistence.RegistrationMapper;

@Component
@Transactional
public class RegistrationService {
	@Autowired
	private RegistrationMapper registrationMapper;

	@Autowired
	private LectureMapper lectureMapper;

	public List<Integer> getYearsByStudent(String studentId) {
		return registrationMapper.getYearsByStudent(studentId);
	}

	public List<HashMap<String, String>> getAllByStudent(String studentId) {
		return registrationMapper.getAllByStudent(studentId);
	}

	public List<HashMap<String, String>> getAllTimesByStudent(String studentId) {

		final SimpleDateFormat transFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		List<LectureTime> times = registrationMapper
				.getAllTimeByStudent(studentId);

		for (int i = 0; i < times.size(); i++) {
			HashMap<String, String> row = new HashMap<String, String>();
			row.put("lectureId", Integer.toString(times.get(i).getLectureId()));
			row.put("period", Integer.toString(times.get(i).getPeriod()));
			if (times.get(i).getStartTime() != null) {
				row.put("startTime",
						transFormat.format(times.get(i).getStartTime()));
			}
			if (times.get(i).getEndTime() != null) {
				row.put("endTime",
						transFormat.format(times.get(i).getEndTime()));
			}
			result.add(row);
		}
		return result;
	}

	public Boolean register(String studentId, int lectureId) {
		List<HashMap<String, String>> registrations = registrationMapper
				.getAllByStudent(studentId);
		int year = lectureMapper.getYearByLectureId(lectureId);
		for (int i = 0; i < registrations.size(); i++) {
			int existedLectureId = Integer.parseInt(String
					.valueOf(registrations.get(i).get("lectureId")));
			if (lectureMapper.getYearByLectureId(existedLectureId) != year) {
				continue;
			}
			if (checkTimeConstraint(existedLectureId, lectureId) == false) {
				return false;
			}
		}
		registrationMapper.register(studentId, lectureId);
		return true;
	}

	public Boolean registerNumber(String studentId, int lectureNumber) {
		List<Integer> lectures = lectureMapper.getByYearNumber(2014,
				lectureNumber);
		if (lectures.size() != 1)
			return false;
		return register(studentId, lectures.get(0));
	}

	public Boolean checkTimeConstraint(int lectureIdA, int lectureIdB) {
		List<LectureTime> times = lectureMapper.getTimesByLectureId(lectureIdA);
		times.addAll(lectureMapper.getTimesByLectureId(lectureIdB));
		Collections.sort(times);
		for (int i = 1; i < times.size(); i++) {
			LectureTime a = times.get(i - 1);
			LectureTime b = times.get(i);
			if (a.getStartTime() == null || b.getStartTime() == null
					|| a.getEndTime() == null || b.getEndTime() == null) {
				continue;
			}
			if (a.getEndTime().compareTo(b.getStartTime()) == 1) {
				return false;
			}
		}
		return true;
	}

	public Boolean unregister(String studentId, int lectureId) {
		registrationMapper.unregister(studentId, lectureId);
		return true;
	}

	public Boolean clear(String studentId) {
		registrationMapper.clear(studentId, 2014);
		return true;
	}
}
