package com.example.hr.application.business.event;

import com.example.ddd.helper.DomainEvent;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;

@DomainEvent(boundedContext="hr")
public final class EmployeeHiredEvent extends HrEvent<TcKimlikNo> {
	private final Employee hiredEmployee;

	public EmployeeHiredEvent(Employee hiredEmployee) {
		super(HrEventType.EMPLOYEE_HIRED_EVENT, hiredEmployee.getIdentity());
		this.hiredEmployee = hiredEmployee;
	}

	public Employee getHiredEmployee() {
		return hiredEmployee;
	}

	@Override
	public String toString() {
		return "EmployeeHiredEvent [hiredEmployee=" + hiredEmployee + ", getId()=" + getId() + ", getDateTime()="
				+ getDateTime() + ", getEventType()=" + getEventType() + ", getIdentity()=" + getIdentity() + "]";
	}
	
	
}
