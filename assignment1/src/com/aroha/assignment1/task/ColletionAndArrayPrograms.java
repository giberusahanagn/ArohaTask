package com.aroha.assignment1.task;

import java.util.*;

public class ColletionAndArrayPrograms {
    public static void main(String[] args) {
        // 1. ArrayList Example
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Delhi");
        cities.add("Mumbai");
        cities.add("Bangalore");
        cities.set(1, "Chennai"); // replace Mumbai
        System.out.println("ArrayList: " + cities);
        System.out.println("Contains Bangalore? " + cities.contains("Bangalore"));
        cities.remove("Delhi");
        System.out.println("ArrayList after removal: " + cities);
        System.out.println("ArrayList size: " + cities.size());

        // 2. Arrays Example
        int[] ids = {105, 102, 110, 101};
        Arrays.sort(ids);
        System.out.println("Sorted Array: " + Arrays.toString(ids));
        int index = Arrays.binarySearch(ids, 105);
        System.out.println("Index of 105: " + index);
        int[] copy = Arrays.copyOf(ids, ids.length);
        System.out.println("Array equals copy? " + Arrays.equals(ids, copy));

        // 3. Set Example
        Set<String> departments = new HashSet<>();
        departments.add("HR");
        departments.add("Finance");
        departments.add("IT");
        departments.add("Finance"); // duplicate, ignored
        System.out.println("Set: " + departments);
        System.out.println("Set contains IT? " + departments.contains("IT"));
        departments.remove("HR");
        System.out.println("Set after removal: " + departments);
        System.out.println("Set size: " + departments.size());

        // 4. Map Example
        Map<Integer, String> employeeMap = new HashMap<>();
        employeeMap.put(101, "Sahana");
        employeeMap.put(102, "Bharath");
        employeeMap.put(103, "janu");
        System.out.println("Map: " + employeeMap);
        System.out.println("Get employee 102: " + employeeMap.get(102));
        employeeMap.remove(103);
        System.out.println("Map after removal: " + employeeMap);
        System.out.println("Contains key 101? " + employeeMap.containsKey(101));
        System.out.println("All values: " + employeeMap.values());
        System.out.println("Map size: " + employeeMap.size());
    }
}
