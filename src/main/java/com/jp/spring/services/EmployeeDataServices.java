package com.jp.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.spring.model.Employee;
import com.jp.spring.repositories.EmployeeRepository;




@Service
public class EmployeeDataServices {
	public static final Logger LOG = LoggerFactory.getLogger(EmployeeDataServices.class);

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired	
	EmployeeRepository employeeRepository;


	public boolean radisConnectionCheck() {
		try {			
			if (redisTemplate.getConnectionFactory().getConnection() != null) {
				LOG.error("Radis Template is not null");
				return true;
			}
			else {
				LOG.error("Radis Template is not null");
				return false;
			}
		}
		catch (Exception ex) {
			LOG.error(ex.getMessage());
			LOG.error("Error while connecting to Radis server");
			return false;
		}
		//		Object value = redisTemplate.opsForValue().get("jitesh");
		//		LOG.debug("value : " + value.toString());

	}

	public Employee saveEmployees(Employee employee){
		Employee newEmployee = employeeRepository.save(employee);
		return newEmployee;
	}
	
//	public Iterator<Employee> saveBulkEmployees(Iterator<Employee> employees){
//		// Create an empty list 
//		List<Employee> list = new ArrayList<Employee>(); 
//  
//        // Add each element of iterator to the List 
//        employees.forEachRemaining(list::add); 
//        
//        Iterator<Employee> newEmployees = employeeRepository.saveAll(list);
//		return newEmployees;
//	}


	public Iterable<Employee> getAllEmployees(){
		Iterable<Employee> employeeList = employeeRepository.findAll();
		return employeeList;

	}
	
	
	public Optional<Employee> getEmployeeById(Integer employeeId){

		Optional<Employee> employee = employeeRepository.findById(employeeId);
		return employee;

	}
	
	public void test(){
		Employee employee1 = new Employee(10010, new Date(1963,6,1), "Duangkaew", "Piveteau", "F", new Date(1989,8,24));
		Employee employee2 = new Employee(10011, new Date(1953,11,7), "Mary", "Sluis", "F", new Date(1990,1,22));
		
		
//		HashOperations<String, String, Employee> hashOperations = redisTemplate.opsForHash();
		
		HashOperations hashOperations = redisTemplate.opsForHash();
		//**** Working 
//		Map empObjHash = new ObjectMapper().convertValue(employee1, Map.class);
//		hashOperations.put("employee1", "10010" , empObjHash);
		
		Map empMap = (Map) hashOperations.get("employee1", "10010");
//		Employee emp = new ObjectMapper().convertValue(empMap, Employee.class);
		LOG.debug("****** " +empMap);
		
		//** Working // Sets user object in USER hashmap at userId key
//		hashOperations.put("employee2", "10011" , employee2);
		
		
//		
//		Employee emp10001 =  hashOperations.get("employee", "employee:10001");
//		if (null != emp10001) {
//			LOG.debug(" firstName: " + emp10001.getFirst_name()  + " .. LastName  " +  emp10001.getLast_name());
//			
//		}
		

		// Sets user object in USER hashmap at userId key
		hashOperations.put("employee2", "10011" , employee2);
//		hashOperations.put("employee", "10011", employee2);
		
		// Get value of USER hashmap at userId key
//		Employee newEmployee1 =hashOperations.get("employee", employee1.getEmp_no());
//		LOG.debug(" *** newEmployee1 " + newEmployee1.toString()  + "   " + newEmployee1.getFirst_name());
//		Employee newEmployee2 =hashOperations.get("employee", employee2.getEmp_no());
//		LOG.debug(" *** newEmployee2 " + newEmployee2.toString()  + "   " + newEmployee2.getFirst_name());
		
//		LOG.debug("**** " + redisTemplate.opsForHash().get("employee:10001", "gender"));
		
		
		// Get all hashes in USER hashmap
//		Map<String, Employee> employeeHashMap = hashOperations.entries("employee");
//		LOG.debug(".... employe map size: "  + employeeHashMap.size());
		
		
		
//		List<String> list = new ArrayList<>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		redisTemplate.opsForHash().put("test", "map", list.toString());
//		redisTemplate.opsForHash().put("test", "isAdmin", true);
//		
//		System.out.println(redisTemplate.opsForHash().get("test", "map")); // [1, 2, 3, 4]
//		System.out.println(redisTemplate.opsForHash().get("test", "isAdmin")); // true
		

	}
	
}
