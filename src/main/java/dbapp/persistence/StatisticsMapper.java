package dbapp.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface StatisticsMapper {
	@Select("SELECT subject.title, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "GROUP BY subject.subjectId\n" + "ORDER BY subject.title")
	public List<HashMap<String, String>> getSubjectStatistics();

	@Select("SELECT subject.title, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "WHERE lecture.year = #{0}\n" + "GROUP BY subject.subjectId\n"
			+ "ORDER BY subject.title")
	public List<HashMap<String, String>> getSubjectStatisticsByYear(int year);

	@Select("SELECT major.name, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN major ON major.majorId = lecture.majorId\n"
			+ "GROUP BY lecture.majorId\n" + "ORDER BY major.name")
	public List<HashMap<String, String>> getMajorStatistics();

	@Select("SELECT major.name, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN major ON major.majorId = lecture.majorId\n"
			+ "WHERE lecture.year = #{0}\n" + "GROUP BY lecture.majorId\n"
			+ "ORDER BY major.name")
	public List<HashMap<String, String>> getMajorStatisticsByYear(int year);

	@Select("SELECT major.name, subject.title, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "INNER JOIN major ON major.majorId = lecture.majorId\n"
			+ "GROUP BY major.majorId, subject.subjectId\n"
			+ "ORDER BY major.name, subject.title")
	public List<HashMap<String, String>> getStatistics();

	@Select("SELECT major.name, subject.title, COUNT(*) AS `count`\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "INNER JOIN major ON major.majorId = lecture.majorId\n"
			+ "WHERE lecture.year = #{0}\n"
			+ "GROUP BY major.majorId, subject.subjectId\n"
			+ "ORDER BY major.name, subject.title")
	public List<HashMap<String, String>> getStatisticsByYear(int year);
}
