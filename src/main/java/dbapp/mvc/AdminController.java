package dbapp.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dbapp.service.StatisticsService;

import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {
	@Autowired
	private StatisticsService statisticsService;

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
}
