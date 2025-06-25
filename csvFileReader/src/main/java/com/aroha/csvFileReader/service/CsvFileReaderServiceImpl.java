package com.aroha.csvFileReader.service;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.csvFileReader.dto.MemberDTO;
import com.aroha.csvFileReader.entity.Member;
import com.aroha.csvFileReader.entity.MemberId;
import com.aroha.csvFileReader.repository.CsvFileReaderRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CsvFileReaderServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CsvFileReaderRepository csvFileReaderRepository;

	@Transactional
	public String readCsv(MultipartFile file) throws Exception {
	    long startTime = System.currentTimeMillis();
	    Set<String> uniqueCompositeKeys = new HashSet<>();

	    DateTimeFormatter[] dateFormats = {
	        DateTimeFormatter.ofPattern("d/M/yyyy"),
	        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
	        DateTimeFormatter.ofPattern("dd/MM/yyyy")
	    };

	    File failedFile = new File("FailedRecords.csv");

	    int validCount = 0;
	    int invalidCount = 0;
	    int currentRowIndex = 1;
	    int batchSize = 100;
	    int batchCount = 0;
	    int batchNumber = 1;

	    try (
	        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
	        CSVWriter failedWriter = new CSVWriter(new FileWriter(failedFile))
	    ) {
	        System.out.println("Reading CSV file...");

	        String[] header = reader.readNext();
	        if (header == null || header.length < 14) {
	            throw new RuntimeException("Invalid or missing CSV header");
	        }

	        String[] invalidHeader = Arrays.copyOf(header, header.length + 1);
	        invalidHeader[header.length] = "Error";
	        failedWriter.writeNext(invalidHeader);

	        String[] row;
	        while ((row = reader.readNext()) != null) {
	            System.out.println("Processing row: " + currentRowIndex++);
	            StringBuilder errorMessage = new StringBuilder();

	            // Trim all fields
	            for (int i = 0; i < row.length; i++) {
	                if (row[i] != null) row[i] = row[i].trim();
	            }

	            // Basic validation
	            if (row.length < 14) {
	                errorMessage.append("Incomplete row");
	                writeInvalidRow(failedWriter, row, errorMessage.toString());
	                invalidCount++;
	                continue;
	            }

	            if (row[1].isEmpty() || row[2].isEmpty() || row[3].isEmpty() || row[4].isEmpty() || row[11].isEmpty()) {
	                errorMessage.append("Missing mandatory field");
	                writeInvalidRow(failedWriter, row, errorMessage.toString());
	                invalidCount++;
	                continue;
	            }

	            // Format sanitization
	            row[7] = row[7].replaceAll("[^a-zA-Z0-9 ]", "");
	            row[8] = row[8].replaceAll("[^a-zA-Z0-9 ]", "");
	            row[11] = row[11].replaceAll("[^0-9]", "");

	            if (!row[11].matches("^[789]\\d{9}$")) {
	                errorMessage.append("Invalid mobile");
	                writeInvalidRow(failedWriter, row, errorMessage.toString());
	                invalidCount++;
	                continue;
	            }

	            // Parse DOB
	            LocalDate dob = null;
	            for (DateTimeFormatter formatter : dateFormats) {
	                try {
	                    dob = LocalDate.parse(row[3], formatter);
	                    break;
	                } catch (Exception ignored) {}
	            }

	            if (dob == null || dob.isAfter(LocalDate.now()) || ChronoUnit.YEARS.between(dob, LocalDate.now()) > 100) {
	                errorMessage.append("Invalid DOB");
	                writeInvalidRow(failedWriter, row, errorMessage.toString());
	                invalidCount++;
	                continue;
	            }

	            // Composite key check
	            String compositeKey = row[1] + "|" + row[2] + "|" + row[4] + "|" + dob;
	            if (!uniqueCompositeKeys.add(compositeKey)) {
	                errorMessage.append("Duplicate in file");
	                writeInvalidRow(failedWriter, row, errorMessage.toString());
	                invalidCount++;
	                continue;
	            }

	            // Construct entity
	            Member member = new Member();
	            MemberId memberId = new MemberId();
	            memberId.setFirstName(row[1]);
	            memberId.setLastName(row[2]);
	            memberId.setGender(row[4]);
	            memberId.setDateOfBirth(dob);

	            member.setMemberId(memberId);
	            member.setRecord(row[0]);
	            member.setEmail(row[12]);
	            member.setCompany(row[6]);
	            member.setAddress1(row[7]);
	            member.setAddress2(row[8]);
	            member.setCity(row[9]);
	            member.setState(row[10]);
	            member.setMobile(row[11]);
	            member.setSalary(row[13]);

	            entityManager.persist(member);
	            validCount++;
	            batchCount++;

	            if (batchCount % batchSize == 0) {
	                entityManager.flush();
	                entityManager.clear();
	                System.out.println("[BATCH COMMIT #" + batchNumber + "] Flushed " + batchSize + " records");
	                batchNumber++;
	            }
	        }
	    }

	    // Final flush for remaining records
	    int remaining = batchCount % batchSize;
	    if (remaining > 0) {
	        entityManager.flush();
	        entityManager.clear();
	        System.out.println("[FINAL BATCH COMMIT #" + batchNumber + "] Flushed " + remaining + " records");
	    }

	    long timeTaken = System.currentTimeMillis() - startTime;

	    System.out.println("CSV Processing Completed.");
	    System.out.println("Valid Records: " + validCount);
	    System.out.println("Invalid Records: " + invalidCount);
	    System.out.println("Time Taken: " + timeTaken + " ms");
	    System.out.println("Batch count (persisted records): " + batchCount);

	    return "Valid: " + validCount + ", Invalid: " + invalidCount + " (see FailedRecords.csv), Time Taken: "
	            + timeTaken + " ms";
	}

	private void writeInvalidRow(CSVWriter writer, String[] row, String errorMessage) {
	    String[] rowWithError = Arrays.copyOf(row, row.length + 1);
	    rowWithError[row.length] = errorMessage;
	    writer.writeNext(rowWithError);
	}

	public List<MemberDTO> getByFirstNameAndLastName(String firstName, String lastName) {
		List<MemberDTO> rows = csvFileReaderRepository.findByFirstAndLastName(firstName, lastName);

		return rows;
	}
	public List<MemberDTO> getDobOfRange(String startDate, String endDate) {
		List<MemberDTO> rows = csvFileReaderRepository.findByDobBetween(startDate, endDate);
		return rows;
	}

	public List<MemberDTO> getBySalary() {
		List<MemberDTO> rows = csvFileReaderRepository.findBySalary();
		return rows;
	}

}
