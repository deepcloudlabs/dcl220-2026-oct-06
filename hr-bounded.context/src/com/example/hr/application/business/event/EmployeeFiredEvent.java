package com.example.hr.application.business.event;

import com.example.ddd.helper.DomainEvent;
import com.example.hr.domain.TcKimlikNo;

@DomainEvent(boundedContext = "hr")
public final class EmployeeFiredEvent extends HrEvent<TcKimlikNo>{

	public EmployeeFiredEvent(TcKimlikNo identity) {
		super(HrEventType.EMPLOYEE_FIRED_EVENT, identity);
	}

}
