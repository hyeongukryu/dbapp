package dbapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;

import dbapp.domain.LectureTime;
import dbapp.domain.Major;
import dbapp.persistence.LectureMapper;
import dbapp.persistence.MajorMapper;
import dbapp.persistence.UserMapper;

@Component
@Transactional
public class CatalogService {
	@Autowired
	private MajorMapper majorMapper;

	@Autowired
	private LectureMapper lectureMapper;
	
	@Autowired
	private UserMapper userMapper;

	public List<Major> getAllMajors() {
		return majorMapper.getAll();
	}

	public List<Integer> getAllYears() {
		return lectureMapper.getAllYears();
	}

	public HashMap<String, List<HashMap<String, String>>> search(int majorId,
			int year, int schoolYear, String lectureNumber, String subjectId,
			String subjectTitle, String instructorName) {
		HashMap<String, List<HashMap<String, String>>> result = new HashMap<String, List<HashMap<String, String>>>();
		result.put("lectures", lectureMapper.searchCatalog(majorId, year,
				schoolYear, lectureNumber, subjectId, subjectTitle,
				instructorName));
		List<LectureTime> lt = lectureMapper.searchCatalogTime(majorId, year,
				schoolYear, lectureNumber, subjectId, subjectTitle,
				instructorName);
		List<HashMap<String, String>> timeTable = new ArrayList<HashMap<String, String>>();

		final SimpleDateFormat transFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < lt.size(); i++) {
			HashMap<String, String> row = new HashMap<String, String>();
			row.put("lectureId", Integer.toString(lt.get(i).getLectureId()));
			row.put("period", Integer.toString(lt.get(i).getPeriod()));
			if (lt.get(i).getStartTime() != null) {
				row.put("startTime",
						transFormat.format(lt.get(i).getStartTime()));
			}
			if (lt.get(i).getEndTime() != null) {
				row.put("endTime", transFormat.format(lt.get(i).getEndTime()));
			}
			timeTable.add(row);
		}
		result.put("timeTable", timeTable);
		return result;
	}

	public Boolean drop(int lectureId) {
		try {
			lectureMapper.deleteLectureTime(lectureId);
			return lectureMapper.deleteLecture(lectureId) > 0;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
	}

	public Boolean create(int majorId, int year, int schoolYear,
			String lectureNumber, String subjectId, String instructorName) {
		try {
			final Integer roomId = null;
			final int limit = 40;
			
			String instructorId = userMapper.getInstructorByName(instructorName);
			return lectureMapper.createLecture(lectureNumber, subjectId,
					majorId, schoolYear, instructorId, limit, roomId, year) > 0;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
	}
}
