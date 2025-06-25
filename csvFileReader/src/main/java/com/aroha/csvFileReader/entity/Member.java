package com.aroha.csvFileReader.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class Member {


	@EmbeddedId
	private MemberId memberId;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String company;
	private String mobile;
	private String record;
	private String salary;
	

}
