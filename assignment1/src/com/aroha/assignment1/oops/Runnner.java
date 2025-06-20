package com.aroha.assignment1.oops;

public class Runnner {
	public static void main(String[] args) {
		// Runtime Polymorphism

		String devName = "Alice";
		String testName = "bob";
		int age = 30;
		Employee emp1 = new Developer("Alice", 30);
		Employee emp2 = new Tester("Bob", 28);
		emp1.work(devName, age);
		emp2.work(testName, age);

		// Compile-time Polymorphism
		Calculator calc = new Calculator();
		System.out.println("Sum 2 int: " + calc.add(10, 20));
		System.out.println("Sum 2 double: " + calc.add(10.5, 20.5));
		System.out.println("Sum 3 int: " + calc.add(1, 2, 3));
	}
}
