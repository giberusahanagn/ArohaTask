package com.aroha.csvFileReader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aroha.csvFileReader.entity.Member;
import com.aroha.csvFileReader.repository.CsvFileReaderRepository;

@Controller
@RequestMapping("/members")
public class PaginationThymeleaf {

	@Autowired
	private CsvFileReaderRepository csvFileReaderRepository;

	@GetMapping
	public String getMembers(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String firstName) {
		Page<Member> memberPage;

		PageRequest pageable = PageRequest.of(page, size);

		if (firstName != null && !firstName.trim().isEmpty()) {
			memberPage = csvFileReaderRepository.findByMemberIdFirstNameIgnoreCase(firstName.trim(), pageable);
		} else {
			memberPage = csvFileReaderRepository.findAll(pageable);
		}

		model.addAttribute("members", memberPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", memberPage.getTotalPages());
		model.addAttribute("firstName", firstName); // keeps search term in input field

		return "member-list";
	}
}
