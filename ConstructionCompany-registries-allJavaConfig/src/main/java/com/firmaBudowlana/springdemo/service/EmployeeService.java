package com.firmaBudowlana.springdemo.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;

public interface EmployeeService {
	
	public void saveEmployee(Employee employee);
	public List<Employee> getEmployees();
	public List<Employee> getAvailableEmployees();
	public List<Employee> getEmployeesOfProject(int projectId);
	public LinkedHashMap<Project, Long> getNumberOfEmployeesPerProject();
	public Employee getEmployee(int employeeId);
	public void matchEmployeesWithProject(int projectId, List<Integer> employeesId);
	public void dischargeEmployee(int employeeId);
	public void deleteEmployee(int employeeId);
}
