package com.example.hr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.ddd.helper.BusinessMethod;
import com.example.ddd.helper.Entity;

@Entity(boundedContext = "hr", identites = {"departments"})
public class DepartmentList {
	private List<Department> departments;

	private DepartmentList(Builder builder) {
		this.departments = builder.departments;
	}

	public static class Builder {
		private List<Department> departments;

		public Builder() {
		}

		public Builder departments(Department... workingDepartments) {
			Objects.requireNonNull(workingDepartments);
			this.departments = new ArrayList<>();
			for (var department : workingDepartments)
				this.departments.add(department);
			return this;
		}

		public DepartmentList build() {
			// Validation Rules
			// Invariants
			// Policies
			// POLICY-125
			if (departments.contains(Department.IT) && departments.size() > 1)
				throw new IllegalArgumentException("POLICY-125: IT Employees cannot work at different departments!");
			// Regulations
			// Business Rule: BR-236
			if (departments.isEmpty())
				throw new IllegalArgumentException("BR-236: You must provide at least one department!");

			return new DepartmentList(this);
		}

	}

	public List<Department> getDepartments() {
		return List.copyOf(departments);
	}

	@BusinessMethod
	public List<Department> addDepartment(Department department) {
		// Validation Rules: VR-423
		if (departments.contains(department))
			throw new IllegalArgumentException("VR-423 Department already exists: %s".formatted(department));
		// Invariants
		// Policies
		// POLICY-125
		if (department == Department.IT)
			throw new IllegalArgumentException("POLICY-125: IT Employees cannot work at different departments!");
		if (departments.contains(Department.IT))
			throw new IllegalArgumentException("POLICY-125: IT Employees cannot work at different departments!");
		// Regulations
		// Business Rule
		departments.add(department);
		return getDepartments();
	}

	@BusinessMethod
	public List<Department> removeDepartment(Department department) {
		// Validation Rules: VR-424
		if (!departments.contains(department))
			throw new IllegalArgumentException("VR-424 Department does not exist: %s".formatted(department));
		// Invariants
		// Policies
		// Regulations
		// Business Rule
		// Business Rule: BR-236
		if (departments.size() == 1)
			throw new IllegalArgumentException("BR-236: You must provide at least one department!");
		departments.remove(department);
		return getDepartments();
	}

	public boolean contains(Department department) {
		Objects.requireNonNull(department);
		return departments.contains(department);
	}

}
