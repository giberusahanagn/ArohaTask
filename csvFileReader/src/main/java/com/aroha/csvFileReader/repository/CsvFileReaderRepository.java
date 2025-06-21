package com.aroha.csvFileReader.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aroha.csvFileReader.entity.Member;
import com.aroha.csvFileReader.entity.MemberId;

@Repository
public interface CsvFileReaderRepository extends JpaRepository<Member, MemberId> {

}
