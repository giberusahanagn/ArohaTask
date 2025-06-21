package com.aroha.csvFileReader.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.csvFileReader.service.CsvFileReaderServiceImpl;

@RestController
@RequestMapping("/api/csv")
public class CsvFileReaderController {

	 @Autowired
	    private CsvFileReaderServiceImpl service;

	    @PostMapping("/upload")
	    public ResponseEntity<String> uploadCsv(@RequestParam("csvFile") MultipartFile file) {
	        try {
	            String result = service.processCsv(file);
	            return ResponseEntity.ok("CSV Processed: " + result);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
	        }
	    }
}
