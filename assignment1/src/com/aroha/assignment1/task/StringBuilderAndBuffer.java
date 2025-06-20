package com.aroha.assignment1.task;

public class StringBuilderAndBuffer {

	public static void main(String[] args) {

		System.out.println("--- StringBuffer Demo ---");
		StringBuffer sbf = new StringBuffer("Hello");
		sbf.append(" World");
		System.out.println("Append: " + sbf); // Hello World

		sbf.insert(5, " Java");
		System.out.println("Insert: " + sbf); // Hello Java World

		sbf.replace(6, 10, "Spring");
		System.out.println("Replace: " + sbf); // Hello Spring World

		sbf.delete(5, 12);
		System.out.println("Delete: " + sbf); // Hello World

		sbf.reverse();
		System.out.println("Reverse: " + sbf); // dlroW olleH

		System.out.println("----------String Builder---------------");
		System.out.println("\n--- StringBuilder Demo ---");
		StringBuilder sbd = new StringBuilder("Spring");

		sbd.append("Boot");
		System.out.println("Append: " + sbd);

		sbd.insert(9, " Java");
		System.out.println("Insert: " + sbd);

		sbd.replace(0, 9, "Engineer");
		System.out.println("Replace: " + sbd);

		sbd.delete(8, 13);
		System.out.println("Delete: " + sbd);

		sbd.reverse();
		System.out.println("Reverse: " + sbd);

	}
}
