package com.softplan.restapi.api.service;

import org.springframework.data.domain.Page;

import com.softplan.restapi.api.entity.Process;

public interface ProcessService {

	Process createOrUpdate(Process process);
	
	Process findById(String id);
	
	Page<Process> findAll(int page, int count);

	Page<Process> findByNumber(int page, int count, Integer number);

	Page<Process> findByParameters(int page, int count, String status);
	
	Page<Process> findByLegalOpinionIsNull(int page, int count);
}
