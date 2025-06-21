package com.aroha.csvFileReader.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Member {

	@EmbeddedId
	private MemberId id;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String mobile;

}
