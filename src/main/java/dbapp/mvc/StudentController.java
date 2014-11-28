package dbapp.mvc;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dbapp.service.CatalogService;
import dbapp.domain.Major;

@RestController
public class StudentController {

	@Autowired
	private CatalogService catalogService;

	@RequestMapping("/majors")
	public List<Major> getMajors() {
		return catalogService.getAllMajors();
	}

	@RequestMapping("/years")
	public List<Integer> getYears() {
		return catalogService.getAllYears();
	}

	@RequestMapping("/searchCatalog")
	public HashMap<String, List<HashMap<String, String>>> search(
			@RequestParam(value = "majorId", required = true) int majorId,
			@RequestParam(value = "year", required = true) int year,
			@RequestParam(value = "schoolYear", required = true) int schoolYear,
			@RequestParam(value = "lectureNumber", required = false) String lectureNumber,
			@RequestParam(value = "subjectId", required = false) String subjectId,
			@RequestParam(value = "subjectTitle", required = false) String subjectTitle,
			@RequestParam(value = "instructorName", required = false) String instructorName) {
		return catalogService.search(majorId, year, schoolYear, lectureNumber,
				subjectId, subjectTitle, instructorName);
	}
}
