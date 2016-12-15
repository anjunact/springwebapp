package com.springcookbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springcookbook.service.UserService;

@Controller
public class HelloController {
	@Autowired
	UserService userService;
	@Autowired
    private ApplicationContext applicationContext;

	@RequestMapping("hi")
	@ResponseBody
	public String hi() {
		return "nb of users: " + userService.findNumberOfUsers();
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		 String[] beans =
			       applicationContext.getBeanDefinitionNames();
			       for (String bean : beans) {
			         System.out.println(bean);
			       }
		
		return "test";
	}
}