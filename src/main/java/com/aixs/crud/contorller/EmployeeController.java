package com.aixs.crud.contorller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aixs.crud.bean.Employee;
import com.aixs.crud.bean.Msg;
import com.aixs.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工请求
 * @author AixsCode
 *
 */
@Controller
public class EmployeeController {
    
	@Autowired
	private EmployeeService employeeService;
	
	
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids)
	{
		//批量删除
		if(ids.contains("-"))
		{
			
			List<Integer> del_ids=new ArrayList<Integer>();
			
			String[] str_ids=ids.split("-");
			//组装id的集合
			for(String string : str_ids)
			{
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		}
		else {
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee)
	{
		employeeService.update(employee);
		return Msg.success();
		
	}
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id")Integer id)
	{
		Employee employee =employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg Checkuser(@RequestParam("empName") String empName)
	{
		//先判断用户名是否是合法的表达式
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx))
		{
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");	
		}
		boolean b=employeeService.checkUser(empName);
		if(b)
		{
			return Msg.success();
		}
		else
		{
			return Msg.fail().add("va_msg", "用户名不可用");
		}	
	}
	/**
	 * 
	 * 员工保存
	 * @return
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result)
	{   
		if(result.hasErrors())
		{
			Map<String, Object> map=new HashMap<String, Object>();
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors)
			{
				System.out.println("错误的字段名"+fieldError.getField());
				System.out.println("错误的信息"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail();
		}
		else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	/**
	 * 返回json字符串
	 * responseBody 可以自动将对象返回为json字符串
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn)
	{
		PageHelper.startPage(pn, 5);
		
		List<Employee> emps=employeeService.getAll();
		
		PageInfo page=new PageInfo(emps,5);
		
		return Msg.success().add("pageInfo", page);
	}
	
	/**
	 * 
	 * 查询员工数据
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,Model model)
	{
		//分页查询
		//查询之前需要调入页码，以及每页的大小
		PageHelper.startPage(pn, 5);
		List<Employee> emps=employeeService.getAll();
		//pageInfo包装查询后的结果
		//封装了详细的分页信息
		PageInfo page=new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	
}
