package com.aroha.csvFileReader.controller;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.csvFileReader.dto.MemberDTO;
import com.aroha.csvFileReader.repository.CsvFileReaderRepository;
import com.aroha.csvFileReader.service.CsvFileReaderServiceImpl;

@RestController
@RequestMapping("/api/csv")
public class CsvFileReaderController {

	@Autowired
	private CsvFileReaderServiceImpl service;

	@Autowired
	private CsvFileReaderRepository csvFileReaderRepository;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadCsv(@RequestParam("csvFile") MultipartFile file) {
		try {
			String result = service.readCsv(file);
			return ResponseEntity.ok("CSV Processed: " + result);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
		}
	}

	@GetMapping("/byName")
	public ResponseEntity<List<MemberDTO>> findByNamePrefix(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName)
			throws IllegalAccessException, InvocationTargetException {

		List<MemberDTO> members = service.getByFirstNameAndLastName(firstName, lastName);
		return ResponseEntity.ok(members);
	}

	@GetMapping("/byDobRange")
	public ResponseEntity<List<MemberDTO>> getByDobRange(
	        @RequestParam String startDate, 
	        @RequestParam String endDate) {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try {
	        LocalDate start = LocalDate.parse(startDate, formatter);
	        LocalDate end = LocalDate.parse(endDate, formatter);
	        List<MemberDTO> result = service.getDobOfRange(start.toString(), end.toString());
	        return ResponseEntity.ok(result);
	    } catch (DateTimeParseException e) {
	        return ResponseEntity.badRequest().body(Collections.emptyList());
	    }
	}

	@GetMapping("/getBySalary")
	    public ResponseEntity<List<MemberDTO>> getBySalary(){
	        

	        List<MemberDTO> result = csvFileReaderRepository.findBySalary();
	        return ResponseEntity.ok(result);
	    }
}
