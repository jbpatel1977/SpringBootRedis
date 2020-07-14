package com.jp.spring.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jp.spring.model.Employee;
import com.jp.spring.services.EmployeeDataServices;



@RestController
@RequestMapping(value={"/jp/genesis"})
public class EmployeeRestController {

	public static final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class);

	@Autowired
	EmployeeDataServices employeeDataServices;


	@GetMapping(value="/connectioncheck" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> healthCheck(){

		boolean isConnectionAvailable = employeeDataServices.radisConnectionCheck();
		String responseStr;
		if (isConnectionAvailable) {
			responseStr = "Application able to connect to Redis server";
		}
		else {
			responseStr = "Application NOT able to connect to Redis server";
		}
		return new ResponseEntity<String>(responseStr, HttpStatus.OK);
	}
	
	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllEmployeees() {
		LOG.debug("Inside Get /employees " );

		Iterable<Employee> employeeList =  employeeDataServices.getAllEmployees();
		

		return new ResponseEntity<Iterable<Employee>>(employeeList ,HttpStatus.OK );
	}
	
	@GetMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
		LOG.debug("Inside Get /getEmployeeById : Id : " + employeeId );

		Optional<Employee> responseEmp = employeeDataServices.getEmployeeById(employeeId);
		if (null != responseEmp && responseEmp.isPresent()) {
			return new ResponseEntity<Optional<Employee>>(responseEmp ,HttpStatus.OK );
		}

		return new ResponseEntity<Optional<Employee>>(responseEmp ,HttpStatus.NOT_FOUND );

	}
	
	@PostMapping(value = "/employees", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveEmployeee(@RequestBody Employee employee) {
		LOG.debug("Inside Post /employees " );

		Employee newEmployee =  employeeDataServices.saveEmployees(employee);		

		return new ResponseEntity<Employee>(newEmployee ,HttpStatus.OK );
	}
	
	
//	@PostMapping(value = "/bulk/employees", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> saveEmployeee(@RequestBody Iterator<Employee> employee) {
//		LOG.debug("Inside Post /employees " );
//
//		Employee newEmployee =  employeeDataServices.saveBulkEmployees(employee);		
//
//		return new ResponseEntity<Employee>(newEmployee ,HttpStatus.OK );
//	}
	

	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> test() {
		LOG.debug("Inside test method " );

		employeeDataServices.test();
		return new ResponseEntity<String>("ok" ,HttpStatus.OK );

	}
	


}
