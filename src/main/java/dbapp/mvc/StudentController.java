package dbapp.mvc;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dbapp.service.CatalogService;
import dbapp.service.RegistrationService;
import dbapp.service.StudentService;
import dbapp.domain.Major;

@RestController
public class StudentController {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private StudentService studentService;

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

	@RequestMapping("/registrations")
	public List<HashMap<String, String>> registrations(
			@RequestParam(value = "studentId", required = true) String studentId) {
		return registrationService.getAllByStudent(studentId);
	}

	@RequestMapping("/registrationTimes")
	public List<HashMap<String, String>> registrationTimes(
			@RequestParam(value = "studentId", required = true) String studentId) {
		return registrationService.getAllTimesByStudent(studentId);
	}

	@RequestMapping("/registrationYears")
	public List<Integer> registrationYears(
			@RequestParam(value = "studentId", required = true) String studentId) {
		return registrationService.getYearsByStudent(studentId);
	}

	@RequestMapping("/students")
	public List<HashMap<String, String>> students() {
		return studentService.getAll();
	}

	@RequestMapping("/unregister")
	public HashMap<String, String> unregister(
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "lectureId", required = true) int lectureId) {
		Boolean result = registrationService.unregister(studentId, lectureId);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}

	@RequestMapping("/register")
	public HashMap<String, String> register(
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "lectureId", required = true) int lectureId) {
		Boolean result = registrationService.register(studentId, lectureId);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}

	@RequestMapping("/registerNumber")
	public HashMap<String, String> registerNumber(
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "lectureNumber", required = true) int lectureNumber) {
		Boolean result = registrationService.registerNumber(studentId,
				lectureNumber);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}

	@RequestMapping("/clear")
	public HashMap<String, String> clear(
			@RequestParam(value = "studentId", required = true) String studentId) {
		Boolean result = registrationService.clear(studentId);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}
}
