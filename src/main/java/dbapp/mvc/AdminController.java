package dbapp.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dbapp.service.CatalogService;
import dbapp.service.StatisticsService;

import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {
	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private CatalogService catalogService;

	@RequestMapping("/statistics1")
	public List<HashMap<String, String>> statistics1(
			@RequestParam(value = "year", required = false) Integer year) {
		return statisticsService.statistics1(year == null ? 0 : year);
	}

	@RequestMapping("/statistics2")
	public List<HashMap<String, String>> statistics2(
			@RequestParam(value = "year", required = false) Integer year) {
		return statisticsService.statistics2(year == null ? 0 : year);
	}

	@RequestMapping("/statistics3")
	public List<HashMap<String, String>> statistics3(
			@RequestParam(value = "year", required = false) Integer year) {
		return statisticsService.statistics3(year == null ? 0 : year);
	}

	@RequestMapping("/drop")
	public HashMap<String, String> drop(
			@RequestParam(value = "lectureId", required = true) int lectureId) {
		Boolean result = catalogService.drop(lectureId);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}

	@RequestMapping("/create")
	public HashMap<String, String> drop(
			@RequestParam(value = "majorId", required = true) int majorId,
			@RequestParam(value = "year", required = true) int year,
			@RequestParam(value = "schoolYear", required = true) int schoolYear,
			@RequestParam(value = "lectureNumber", required = true) String lectureNumber,
			@RequestParam(value = "subjectId", required = true) String subjectId,
			@RequestParam(value = "instructorName", required = true) String instructorName) {
		Boolean result = catalogService.create(majorId, year, schoolYear, lectureNumber, subjectId, instructorName);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("result", result ? "success" : "error");
		return response;
	}
}
