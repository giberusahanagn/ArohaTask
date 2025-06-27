package com.aroha.csvFileReader.service;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.csvFileReader.dto.MemberDTO;
import com.aroha.csvFileReader.repository.CsvFileReaderRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Service
public class CsvFileReaderServiceImpl {

	@Autowired
	private CsvFileReaderRepository csvFileReaderRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String readCsv(MultipartFile file) throws Exception {
		long startTime = System.currentTimeMillis();
		Set<String> uniqueCompositeKeys = new HashSet<>();

		DateTimeFormatter[] dateFormats = { DateTimeFormatter.ofPattern("d/M/yyyy"),
				DateTimeFormatter.ofPattern("dd-MM-yyyy"), DateTimeFormatter.ofPattern("dd/MM/yyyy") };

		File failedFile = new File("FailedRecords.csv");

		int validCount = 0;
		int invalidCount = 0;
		int currentRowIndex = 1;
		int batchSize = 100;

		List<Object[]> batchParams = new ArrayList<>();
		int batchNumber = 1;

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
			while ((row = reader.readNext()) != null) {
				System.out.println("Processing row: " + currentRowIndex++);
				StringBuilder errorMessage = new StringBuilder();

				for (int i = 0; i < row.length; i++) {
					if (row[i] != null)
						row[i] = row[i].trim();
				}

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

				row[7] = row[7].replaceAll("[^a-zA-Z0-9 ]", "");
				row[8] = row[8].replaceAll("[^a-zA-Z0-9 ]", "");
				row[11] = row[11].replaceAll("[^0-9]", "");

				if (!row[11].matches("^[789]\\d{9}$")) {
					errorMessage.append("Invalid mobile");
					writeInvalidRow(failedWriter, row, errorMessage.toString());
					invalidCount++;
					continue;
				}

				LocalDate dob = null;
				for (DateTimeFormatter formatter : dateFormats) {
					try {
						dob = LocalDate.parse(row[3], formatter);
						break;
					} catch (Exception ignored) {
					}
				}

				if (dob == null || dob.isAfter(LocalDate.now())
						|| ChronoUnit.YEARS.between(dob, LocalDate.now()) > 100) {
					errorMessage.append("Invalid DOB");
					writeInvalidRow(failedWriter, row, errorMessage.toString());
					invalidCount++;
					continue;
				}

				String compositeKey = row[1] + "|" + row[2] + "|" + row[4] + "|" + dob;
				if (!uniqueCompositeKeys.add(compositeKey)) {
					errorMessage.append("Duplicate in file");
					System.out.println("Duplicate entry: " + compositeKey);
					writeInvalidRow(failedWriter, row, errorMessage.toString());
					invalidCount++;
					continue;
				}

				// Add valid row to batchParams
				Object[] params = new Object[] { dob, // date_of_birth
						row[1], // first_name
						row[4], // gender
						row[2], // last_name
						row[7], // address1
						row[8], // address2
						row[9], // city
						row[6], // company
						row[12], // email
						row[11], // mobile
						row[0], // record
						row[13], // salary
						row[10] // state
				};
				batchParams.add(params);
				validCount++;

				if (batchParams.size() == batchSize) {
					insertBatch(batchParams);
					System.out.println("[BATCH COMMIT #" + batchNumber++ + "] Flushed " + batchSize + " records");
					batchParams.clear();
				}
			}

			// Final batch insert
			if (!batchParams.isEmpty()) {
				insertBatch(batchParams);
				System.out.println(
						"[FINAL BATCH COMMIT #" + batchNumber + "] Flushed " + batchParams.size() + " records");
			}
		}

		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("CSV Processing Completed.");
		System.out.println("Valid Records: " + validCount);
		System.out.println("Invalid Records: " + invalidCount);
		System.out.println("Time Taken: " + timeTaken + " ms");

		return "Valid: " + validCount + ", Invalid: " + invalidCount + " (see FailedRecords.csv), Time Taken: "
				+ timeTaken + " ms";
	}

	private void insertBatch(List<Object[]> batchParams) {
		String sql = "INSERT INTO member (date_of_birth, first_name, gender, last_name, "
				+ "address1, address2, city, company, email, mobile, record, salary, state) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, batchParams);
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
