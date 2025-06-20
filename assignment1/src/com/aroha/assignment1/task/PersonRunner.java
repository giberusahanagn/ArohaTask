package com.aroha.assignment1.task;

import java.util.*;
import java.util.stream.Collectors;

public class PersonRunner {
    public static void main(String[] args) {

        List<Person> list1 = new ArrayList<>();
        list1.add(new Person("Sahana", 24, "bansankri"));
        list1.add(new Person("bharath", 26, "whitefield"));
        list1.add(new Person("janu", 15, "bangalore"));
        list1.add(new Person("shreya", 24, "hebbagodi"));
        list1.add(new Person("rithvik", 4, "vinayaka nagar"));

        List<Person> list2 = new ArrayList<>();
        list2.add(new Person("Sahana", 24, "bansankri"));
        list2.add(new Person("manjula", 46, "whitefield"));
        list2.add(new Person("nagaraj", 55, "bangalore"));
        list2.add(new Person("shreya", 24, "hebbagodi"));
        list2.add(new Person("rithvik", 4, "vinayaka nagar"));

        // For-each loop – Only in list1
        List<Person> onlyInList1 = new ArrayList<>();
        for (Person p : list1) {
            if (!list2.contains(p)) {
                onlyInList1.add(p);
            }
        }
        System.out.println("Only in list1 (for-each):");
        onlyInList1.forEach(System.out::println);

        // Streams – Only in list1
        List<Person> onlyInList1Stream = list1.stream()
                .filter(p -> !list2.contains(p))
                .collect(Collectors.toList());
        System.out.println("\nOnly in list1 (stream):");
        onlyInList1Stream.forEach(System.out::println);

        //Common in both lists
        List<Person> commonInBoth = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
        System.out.println("\nCommon in both lists:");
        commonInBoth.forEach(System.out::println);
    }
}
