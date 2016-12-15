package com.springcookbook.entity;

import java.util.LinkedList;

public class User {
	private Long id;
    private String firstName;
    private LinkedList<Post> posts = new LinkedList<Post>();
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	private Integer age;


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
