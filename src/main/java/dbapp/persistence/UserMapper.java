package dbapp.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Select("SELECT studentId AS id, student.name, gender, student.instructorId, student.schoolYear, instructor.name, major.name\n"
			+ "FROM student\n"
			+ "INNER JOIN major ON major.majorId = student.majorId\n"
			+ "INNER JOIN instructor ON instructor.instructorId = student.instructorId\n")
	public List<HashMap<String, String>> getAll();

	@Select("SELECT studentId AS id, student.name, gender, student.instructorId, student.schoolYear, instructor.name, major.name\n"
			+ "FROM student\n"
			+ "INNER JOIN major ON major.majorId = student.majorId\n"
			+ "INNER JOIN instructor ON instructor.instructorId = student.instructorId\n"
			+ "WHERE student.studentId = #{0}\n")
	public HashMap<String, String> getByStudentId(String studentId);

	@Select("SELECT instructorId FROM instructor WHERE name = #{0} LIMIT 1")
	public String getInstructorByName(String instructorName);
}
