package com.aroha.assignment1.oops;

public class Developer implements Employee {

	private String name;
	private	int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Developer() {
		// TODO Auto-generated constructor stub
	}
	
	public Developer(String name, int age) {
		this.name=name;
		this.age=age;
	}
	@Override
	public void work(String name,int age) {
		System.out.println(getName() + " writes code.");
		System.out.println(getAge());
	}

	
	@Override
	public String toString() {
		return "Developer [name=" + name + ", age=" + age + "]";
	}
}