package com.aroha.assignment1.task;

import java.util.Objects;

public class Person {
	private String name;
	private int age;
	private String city;

	public Person(String name, int age, String city) {
		this.name = name;
		this.age = age;
		this.city = city;
	}

	// Getters (optional if needed)
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getCity() {
		return city;
	}

	// Override equals() and hashCode() for correct comparison
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Person))
			return false;
		Person person = (Person) o;
		return age == person.age && name.equals(person.name) && city.equals(person.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age, city);
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", city=" + city + "]";
	}
}
