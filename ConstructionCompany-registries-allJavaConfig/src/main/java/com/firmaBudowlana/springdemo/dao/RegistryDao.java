package com.firmaBudowlana.springdemo.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.firmaBudowlana.springdemo.entity.Catering;
import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.entity.Accommodation;
import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;

public interface RegistryDao {

	public void saveManager(User user);
	
	public LinkedHashMap<Integer,String> getManagers();
	
	public LinkedHashMap<Integer,String> getManagersWithoutAdmin();
	
	public List<User> getManagerList();
	
	public List<User> getManagerListWithoutAdmin();
	
	public void deleteManager(int userId);
	
	public void saveProject(Project project, int userId);
	
	public List<Project> getAllProjects();
	
	public List<Project> getOngoingProjects();
	
	public void deleteProject(int projectId);
	
	public void saveEmployee(Employee employee);
	
	public Employee getEmployee(int employeeId);
	
	public List<Employee> getEmployees();
	
	public List<Employee> getAvailableEmployees();
	
	public LinkedHashMap<Project, Long> getNumberOfEmployeesPerProject();
	
	public void deleteEmployee(int employeeId);
	
	public void matchEmployeesWithProject(int projectId, List<Integer> employeesId);
	
	public void dischargeEmployee(int employeeId);
	
	public void saveCatering(Catering catering);
	
	public void saveAccommodation(Accommodation accommodation);
	
	public List<Project> getManagerProjects(int userId);
	
	public List<Project> getOngoingManagerProjects(int userId);
	
	public List<Project> getOngoingManagerProject(String username);
	
	public User getManager(int userId);
	
	public List<Employee> getEmployeesOfProject(int projectId);
	
	public List<Catering> getCatering();
	
	public Catering getCatering(int cateringId);
	
	public List<Accommodation> getAccommodationsList();
	
	public Accommodation getAccommodation(int accommodationId);
	
	public Project getProject(int projectId);
	
	public List<Registry> matchRegistriesWithEmployees(int projectId, Date registryDate, int workingTime, String absence, int cateringId, int accommodationId);
	
	public Registry getRegistry(int registryId);
	
	public Project getProjectOfRegistry(int registryId);
	
	public Employee getEmployeeOfRegistry(int registryId);
	
	public void cleanRegistryBeforeUpdate(Registry registry);
	
	public void saveRegistry(Registry registry);
	
	public void saveRegistry(int registryId, int workingTime, Date registryDate, String absence, int cateringId, int accommodationId);
	
	public List<Registry> getRegistryList(int projectId, Date registryDate);
	
	public void deleteRegistries(int projectId, Date registryDate);
	
	public List<Date> getRegistryDates(int projectId);
	
	public List<Date> getRegistryDates();
	
	public User getProjectManager(int projectId);
	
	public List<Registry> copyRegistgryList(int projectId, Date newDate, Date oldDate) throws IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry;
	
	public List<Registry> getAllRegistries();
	
	public List<Registry> filterRegistries(int projectId, Date startDate, Date endDate, boolean status);
}
