package com.aroha.assignment1.task;

public class Conversion {
public static void main(String[] args) {

			System.out.println("\n--- Type Conversion Demo ---");

			// Wrapper to String
			Long l = 123456789L;
			Float f = 12.34f;
			Double d = 56.78;
			Integer i = 100;

			String s1 = l.toString();
			String s2 = f.toString();
			String s3 = d.toString();
			String s4 = i.toString();

			System.out.println("Long to String: " + s1);
			System.out.println("Float to String: " + s2);
			System.out.println("Double to String: " + s3);
			System.out.println("Integer to String: " + s4);

			// String to Wrapper
			Long l2 = Long.parseLong(s1);
			Float f2 = Float.parseFloat(s2);
			Double d2 = Double.parseDouble(s3);
			Integer i2 = Integer.parseInt(s4);

			System.out.println("String to Long: " + l2);
			System.out.println("String to Float: " + f2);
			System.out.println("String to Double: " + d2);
			System.out.println("String to Integer: " + i2);
}
}
