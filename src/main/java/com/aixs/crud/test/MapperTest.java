package com.aixs.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aixs.crud.bean.Department;
import com.aixs.crud.bean.Employee;
import com.aixs.crud.dao.DepartmentMapper;
import com.aixs.crud.dao.EmployeeMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
     
	
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD()
	{
		
		System.out.println(departmentMapper);
		//1、插入几个部门
	    //departmentMapper.insertSelective(new Department(null, "开发部"));
		//departmentMapper.insertSelective(new Department(null, "测试部"));
	    //employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@atguigu.com", 1));
		
		EmployeeMapper mapper= sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) 
		{
			String uid=UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null, uid, "M",uid+"@atguigu.com", 1));
		}
		
		System.out.println("批量完成");
		
	}
	
}
