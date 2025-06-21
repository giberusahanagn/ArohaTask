package com.aroha.csvFileReader.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MemberId implements Serializable {

	  private String firstName;
	    private String lastName;
	    private String gender;
	    private LocalDate dateOfBirth;
		
}
