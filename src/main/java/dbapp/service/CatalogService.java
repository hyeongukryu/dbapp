package dbapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dbapp.domain.LectureTime;
import dbapp.domain.Major;
import dbapp.persistence.LectureMapper;
import dbapp.persistence.MajorMapper;

@Component
public class CatalogService {
	@Autowired
	private MajorMapper majorMapper;

	@Autowired
	private LectureMapper lectureMapper;

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
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int i = 0; i < lt.size(); i++) {
			HashMap<String, String> row = new HashMap<String, String>();
			row.put("lectureId", Integer.toString(lt.get(i).getLectureId()));
			row.put("period", Integer.toString(lt.get(i).getPeriod()));
			if (lt.get(i).getStartTime() != null) {
				row.put("startTime", transFormat.format(lt.get(i).getStartTime()));
			}
			if (lt.get(i).getEndTime() != null) {
				row.put("endTime", transFormat.format(lt.get(i).getEndTime()));
			}
			timeTable.add(row);
		}
		result.put("timeTable", timeTable);
		return result;
	}

}
