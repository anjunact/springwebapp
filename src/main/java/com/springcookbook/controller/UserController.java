package com.springcookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/list")
	public String userList(Model model) {
		model.addAttribute("nbUsers", 13);
		return "user/list";
	}
	 @RequestMapping("/add")
     public void addUser() {
		 
	 }
}
