package com.aroha.csvFileReader.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data


public class MemberDTO {
	private String record;
	private String firstName;
	private String lastName;
	private java.sql.Date dateOfBirth;
	private String gender;
	private String city;
	private String salary;
	public MemberDTO(String record, String firstName, String lastName, Date dateOfBirth, String gender, String city) {
		super();
		this.record = record;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.city = city;
	}
	public MemberDTO(String record, String firstName, String lastName, Date dateOfBirth, String gender, String city,
			String salary) {
		super();
		this.record = record;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.city = city;
		this.salary = salary;
	}

}
