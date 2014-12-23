package dbapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dbapp.persistence.StatisticsMapper;

import java.util.HashMap;
import java.util.List;

@Component
public class StatisticsService {

	@Autowired
	private StatisticsMapper statisticsMapper;

	public List<HashMap<String, String>> statistics1(int year) {
		if (year != 0) {
			return statisticsMapper.getSubjectStatisticsByYear(year);
		}
		return statisticsMapper.getSubjectStatistics();
	}

	public List<HashMap<String, String>> statistics2(int year) {
		if (year != 0) {
			return statisticsMapper.getMajorStatisticsByYear(year);
		}
		return statisticsMapper.getMajorStatistics();
	}

	public List<HashMap<String, String>> statistics3(int year) {
		if (year != 0) {
			return statisticsMapper.getStatisticsByYear(year);
		}
		return statisticsMapper.getStatistics();
	}
}
