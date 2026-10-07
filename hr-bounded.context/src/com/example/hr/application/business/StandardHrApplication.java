package com.example.hr.application.business;

import java.util.Optional;

import com.example.hexagonal.helper.Application;
import com.example.hr.application.HrApplication;
import com.example.hr.application.business.event.EmployeeFiredEvent;
import com.example.hr.application.business.event.EmployeeHiredEvent;
import com.example.hr.application.business.event.EmployeeSalaryUpdatedEvent;
import com.example.hr.application.business.event.HrEvent;
import com.example.hr.domain.Employee;
import com.example.hr.domain.Rate;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.infrastructure.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

@Application(HrApplication.class)
public class StandardHrApplication implements HrApplication {
	private final EmployeeRepository employeeRepository;
	private final EventPublisher<HrEvent<TcKimlikNo>> eventPublisher;
	
	public StandardHrApplication(EmployeeRepository employeeRepository, EventPublisher<HrEvent<TcKimlikNo>> eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public Employee hireEmployee(Employee employee) {
		var identity = employee.getIdentity();
		if (employeeRepository.employeeExists(identity ))
			throw new IllegalStateException("Employee (%s) already exists.".formatted(identity));
		var persistedEmployee = employeeRepository.persist(employee);
		var event = new EmployeeHiredEvent(persistedEmployee);
		eventPublisher.publishEvent(event);
		return persistedEmployee;
	}

	@Override
	public Optional<Employee> fireEmployee(TcKimlikNo identity) {
		var employee = employeeRepository.findEmployeeByIdentity(identity).orElseThrow(() -> new IllegalArgumentException("There is no such employee (%s) exists.".formatted(identity.getValue())));
		employeeRepository.remove(employee);
		var event = new EmployeeFiredEvent(identity);
		eventPublisher.publishEvent(event);		
		return Optional.of(employee);
	}

	@Override
	public Optional<Employee> increaseSalary(TcKimlikNo identity, Rate rate) {
		var employee = employeeRepository.findEmployeeByIdentity(identity).orElseThrow(() -> new IllegalArgumentException("There is no such employee (%s) exists.".formatted(identity.getValue())));
		var oldSalary = employee.getSalary();
		var newSalary = employee.increaseSalary(rate);
		employeeRepository.save(employee);
		var event = new EmployeeSalaryUpdatedEvent(identity,rate,oldSalary,newSalary);
		eventPublisher.publishEvent(event);
		return Optional.of(employee);
	}

	@Override
	public Optional<Employee> getEmployee(TcKimlikNo identity) {
		return employeeRepository.findEmployeeByIdentity(identity);
	}

}
