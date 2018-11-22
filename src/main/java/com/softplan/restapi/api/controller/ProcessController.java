package com.softplan.restapi.api.controller;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.restapi.api.response.Response;
import com.softplan.restapi.api.service.ProcessService;
import com.softplan.restapi.api.entity.Process;
import com.softplan.restapi.api.entity.User;
import com.softplan.restapi.api.enums.StatusEnum;

@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "*")//Permitindo o acesso de qualquer IP, porta, etc.
public class ProcessController {

	@Autowired
	private ProcessService processService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('USER_SCREENING')")//Autorização com base no perfil. Nesse caso apenas USER_SCREENING podem criar processos.
	public ResponseEntity<Response<Process>> create(HttpServletRequest request, @RequestBody String occurrence, 
			BindingResult result) {
		Response<Process> response = new Response<Process>();
		
		try {
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Process process = new Process();
			process.setOccurrence(occurrence);
			process.setCreateAt(new Date());
			process.setStatus(StatusEnum.New);
			process.setNumber(this.generateNumber());
			Process processPersisted = this.processService.createOrUpdate(process);
			response.setData(processPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('USER_SCREENING')")//Autorização com base no perfil. Nesse caso apenas USER_SCREENING podem listar processos.
	public ResponseEntity<Response<Page<Process>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<Process>> response = new Response<Page<Process>>();
		Page<Process> processList = this.processService.findAll(page, count);
		response.setData(processList);
		
		return ResponseEntity.ok(response);
	}
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
}