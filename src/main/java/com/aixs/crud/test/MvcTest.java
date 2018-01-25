package com.aixs.crud.test;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.aixs.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

/**
 * 测试CRUD请求的正确性
 * @author AixsCode
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
       
	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@org.junit.Before
	public void initMockMvc()
	{
		
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception
	{
		//模拟请求拿到返回值
		 MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		
		//请求域中会有pageInfo  
		
		 MockHttpServletRequest request=result.getRequest();
		
		 PageInfo pi= (PageInfo) request.getAttribute("pagInfo");
		 
		 System.out.println("当前页码"+pi.getPageNum());
		 System.out.println("总页码"+pi.getPages());
		 System.out.println("总记录数"+pi.getTotal());
		 
		 //获取员工数据
		 List<Employee> list=pi.getList();
		 for(Employee employee:list)
		 {
			 
			 System.out.println("ID:"+employee.getdId()+"name"+employee.getEmpName());
		 }
	}
}
