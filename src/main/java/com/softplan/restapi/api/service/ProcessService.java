package com.softplan.restapi.api.service;

import org.springframework.data.domain.Page;
import com.softplan.restapi.api.entity.Process;

public interface ProcessService {

	Process createOrUpdate(Process process);
	
	Process findById(String id);
	
	Page<Process> findAll(int page, int count);
}
