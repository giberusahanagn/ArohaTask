package com.aroha.csvFileReader.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberId implements Serializable {

	
	private String firstName;
	    private String lastName;
	    private String gender;
	    private LocalDate dateOfBirth;
		
}
