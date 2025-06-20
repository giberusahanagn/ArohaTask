package com.aroha.assignment1.oops;

public class Tester implements Employee {
	private String name;
	private int age;

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

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public Tester(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public void work(String name, int age) {
		System.out.println(getName() + " tests code.");
	}

}
