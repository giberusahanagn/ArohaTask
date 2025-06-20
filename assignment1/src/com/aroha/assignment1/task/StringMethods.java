package com.aroha.assignment1.task;

import java.util.Scanner;

public class StringMethods {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Taking input from user
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();  // e.g., Java Development

        System.out.println("\n--- String Method Examples ---");

        // length()
        System.out.println("Length: " + input.length());

        // charAt()
        if (input.length() > 5) {
            System.out.println("Character at index 5: " + input.charAt(5));
        }

        // substring()
        if (input.length() >= 4) {
            System.out.println("Substring from index 4: " + input.substring(4));
        }

        // toUpperCase and toLowerCase
        System.out.println("To Upper Case: " + input.toUpperCase());
        System.out.println("To Lower Case: " + input.toLowerCase());

        // indexOf()
        System.out.println("Index of 'p': " + input.indexOf('p'));

        // contains()
        System.out.println("Contains 'Java': " + input.contains("java"));

        // trim()
        System.out.println("Trimmed: '" + input.trim() + "'");

        // replace
        System.out.println("Replace 'Java' with 'Spring': " + input.replace("java", "spring"));

        // split()
        String[] parts = input.split(" ");
        System.out.println("Split words:");
        for (String part : parts) {
            System.out.println("- " + part);
        }

        // isEmpty()
        System.out.println("Is empty? " + input.isEmpty());

        // concat
        System.out.println("Concatenated with ' Language': " + input.concat(" Language"));

        // valueOf()
        int year = 2025;
        System.out.println("String.valueOf(year): " + String.valueOf(year));

        System.out.println(input.isBlank()); 

        scanner.close();
    }
}
