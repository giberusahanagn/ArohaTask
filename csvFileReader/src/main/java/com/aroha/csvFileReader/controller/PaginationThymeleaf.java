package com.aroha.csvFileReader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


	@GetMapping("/search")
	public String getMemberBySerch(Model model, @RequestParam String firstName) {
		List<Member> member = csvFileReaderRepository.searchByFirstName(firstName);
		model.addAttribute("members",member);
	    model.addAttribute("firstName", firstName); 
		return "member-list";

	}

	@GetMapping
	public String getMembers(
	    Model model,
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "10") int size,
	    @RequestParam(required = false) String firstName
	) {
	    Page<Member> memberPage;

	    // Sort by memberId.id (if it's an embedded ID field)
	 //   Sort sortByRecord = Sort.by("record").ascending();
	 //   PageRequest pageable = PageRequest.of(page, size, sortByRecord);

	    PageRequest pageable = PageRequest.of(page, size);


	    if (firstName != null && !firstName.isEmpty()) {
	        memberPage = csvFileReaderRepository.findByMemberIdFirstNameIgnoreCase(firstName, pageable);
	    } else {
	        memberPage = csvFileReaderRepository.findAll(pageable);
	    }

	    model.addAttribute("members", memberPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", memberPage.getTotalPages());
	    model.addAttribute("firstName", firstName); // so the search stays in the input box

	    return "member-list";
	}


}
