package com.aixs.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aixs.crud.bean.Department;
import com.aixs.crud.dao.DepartmentMapper;




@Service
public class DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;
	public List<Department> getDepts() {
		
		List<Department> list=departmentMapper.selectByExample(null);
		return list;
	}

}
