package com.softplan.restapi.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.softplan.restapi.api.entity.Process;
import com.softplan.restapi.api.repository.ProcessRepository;
import com.softplan.restapi.api.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {
	
	@Autowired
	private ProcessRepository processRepository;
	
	@Override
	public Process createOrUpdate(Process process) {
		return this.processRepository.save(process);
	}

	@Override
	public Process findById(String id) {
		return this.processRepository.findOneById(id);
	}

	@Override
	public Page<Process> findAll(int page, int count) {
		return this.processRepository.findAll(PageRequest.of(page, count));
	}

	@Override
	public Page<Process> findByNumber(int page, int count, Integer number) {
		return this.processRepository.findByNumber(number, PageRequest.of(page, count));
	}

	@Override
	public Page<Process> findByParameters(int page, int count, String status) {
		return this.processRepository.findByStatusIgnoreCaseContainingOrderByCreateAtDesc(status, PageRequest.of(page, count));
	}

}
