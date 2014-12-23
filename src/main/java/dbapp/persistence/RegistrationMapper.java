package dbapp.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import dbapp.domain.LectureTime;

public interface RegistrationMapper {
	@Select("SELECT registration.lectureId, lecture.year AS year, registration.grade AS grade, lecture.number AS lectureNumber,\n"
			+ "subject.title AS subjectTitle, subject.credit, major.name AS majorName, instructor.name AS instructorName,\n"
			+ "building.name AS buildingName, lecture.schoolYear AS schoolYear, subject.subjectId\n"
			+ "FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "INNER JOIN subject ON subject.subjectId = lecture.subjectId\n"
			+ "INNER JOIN major ON major.majorId = lecture.majorId\n"
			+ "INNER JOIN instructor ON lecture.instructorId = instructor.instructorId\n"
			+ "LEFT OUTER JOIN room ON lecture.roomId = room.roomId\n"
			+ "LEFT OUTER JOIN building ON building.buildingId = room.buildingId\n"
			+ "WHERE registration.studentId = #{0}")
	public List<HashMap<String, String>> getAllByStudent(String studentId);

	@Delete("INSERT INTO registration SET studentId = #{0}, lectureId = #{1}")
	public void register(String studentId, int lectureId);

	@Delete("DELETE FROM registration WHERE studentId = #{0} AND lectureId = #{1}")
	public void unregister(String studentId, int lectureId);

	@Select("SELECT DISTINCT year FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "WHERE registration.studentId = #{0}\n" + "ORDER BY year DESC")
	public List<Integer> getYearsByStudent(String studentId);

	@Select("SELECT registration.lectureId AS lectureId, period, startTime, endTime\n"
			+ "FROM registration\n"
			+ "LEFT OUTER JOIN lecture_time ON lecture_time.lectureId = registration.lectureId\n"
			+ "WHERE studentId = #{0}")
	public List<LectureTime> getAllTimeByStudent(String studentId);

	@Delete("DELETE registration FROM registration\n"
			+ "INNER JOIN lecture ON lecture.lectureId = registration.lectureId\n"
			+ "WHERE lecture.year = #{1} AND registration.studentId = #{0}")
	public void clear(String studentId, int year);
}
