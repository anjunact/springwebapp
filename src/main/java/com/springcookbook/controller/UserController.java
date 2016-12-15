package com.springcookbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springcookbook.dao.UserDAO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping("/list")
	public String userList(Model model) {
		model.addAttribute("nbUsers", 13);
		return "user/list";
	}

	@RequestMapping("/add")
	public void addUser() {

	}
}
