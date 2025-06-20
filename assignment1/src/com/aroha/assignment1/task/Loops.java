package com.aroha.assignment1.task;

public class Loops {

	public static void main(String[] args) {
        int number = 7;

        // if-else-if
        if (number > 10) {
            System.out.println("Number is greater than 10");
        } else if (number == 10) {
            System.out.println("Number is exactly 10");
        } else {
            System.out.println("Number is less than 10");
        }

        // for loop
        System.out.println("For loop from 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // while loop
        System.out.println("While loop:");
        int j = 0;
        while (j < 3) {
            System.out.println("j = " + j);
            j++;
        }

        // do-while loop
        System.out.println("Do-while loop:");
        int k = 0;
        do {
            System.out.println("k = " + k);
            k++;
        } while (k < 2);
    }
}
