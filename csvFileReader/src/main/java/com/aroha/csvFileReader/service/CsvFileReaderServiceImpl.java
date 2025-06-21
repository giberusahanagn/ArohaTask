package com.aroha.csvFileReader.service;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.csvFileReader.entity.Member;
import com.aroha.csvFileReader.entity.MemberId;
import com.aroha.csvFileReader.repository.CsvFileReaderRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Service
public class CsvFileReaderServiceImpl {

    @Autowired
    private CsvFileReaderRepository csvFileReaderRepository;

    public String processCsv(MultipartFile file) throws Exception {
    	
    	long startTime = System.currentTimeMillis(); // Start time
        Set<String> uniqueSet = new HashSet<>();
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        };

        File failedFile = new File("FailedRecords.csv");
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
             CSVWriter failedWriter = new CSVWriter(new FileWriter(failedFile))) {

            String[] header = reader.readNext();
            if (header == null || header.length < 14) {
                throw new RuntimeException("Invalid or missing CSV header");
            }

            String[] invalidHeader = Arrays.copyOf(header, header.length + 1);
            invalidHeader[header.length] = "Error";
            failedWriter.writeNext(invalidHeader);

            String[] row;
            int valid = 0, invalid = 0;

            while ((row = reader.readNext()) != null) {
                boolean isValid = true;
                StringBuilder error = new StringBuilder();

                // Skip if row length is insufficient
                if (row.length < 14) {
                    invalid++;
                    continue;
                }

                // Trim all fields
                for (int i = 0; i < row.length; i++) {
                    if (row[i] != null) row[i] = row[i].trim();
                }

                // Mandatory field checks
                if (row[1].isEmpty() || row[2].isEmpty() || row[3].isEmpty() ||
                    row[4].isEmpty() || row[11].isEmpty()) {
                    isValid = false;
                    error.append("Missing mandatory field; ");
                }

                // Address cleanup
                row[7] = row[7].replaceAll("[^a-zA-Z0-9 ]", "");
                row[8] = row[8].replaceAll("[^a-zA-Z0-9 ]", "");

                // Mobile sanitization and validation
                row[11] = row[11].replaceAll("[^0-9]", "");
                if (!row[11].matches("^[789]\\d{9}$")) {
                    isValid = false;
                    error.append("Invalid mobile; ");
                }

                // DOB parsing and validation
                LocalDate dob = null;
                boolean dobValid = false;
                for (DateTimeFormatter fmt : formatters) {
                    try {
                        dob = LocalDate.parse(row[3], fmt);
                        dobValid = true;
                        break;
                    } catch (Exception ignored) {}
                }
                if (!dobValid || dob.isAfter(LocalDate.now()) ||
                    ChronoUnit.YEARS.between(dob, LocalDate.now()) > 100) {
                    isValid = false;
                    error.append("Invalid DOB; ");
                }

                // Education field
                if (row[5].isEmpty()) {
                    isValid = false;
                    error.append("Empty education; ");
                }

                // Salary parsing
                try {
                    Double.parseDouble(row[13]);
                } catch (NumberFormatException e) {
                    isValid = false;
                    error.append("Invalid salary; ");
                }

                // Composite uniqueness check
                String key = row[1] + "|" + row[2] + "|" + row[4] + "|" + dob;
                if (!uniqueSet.add(key)) {
                    isValid = false;
                    error.append("Duplicate record; ");
                }

                if (isValid) {
                    Member member = new Member();
                    MemberId id = new MemberId();
                    id.setFirstName(row[1]);
                    id.setLastName(row[2]);
                    id.setGender(row[4]);
                    id.setDateOfBirth(dob);

                    member.setId(id);
                    member.setEmail(row[0]);
                    member.setAddress1(row[7]);
                    member.setAddress2(row[8]);
                    member.setCity(row[9]);
                    member.setState(row[10]);
                    member.setCountry(row[6]);
                    member.setMobile(row[11]);

                    csvFileReaderRepository.save(member);
                    valid++;
                } else {
                    String[] rowWithError = Arrays.copyOf(row, row.length + 1);
                    rowWithError[row.length] = error.toString();
                    failedWriter.writeNext(rowWithError);
                    invalid++;
                }
            }
            long endTime = System.currentTimeMillis(); // End time
            long timeTaken = endTime - startTime;

            return "Valid: " + valid + ", Invalid: " + invalid +
                   " (see FailedRecords.csv), Time Taken: " + timeTaken + " ms";        }
    }
}
