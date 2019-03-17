package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveEmployee(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(employee);
	}

	@Override
	public List<Employee> getEmployees() {
		Session session = sessionFactory.getCurrentSession();
		Query<Employee> theQuery = session.createQuery("from Employee e order by e.lastName", Employee.class);
		List<Employee> employees = theQuery.getResultList();
		return employees;
	}

	@Override
	public List<Employee> getAvailableEmployees() {
		List<Employee> availableEmployees = new ArrayList<Employee>();
		String hql = "select e from Employee e left join e.projects pr group by e.id having count(pr)=0 order by e.lastName";
		Session session = sessionFactory.getCurrentSession();
		Query<Employee> theQuery = session.createQuery(hql, Employee.class);
		availableEmployees=theQuery.getResultList();
		return availableEmployees;
	}

	@Override
	public List<Employee> getEmployeesOfProject(int projectId) {
		List<Employee> employees = new ArrayList<Employee>();
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		Hibernate.initialize(project.getEmployees());
		employees = project.getEmployees();
		return employees;
	}

	@Override
	public LinkedHashMap<Project, Long> getNumberOfEmployeesPerProject() {
		String hql = "select pr, count(e) from Project pr left join pr.employees e where pr.projectStatus=0 group by pr.id";
		LinkedHashMap<Project, Long> numberOfEmployeesPerProject = new LinkedHashMap<Project, Long>();
		Session session = sessionFactory.getCurrentSession();
		Query<?> theQuery = session.createQuery(hql);
		Iterator<?> iterator = theQuery.getResultList().iterator();
		while(iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			Project project = (Project) item[0];
			Long numberOfEmployees = (Long) item[1];
			numberOfEmployeesPerProject.put(project, numberOfEmployees);
		}
		return numberOfEmployeesPerProject;
	}

	@Override
	public Employee getEmployee(int employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Employee employee = session.get(Employee.class, employeeId);
		return employee;
	}

	@Override
	public void matchEmployeesWithProject(int projectId, List<Integer> employeesId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		List<Employee> employees = project.getEmployees();
		for (Integer tmpEmployeeId : employeesId) {
			Employee tmpEmployee = session.get(Employee.class, tmpEmployeeId);
			employees.add(tmpEmployee);
		}
		project.setEmployees(employees);
	}

	@Override
	public void dischargeEmployee(int employeeId) {
		String hql = "select pr from Project pr left join pr.employees e where pr.projectStatus=0 and e.id=:employeeId group by pr.id";
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery(hql, Project.class);
		theQuery.setParameter("employeeId", employeeId);
		List<Project> projects = theQuery.getResultList();
		for(Project tmpProject: projects) {
			List<Employee> tmpEmployees = tmpProject.getEmployees();
			for(Iterator<Employee> iterator = tmpEmployees.iterator(); iterator.hasNext();) {
				Employee tmpEmployee = iterator.next();
				if(tmpEmployee.getId()==employeeId) {
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void deleteEmployee(int employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Employee employee = session.get(Employee.class, employeeId);
		session.delete(employee);
	}
	
	
}
