package dbapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dbapp.persistence.UserMapper;

@Component
@Transactional
public class StudentService {
	@Autowired
	private UserMapper userMapper;

	public List<HashMap<String, String>> getAll() {
		return userMapper.getAll();
	}

	public HashMap<String, String> getByStudentId(String studentId) {
		return userMapper.getByStudentId(studentId);
	}
}
