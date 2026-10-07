package com.example.hr.application;

import static com.example.hexagonal.helper.PortType.DRIVING;

import java.util.Optional;

import com.example.hexagonal.helper.Port;
import com.example.hr.domain.Employee;
import com.example.hr.domain.Rate;
import com.example.hr.domain.TcKimlikNo;

@Port(DRIVING)
public interface HrApplication {
	Employee hireEmployee(Employee employee);

	Optional<Employee> fireEmployee(TcKimlikNo identity);

	Optional<Employee> increaseSalary(TcKimlikNo identity, Rate rate);

	Optional<Employee> getEmployee(TcKimlikNo identity);
}
