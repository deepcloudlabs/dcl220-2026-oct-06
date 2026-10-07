package com.example.hr.repository;

import java.util.Optional;

import com.example.ddd.helper.Repository;
import com.example.hexagonal.helper.Port;
import com.example.hexagonal.helper.PortType;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;

@Repository(entity=Employee.class)
@Port(PortType.DRIVEN)
public interface EmployeeRepository {

	Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity);

	boolean employeeExists(TcKimlikNo identity);

	Employee persist(Employee employee);

	void save(Employee employee);

	void remove(Employee employee);

}
