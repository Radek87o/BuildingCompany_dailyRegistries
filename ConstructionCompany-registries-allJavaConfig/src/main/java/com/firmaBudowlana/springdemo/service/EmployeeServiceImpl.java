package com.firmaBudowlana.springdemo.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.EmployeeDao;
import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeDao dao;

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		dao.saveEmployee(employee);
	}

	@Override
	@Transactional
	public List<Employee> getEmployees() {
		return dao.getEmployees();
	}

	@Override
	@Transactional
	public List<Employee> getAvailableEmployees() {
		return dao.getAvailableEmployees();
	}

	@Override
	@Transactional
	public List<Employee> getEmployeesOfProject(int projectId) {
		return dao.getEmployeesOfProject(projectId);
	}

	@Override
	@Transactional
	public LinkedHashMap<Project, Long> getNumberOfEmployeesPerProject() {
		return dao.getNumberOfEmployeesPerProject();
	}

	@Override
	@Transactional
	public Employee getEmployee(int employeeId) {
		return dao.getEmployee(employeeId);
	}

	@Override
	@Transactional
	public void matchEmployeesWithProject(int projectId, List<Integer> employeesId) {
		dao.matchEmployeesWithProject(projectId, employeesId);
	}

	@Override
	@Transactional
	public void dischargeEmployee(int employeeId) {
		dao.dischargeEmployee(employeeId);
	}

	@Override
	@Transactional
	public void deleteEmployee(int employeeId) {
		dao.deleteEmployee(employeeId);
	}
}
