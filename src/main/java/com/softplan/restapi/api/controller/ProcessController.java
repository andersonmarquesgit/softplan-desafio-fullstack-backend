package com.softplan.restapi.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.restapi.api.entity.Process;
import com.softplan.restapi.api.entity.User;
import com.softplan.restapi.api.enums.StatusEnum;
import com.softplan.restapi.api.request.AssignUsersRequest;
import com.softplan.restapi.api.response.Response;
import com.softplan.restapi.api.service.ProcessService;
import com.softplan.restapi.api.service.UserService;

@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "*")//Permitindo o acesso de qualquer IP, porta, etc.
public class ProcessController {

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private UserService userService;
	
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

	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('USER_SCREENING')")//Autorização com base no perfil. Nesse caso apenas USER_SCREENING podem consultar processo pelo ID.
	public ResponseEntity<Response<Process>> findById(@PathVariable String id) {
		Response<Process> response = new Response<Process>();
		Process process = this.processService.findById(id);
		
		if(process == null) {
			response.getErrors().add("Register not found! ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(process);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/{number}/{status}/")
	@PreAuthorize("hasAnyRole('USER_SCREENING')")
	public ResponseEntity<Response<Page<Process>>> findByParams(HttpServletRequest request, 
			@PathVariable("page") int page, 
			@PathVariable("count") int count, 
			@PathVariable("number") Integer number, 
			@PathVariable("status") String status) {
		
		//Quando não se quiser informar um dos campos abaixo basta usar uninformed na url
		status = status.equals("uninformed") ? "" : status;
		
		Response<Page<Process>> response = new Response<Page<Process>>();
		Page<Process> processList = null;
		
		if (number > 0) {
			processList = this.processService.findByNumber(page, count, number);
		} else {
			processList = this.processService.findByParameters(page, count, status);
			
		}
		
		response.setData(processList);
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
	
	@PutMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('USER_SCREENING')")
	public ResponseEntity<Response<Process>> assignUser(
			HttpServletRequest request,
			@PathVariable String id,
			@RequestBody AssignUsersRequest assignUsers,
			BindingResult result) {
		
		Response<Process> response = new Response<Process>();
		Process processCurrent = this.processService.findById(id);
		
		if(processCurrent == null) {
			response.getErrors().add("Register not found! ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			this.validateAssignUserProcess(assignUsers.getUserIds(), result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			} 
			
			Set<User> users = this.userService.findByIdIn(assignUsers.getUserIds());
			
			processCurrent.setUsersAssigned(users);
			processCurrent.setStatus(StatusEnum.Assigned);
					
			Process processPersisted = this.processService.createOrUpdate(processCurrent);
			response.setData(processPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	private void validateAssignUserProcess(List<String> ids, BindingResult result) {
		if(ids == null || ids.isEmpty()) {
			result.addError(new ObjectError("Ids", "Ids no information"));
			return;
		}
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
}
