package com.aroha.assignment1.task;

import java.util.regex.Pattern;

public class RegularExpression {
    public static void main(String[] args) {

        System.out.println("---- Character Classes ----");
        System.out.println(Pattern.matches("[abc]", "a"));        // true
        System.out.println(Pattern.matches("[^abc]", "d"));       // true
        System.out.println(Pattern.matches("[a-zA-Z]", "^"));     // false
        System.out.println(Pattern.matches("[a-d[m-p]]", "Q"));   // false (union)
        System.out.println(Pattern.matches("[a-z&&[def]]", "e")); // true (intersection)
        System.out.println(Pattern.matches("[a-z&&[^bc]]", "a")); // true (subtraction)
        System.out.println(Pattern.matches("[a-z&&[^m-p]]", "o"));// false (excluded)

        System.out.println("\n---- Quantifiers ----");
        System.out.println(Pattern.matches("a?", "a"));       // true (0 or 1 'a')
        System.out.println(Pattern.matches("a+", "aaaa"));    // true (1 or more 'a')
        System.out.println(Pattern.matches("[0-9]*", "767887"));        // true (0 or more 'a')
        System.out.println(Pattern.matches("a{3}", "aaa"));   // true (exactly 3)
        System.out.println(Pattern.matches("[a-z]{2,}", "sahanan")); // true (2 or more)
        System.out.println(Pattern.matches("[A-Z]{2,4}", "Aa"));  // true (between 2–4)

        System.out.println("\n---- Predefined Character Classes ----");
        System.out.println(Pattern.matches("\\d", "7"));        // true
        System.out.println(Pattern.matches("\\D", "S"));        // true
        System.out.println(Pattern.matches("\\s", "\n"));       // true
        System.out.println(Pattern.matches("\\S", "x"));        // true
        System.out.println(Pattern.matches("\\w", "5"));        // true
        System.out.println(Pattern.matches("\\W", "#"));        // true

        System.out.println("\n---- Anchors and Boundaries ----");
        System.out.println(Pattern.matches("^Java$", "Java"));      // true (exact match)
        System.out.println(Pattern.matches("^J.*a$", "Ja6987^&va"));      // true (starts J, ends a)
        System.out.println(Pattern.matches("\\bJava\\b", "Javattt"));  // true (word boundary)

        System.out.println("\n---- Dot and Escapes ----");
        System.out.println(Pattern.matches("a.{3,10}b", "a**hggjrh8778**b"));     // true (any char in between)
        System.out.println(Pattern.matches("a\\.b", "a.b"));   // true (escaped dot)
        System.out.println(Pattern.matches("1\\+1=2", "1+1=2"));// true (escaped plus)
        System.out.println(Pattern.matches("1.1=2", "181=j"));// false (escaped plus)

        System.out.println("\n---- Complex Examples ----");
        // Indian mobile number
        System.out.println(Pattern.matches("[789]\\d{9}", "9876543210")); // true

        // Email-like pattern
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{2,6}";
        System.out.println(Pattern.matches(emailPattern, "sahana%gn275+%@gmail.com")); // true

        // Alphanumeric with underscores only
        System.out.println(Pattern.matches("\\w+", "User123")); // true

        //or entreing gender
        System.out.println(Pattern.matches("^(?i)(male|female|m|f)$", "female"));
        System.out.println(Pattern.matches("\\s*", "\n\t")); // true
    }
    
    
   
}
