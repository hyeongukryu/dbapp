package dbapp.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import dbapp.domain.LectureTime;

public interface LectureMapper {
	@Select("SELECT DISTINCT year FROM lecture ORDER BY year ASC")
	public List<Integer> getAllYears();

	@Select("SELECT lecture.lectureId AS lectureId, schoolYear, number AS lectureNumber, subject.subjectId, subject.title AS subjectTitle, instructor.name AS instructorName, credit, lecture.`limit`, building.name AS buildingName, COUNT(registration.studentId) AS `registrations`\n"
			+ "FROM lecture\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "INNER JOIN instructor ON instructor.instructorId = lecture.instructorId\n"
			+ "LEFT OUTER JOIN room ON room.roomId = lecture.roomId\n"
			+ "LEFT OUTER JOIN building ON building.buildingId = room.buildingId\n"
			+ "LEFT OUTER JOIN registration ON registration.lectureId = lecture.lectureId\n"
			+ "WHERE schoolYear = #{2} AND year = #{1} AND lecture.majorId = #{0}\n"
			+ "AND (#{3} IS NULL OR lecture.number = #{3})\n"
			+ "AND (#{4} IS NULL OR subject.subjectId = #{4})\n"
			+ "AND (#{5} IS NULL OR subject.title LIKE CONCAT('%', #{5}, '%'))\n"
			+ "AND (#{6} IS NULL OR instructor.name LIKE CONCAT(#{6}, '%'))\n"
			+ "GROUP BY lecture.lectureId")
	public List<HashMap<String, String>> searchCatalog(int majorId, int year,
			int schoolYear, String lectureNumber, String subjectId,
			String subjectTitle, String instructorName);

	@Select("SELECT lecture.lectureId AS lectureId, period, startTime, endTime\n"
			+ "FROM lecture\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "INNER JOIN instructor ON instructor.instructorId = lecture.instructorId\n"
			+ "LEFT OUTER JOIN lecture_time ON lecture_time.lectureId = lecture.lectureId\n"
			+ "WHERE schoolYear = #{2} AND year = #{1} AND lecture.majorId = #{0}\n"
			+ "AND (#{3} IS NULL OR lecture.number = #{3})\n"
			+ "AND (#{4} IS NULL OR subject.subjectId = #{4})\n"
			+ "AND (#{5} IS NULL OR subject.title LIKE CONCAT('%', #{5}, '%'))\n"
			+ "AND (#{6} IS NULL OR instructor.name LIKE CONCAT(#{6}, '%'))")
	public List<LectureTime> searchCatalogTime(int majorId, int year,
			int schoolYear, String lectureNumber, String subjectId,
			String subjectTitle, String instructorName);
	
	@Select("SELECT year FROM lecture WHERE lectureId = #{0} LIMIT 1")
	public int getYearByLectureId(int lectureId);
	
	@Select("SELECT lectureId FROM lecture WHERE year = #{0} AND number = #{1}")
	public List<Integer> getByYearNumber(int year, int lectureNumber);

	@Select("SELECT lecture.lectureId, period, startTime, endTime FROM lecture\n"
			+ "LEFT OUTER JOIN lecture_time ON lecture_time.lectureId = lecture.lectureId\n"
			+ "WHERE lecture.lectureId = #{0}")
	public List<LectureTime> getTimesByLectureId(int lectureId);
}
