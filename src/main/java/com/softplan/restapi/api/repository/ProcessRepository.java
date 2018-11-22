package com.softplan.restapi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softplan.restapi.api.entity.Process;

public interface ProcessRepository extends JpaRepository<Process, String> {
	
	Process findOneById(String id);
}
