package com.aroha.csvFileReader.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aroha.csvFileReader.dto.MemberDTO;
import com.aroha.csvFileReader.entity.Member;
import com.aroha.csvFileReader.entity.MemberId;

@Repository
public interface CsvFileReaderRepository extends JpaRepository<Member, MemberId> {

	@Query(value = "SELECT record, first_name, last_name, date_of_birth, gender, city " + "FROM member "
			+ "WHERE first_name LIKE ?1 AND last_name LIKE ?2", nativeQuery = true)
	List<MemberDTO> findByFirstAndLastName(String firstName, String lastName);

	@Query(value = """
				    SELECT
				        record AS record,
				        first_name AS firstName,
				        last_name AS lastName,
				        date_of_birth AS dateOfBirth,
				        gender AS gender,
				        city AS city
				    FROM member
			WHERE date_of_birth BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)
					""", nativeQuery = true)
	List<MemberDTO> findByDobBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = """
				    SELECT
				        record AS record,
				        first_name AS firstName,
				        last_name AS lastName,
				        date_of_birth AS dateOfBirth,
				        gender AS gender,
				        city AS city,
				        salary AS salary
				    FROM member
			WHERE salary >20000""", nativeQuery = true)
	List<MemberDTO> findBySalary();

}
