package com.example.hr.application.business.event;

import com.example.ddd.helper.DomainEvent;
import com.example.hr.domain.Rate;
import com.example.hr.domain.Salary;
import com.example.hr.domain.TcKimlikNo;

@DomainEvent(boundedContext = "hr")
public final class EmployeeSalaryUpdatedEvent extends HrEvent<TcKimlikNo> {

	private final Rate rate;
	private final Salary oldSalary;
	private final Salary newSalary;

	public EmployeeSalaryUpdatedEvent(TcKimlikNo identity,Rate rate,Salary oldSalary,Salary newSalary) {
		super(HrEventType.EMPLOYEE_UPDATED_EVENT, identity);
		this.rate = rate;
		this.oldSalary = oldSalary;
		this.newSalary = newSalary;
	}

	public Rate getRate() {
		return rate;
	}

	public Salary getOldSalary() {
		return oldSalary;
	}

	public Salary getNewSalary() {
		return newSalary;
	}

}
