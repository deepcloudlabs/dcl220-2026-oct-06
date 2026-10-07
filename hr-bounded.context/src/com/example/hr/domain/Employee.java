package com.example.hr.domain;

import java.util.Arrays;
import java.util.Objects;

import com.example.ddd.helper.BusinessMethod;
import com.example.ddd.helper.Entity;

// Domain --> sub-domain(s)    
//            core sub-domain  --> bounded-context       --> microservices -> process -> docker instance
// ubiquitous language: TcKimlikNo, FullName, Iban, Salary, Email, BirthYear, ...
//                      Domain Expert -> Distilled Knowledge -> 
//                      Knowledge Crunching    
//            analysis         --> design                     
@Entity(identites = { "identity" }, boundedContext = "hr", aggregate = true)
public class Employee {
	public static final Salary MIN_SALARY = Salary.of(30_000);

	private TcKimlikNo identity;
	private FullName fullName;
	private Iban iban;
	private Salary salary;
	private Email email;
	private BirthYear birthYear;
	private JobStyle jobStyle;
	private DepartmentList departmentList;
	private Photo photo;

	private Employee(Builder builder) {
		this.identity = builder.identity;
		this.fullName = builder.fullName;
		this.iban = builder.iban;
		this.salary = builder.salary;
		this.email = builder.email;
		this.birthYear = builder.birthYear;
		this.jobStyle = builder.jobStyle;
		this.departmentList = builder.departmentList;
		this.photo = builder.photo;
	}

	public static class Builder {
		private TcKimlikNo identity;
		private FullName fullName;
		private Iban iban;
		private Salary salary;
		private Email email;
		private BirthYear birthYear;
		private JobStyle jobStyle;
		private DepartmentList departmentList;
		private Photo photo;

		public Builder(String identity) {
			this.identity = TcKimlikNo.valueOf(identity);
		}

		public Builder fullName(String firstName, String lastName) {
			this.fullName = FullName.of(firstName, lastName);
			return this;
		}

		public Builder iban(String value) {
			this.iban = Iban.valueOf(value);
			return this;
		}

		public Builder salary(double value) {
			this.salary = Salary.of(value);
			return this;
		}

		public Builder salary(double value, FiatCurrency currency) {
			this.salary = Salary.of(value, currency);
			return this;
		}
		
		public Builder salary(double value, String currency) {
			this.salary = Salary.of(value, FiatCurrency.valueOf(currency));
			return this;
		}

		public Builder email(String value) {
			this.email = Email.valueOf(value);
			return this;
		}

		public Builder birthYear(int value) {
			this.birthYear = new BirthYear(value);
			return this;
		}

		public Builder jobStyle(String value) {
			this.jobStyle = JobStyle.valueOf(value);
			return this;
		}

		public Builder departmentList(String... workingDepartments) {
			this.departmentList = new DepartmentList.Builder().departments(
					Arrays.stream(workingDepartments).map(Department::valueOf).toList().toArray(new Department[0]))
					.build();
			return this;
		}

		public Builder departmentList(Department... workingDepartments) {
			this.departmentList = new DepartmentList.Builder().departments(workingDepartments).build();
			return this;
		}

		public Builder photo(String value) {
			this.photo = Photo.of(value);
			return this;
		}

		public Builder photo(byte[] value) {
			this.photo = Photo.of(value);
			return this;
		}

		public Employee build() {
			// Validation Rules
			// Invariants
			// Policies
			// POLICY-442
			if (departmentList.contains(Department.IT) && jobStyle == JobStyle.FULL_TIME
					&& salary.lessThanOrEqual(MIN_SALARY.multiplies(5.0)))
				throw new IllegalArgumentException("POLICY-442 is violated.");
			// Regulations
			// Business Rule
			return new Employee(this);
		}
	}

	@BusinessMethod
	public Salary increaseSalary(Rate rate) {
		var newSalary = salary.multiplies(rate);
		// Validation Rules
		// Invariants
		// Policies
		// POLICY-442
		if (departmentList.contains(Department.IT) && jobStyle == JobStyle.FULL_TIME
				&& newSalary.lessThanOrEqual(MIN_SALARY.multiplies(5.0)))
			throw new IllegalArgumentException("POLICY-442 is violated.");
		// Regulations
		// Business Rule
		this.salary = newSalary;
		return this.salary;
	}

	@BusinessMethod
	public void promote(Rate rate,Department department) {
		var newSalary = salary.multiplies(rate);
		@SuppressWarnings("unused")
		var existingDepartments = departmentList.getDepartments();
		departmentList.addDepartment(department);
		// TODO: Apply Memento pattern
		// Validation Rules
		// Invariants
		// Policies
		// POLICY-442
		if (departmentList.contains(Department.IT) && jobStyle == JobStyle.FULL_TIME
				&& newSalary.lessThanOrEqual(MIN_SALARY.multiplies(5.0)))
			throw new IllegalArgumentException("POLICY-442 is violated.");
		// Regulations
		// Business Rule
		this.salary = newSalary;		
	}

	public TcKimlikNo getIdentity() {
		return identity;
	}

	public void setIdentity(TcKimlikNo identity) {
		this.identity = identity;
	}

	public FullName getFullName() {
		return fullName;
	}

	public void setFullName(FullName fullName) {
		this.fullName = fullName;
	}

	public Iban getIban() {
		return iban;
	}

	public void setIban(Iban iban) {
		this.iban = iban;
	}

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public BirthYear getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(BirthYear birthYear) {
		this.birthYear = birthYear;
	}

	public JobStyle getJobStyle() {
		return jobStyle;
	}

	public void setJobStyle(JobStyle jobStyle) {
		this.jobStyle = jobStyle;
	}

	public DepartmentList getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(DepartmentList departmentList) {
		this.departmentList = departmentList;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "Employee [identity=" + identity + ", fullName=" + fullName + ", iban=" + iban + ", salary=" + salary
				+ ", email=" + email + ", birthYear=" + birthYear + ", jobStyle=" + jobStyle + ", departments="
				+ departmentList + "]";
	}
	
	
}
