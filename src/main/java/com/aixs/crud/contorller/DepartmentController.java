package com.aixs.crud.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aixs.crud.bean.Department;
import com.aixs.crud.bean.Msg;
import com.aixs.crud.service.DepartmentService;

/**
 * 处理和部门有关的请求
 * @author AixsCode
 *
 */
@Controller
public class DepartmentController {

	
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 返回所有部门信息
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts()
	{
		List<Department> list =departmentService.getDepts();
		return Msg.success().add("depts", list);
	}
}
