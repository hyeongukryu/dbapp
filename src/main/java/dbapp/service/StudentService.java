package dbapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dbapp.persistence.StudentMapper;

@Component
@Transactional
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;

	public List<HashMap<String, String>> getAll() {
		return studentMapper.getAll();
	}

	public HashMap<String, String> getByStudentId(String studentId) {
		return studentMapper.getByStudentId(studentId);
	}
}
