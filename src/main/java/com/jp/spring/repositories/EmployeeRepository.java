package com.jp.spring.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jp.spring.model.Employee;


@Repository
public interface EmployeeRepository extends  CrudRepository <Employee, Integer >{
	

	

}
