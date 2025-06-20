package com.aroha.csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("CSV Validation started...");
        long startTime = System.currentTimeMillis();

        String inputFile = "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Member_Data.csv";
        String validFile = "ValidatedMembers.csv";
        String invalidFile = "FailedRecords.csv";

        try (
            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter validWriter = new CSVWriter(new FileWriter(validFile));
            CSVWriter invalidWriter = new CSVWriter(new FileWriter(invalidFile))
        ) {
            String[] header = reader.readNext();
            validWriter.writeNext(header);

            // For invalid file, add Error column
            String[] invalidHeader = Arrays.copyOf(header, header.length + 1);
            invalidHeader[header.length] = "Error";
            invalidWriter.writeNext(invalidHeader);

            Set<String> uniqueSet = new HashSet<>();
            DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            };

            int validCount = 0, invalidCount = 0;
            int rowNumber = 1; // Excludes header

            String[] row;
            while ((row = reader.readNext()) != null) {
                boolean valid = true;
                StringBuilder error = new StringBuilder();

                // 1. All fields mandatory
                for (String field : row) {
                    if (field == null || field.trim().isEmpty()) {
                        valid = false;
                        error.append("Empty field; ");
                        break;
                    }
                }

                // 2. Uniqueness
                String uniqueKey = row[1] + "|" + row[2] + "|" + row[4] + "|" + row[3];
                if (!uniqueSet.add(uniqueKey)) {
                    valid = false;
                    error.append("Duplicate record; ");
                }

                // 3. Address - remove special chars
                row[7] = row[7].replaceAll("[^a-zA-Z0-9 ]", "");
                row[8] = row[8].replaceAll("[^a-zA-Z0-9 ]", "");

                // 4. Mobile number
                if (!row[11].matches("^[789]\\d{9}$")) {
                    valid = false;
                    error.append("Invalid mobile; ");
                }

                // 5. DOB (support multiple formats)
                boolean dobValid = false;
                LocalDate dob = null;
                for (DateTimeFormatter fmt : formatters) {
                    try {
                        dob = LocalDate.parse(row[3], fmt);
                        dobValid = true;
                        break;
                    } catch (Exception ignored) {}
                }
                if (!dobValid) {
                    valid = false;
                    error.append("DOB parse error; ");
                } else {
                    LocalDate now = LocalDate.now();
                    if (dob.isAfter(now) || ChronoUnit.YEARS.between(dob, now) > 100) {
                        valid = false;
                        error.append("Invalid DOB; ");
                    }
                }

                // Write to appropriate filev 
                if (valid) {
                    validWriter.writeNext(row);
                    validCount++;
                } else {
                    String[] rowWithError = Arrays.copyOf(row, row.length + 1);
                    rowWithError[row.length] = error.toString();
                    invalidWriter.writeNext(rowWithError);
                    invalidCount++;
                }
                rowNumber++;
            }
            System.out.println("Validation completed!");
            System.out.println("Valid records: " + validCount);
            System.out.println("Invalid records: " + invalidCount);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total time (ms): " + (endTime - startTime));
    }
}