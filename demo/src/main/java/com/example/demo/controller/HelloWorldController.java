package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.User;

@RestController
public class HelloWorldController {

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@RequestMapping("/index")
	public Object index() {
		User user = new User(1,"张三",23);
		return user;
	}
	
}
