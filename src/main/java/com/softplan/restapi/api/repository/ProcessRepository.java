package com.softplan.restapi.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.softplan.restapi.api.entity.Process;

public interface ProcessRepository extends JpaRepository<Process, String> {
	
	Process findOneById(String id);

	Page<Process> findByStatusIgnoreCaseContainingOrderByCreateAtDesc(String status, PageRequest pages);

	Page<Process> findByNumber(Integer number, PageRequest pages);
}
