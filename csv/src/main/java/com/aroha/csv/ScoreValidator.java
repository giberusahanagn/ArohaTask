package com.aroha.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreValidator {

	public static void main(String[] args) {
		// Scanner for taking input from user
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter inputs (numbers or c/C/d/D or +  ):");
		
		// Splitting input based on whitespace
		String[] inputs = sc.nextLine().trim().split("\\s+");

		// List to store valid numbers
		List<Integer> validNumbers = new ArrayList<>();
		int totalScore = 0;

		// taking each input
		for (String value : inputs) {
			// Check if the input is a valid number (up to 3 digits)
			if (value.matches("-?\\d{1,3}")) {
				int num = Integer.parseInt(value);
				
				// Validate range: -999 to 999
				if (num >= -999 && num <= 999) {
					validNumbers.add(num);      // Adding valid number to list
					totalScore += num;          // Updated total score
					
					System.out.println("input is : " +value +" the totalScore is :" +totalScore);
				} else {
					System.out.println("Given input is not valid. Number should be between -999 and 999.");
				}
				
			//  "c" or "C": Cancel last value
			} else if (value.equalsIgnoreCase("c")) {
				if (!validNumbers.isEmpty()) {
					int removed = validNumbers.remove(validNumbers.size() - 1);
					totalScore -= removed;
					System.out.println("Ignored the previous value." + value);
					System.out.println("input is C totalScore " +totalScore);

				} else {
					System.out.println("Unable to ignore. No previous value found."+value);
				}
				
			//  "d" or "D": Double last valid number
			} else if (value.equalsIgnoreCase("d")) {
				if (!validNumbers.isEmpty()) {
					int last = validNumbers.get(validNumbers.size() - 1);
					int doubled = 2 * last;
					validNumbers.add(doubled);  
					totalScore += doubled;
					System.out.println("Doubled the previous value.");
					System.out.println(" input is d totalScore" +totalScore);

				} else {
					System.out.println("Unable to double. No previous value found."+value);
				}
				
			//  "+": Add last two numbers
			} else if (value.equals("+")) {
				if (validNumbers.size() >= 2) {
					int sumLastTwo = validNumbers.get(validNumbers.size() - 1)
							+ validNumbers.get(validNumbers.size() - 2);
					totalScore += sumLastTwo;
					System.out.println("Added last two values.");
					System.out.println("totalScore" +totalScore);

				} else {
					System.out.println("Not enough values to perform '+' operation.");
				}
			}
			else {
			    System.out.println("Invalid input :" + value );
			}
		}

		// Output final score
		System.out.println("\nTotalScore = " + totalScore);
	}
}
