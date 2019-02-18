package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Catering;
import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.entity.Accommodation;
import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;

@Repository
public class RegistryDaoImpl implements RegistryDao {
	
	//all the methods referring to application's business logic
	//CRUD for each entity class from com.firmaBudowlana.springdemo.entity
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveManager(User user) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(user);
	}

	@Override
	public LinkedHashMap<Integer, String> getManagers() {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> managers = new LinkedHashMap<Integer, String>();
		Query<?> theQuery = session
				.createQuery("select k.id, concat(k.firstName,' ',k.lastName) from User k order by k.lastName");
		Iterator<?> iterator = theQuery.getResultList().iterator();
		while (iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int id = (int) item[0];
			String fullName = (String) item[1];
			managers.put(id, fullName);
		}
		return managers;
	}
	
	@Override
	public LinkedHashMap<Integer, String> getManagersWithoutAdmin() {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> managers = new LinkedHashMap<Integer, String>();
		Query<?> theQuery = session
				.createQuery("select k.id, concat(k.firstName,' ',k.lastName) from User k where k.id<>1 order by k.lastName");
		Iterator<?> iterator = theQuery.getResultList().iterator();
		while (iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int id = (int) item[0];
			String fullName = (String) item[1];
			managers.put(id, fullName);
		}
		return managers;
	}

	@Override
	public List<User> getManagerList() {
		List<User> managerList = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Query<User> theQuery = session.createQuery("from User k order by k.lastName",
				User.class);
		managerList = theQuery.getResultList();
		return managerList;
	}
	
	@Override
	public List<User> getManagerListWithoutAdmin() {
		List<User> managerList = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Query<User> theQuery = session.createQuery("from User u where u.id<>1 order by u.lastName", User.class);
		managerList = theQuery.getResultList();
		return managerList;
	}

	@Override
	public void deleteManager(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User manager = session.get(User.class, userId);
		Hibernate.initialize(manager.getProjects());
		List<Project> managerProjects = manager.getProjects();
		for (Project tempProject : managerProjects) {
			tempProject.setUser(null);
		}
		session.delete(manager);
	}

	@Override
	public void saveProject(Project project, int userId) {
		Session session = sessionFactory.getCurrentSession();
		User manager = session.get(User.class, userId);
		project.setUser(manager);
		session.merge(project);
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects;
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("from Project", Project.class);
		projects = theQuery.getResultList();
		return projects;
	}
	
	@Override
	public List<Project> getOngoingProjects() {
		List<Project> ongoingProjects = new ArrayList<Project>();
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("from Project p where p.projectStatus=0", Project.class);
		ongoingProjects = theQuery.getResultList();
		return ongoingProjects;
	}
	
	@Override
	public List<Project> getOngoingManagerProject(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("select p from Project p inner join User u where u.username=:username", Project.class);
		theQuery.setParameter("username", username);
		List<Project> projects = theQuery.getResultList();
		return projects;
	}

	@Override
	public void deleteProject(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		session.delete(project);
	}

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
	public void deleteEmployee(int employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Employee employee = session.get(Employee.class, employeeId);
		session.delete(employee);
	}
	
	@Override
	public Employee getEmployee(int employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Employee employee = session.get(Employee.class, employeeId);
		return employee;
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
		//Employee employee = session.get(Employee.class, employeeId);
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
	public void saveCatering(Catering catering) {
		Session session = sessionFactory.getCurrentSession();
		session.save(catering);
	}

	@Override
	public void saveAccommodation(Accommodation accommodation) {
		Session session = sessionFactory.getCurrentSession();
		session.save(accommodation);
	}

	@Override
	public List<Project> getManagerProjects(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		Hibernate.initialize(user.getProjects());
		List<Project> projects = user.getProjects();
		return projects;
	}

	@Override
	public List<Project> getOngoingManagerProjects(int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("select p from Project p left join p.user u where p.projectStatus=0 and u.id=:userId", Project.class);
		theQuery.setParameter("userId", userId);
		List<Project> projects = theQuery.getResultList();
		return projects;
	}

	@Override
	public User getManager(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		return user;
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
	public List<Catering> getCatering() {
		List<Catering> listCatering = new ArrayList<Catering>();
		Session session = sessionFactory.getCurrentSession();
		Query<Catering> theQuery = session.createQuery("from Catering", Catering.class);
		listCatering = theQuery.getResultList();
		return listCatering;
	}

	@Override
	public Catering getCatering(int cateringId) {
		Session session = sessionFactory.getCurrentSession();
		Catering catering = session.get(Catering.class, cateringId);
		return catering;
	}

	@Override
	public List<Accommodation> getAccommodationsList() {
		List<Accommodation> accommodations = new ArrayList<Accommodation>();
		Session session = sessionFactory.getCurrentSession();
		Query<Accommodation> theQuery = session.createQuery("from Accommodation", Accommodation.class);
		accommodations = theQuery.getResultList();
		return accommodations;
	}
	
	@Override
	public Accommodation getAccommodation(int accommodationId) {
		Session session = sessionFactory.getCurrentSession();
		Accommodation accommodation = session.get(Accommodation.class, accommodationId);
		return accommodation;
	}

	@Override
	public Project getProject(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		return project;
	}

	@Override
	public List<Registry> matchRegistriesWithEmployees(int projectId, Date registryDate, int workingTime, String absence,
			int cateringId, int accommodationId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		List<Employee> projectEmployees = project.getEmployees();
		Accommodation accommodation = session.get(Accommodation.class, accommodationId);
		Catering catering = session.get(Catering.class, cateringId);
		
		Query<Registry> theQuery = session.createQuery("select r from Registry r inner join r.project p where r.date=:registryDate and p.id=:projectId", Registry.class);
		theQuery.setParameter("registryDate", registryDate);
		theQuery.setParameter("projectId", projectId);
		List<Registry> registries = theQuery.getResultList();
		for (Employee tmpEmployee : projectEmployees) {
			Registry registry = new Registry(registryDate, workingTime, absence);
			registry.setCatering(catering);
			registry.setAccommodation(accommodation);
			registry.setProject(project);
			registry.setEmployee(tmpEmployee);
			registries.add(registry);
		}

		for (Registry tmpRegistry : registries) {
			session.saveOrUpdate(tmpRegistry);
		}
		
		return registries;
	}

	@Override
	public Registry getRegistry(int registryId) {
		Session session = sessionFactory.getCurrentSession();
		Registry registry = session.get(Registry.class, registryId);
		return registry;
	}

	@Override
	public Project getProjectOfRegistry(int registryId) {
		Session session = sessionFactory.getCurrentSession();
		Registry registry = session.get(Registry.class, registryId);
		Hibernate.initialize(registry.getProject());
		Project project = registry.getProject();
		return project;
	}

	@Override
	public Employee getEmployeeOfRegistry(int registryId) {
		Session session = sessionFactory.getCurrentSession();
		Registry registry = session.get(Registry.class, registryId);
		Hibernate.initialize(registry.getEmployee());
		Employee employee = registry.getEmployee();
		return employee;
	}
	
	
	
	@Override
	public void cleanRegistryBeforeUpdate(Registry registry) {
		Session session = sessionFactory.getCurrentSession();
		Date date = registry.getDate();
		int employeeId = registry.getEmployee().getId();
		Query<Registry> theQuery = session.createQuery("select r from Registry r inner join r.employee e where e.id=:employeeId and r.date=:registryDate", Registry.class);
		theQuery.setParameter("employeeId", employeeId);
		theQuery.setParameter("registryDate", date);
		Registry oldRegistry = theQuery.getSingleResult();
		oldRegistry.setId(0);
		session.delete(oldRegistry);
	}

	@Override
	public void saveRegistry(Registry registry) {
		Session session = sessionFactory.getCurrentSession();
		
		if(!registry.getAbsence().equals("nd.")) {
			registry.setCatering(null);
			registry.setAccommodation(null);
			registry.setWorkingTime(0);
		}
		
		else if(registry.getCatering().getId()==0 || registry.getAccommodation().getId()==0) {
			if(registry.getCatering().getId()==0) {
				registry.setCatering(null);
			}
			
			if(registry.getAccommodation().getId()==0) {
				registry.setAccommodation(null);
			}
		}
		registry.setDateOfIssue(new Date());
		session.merge(registry);
	}
	
	
	

	@Override
	public void saveRegistry(int registryId, int workingTime, Date registryDate, String absence, Catering catering,
			Accommodation accommodation) {
		Session session = sessionFactory.getCurrentSession();
		
		Registry registry = session.get(Registry.class, registryId);
		
		registry.setAbsence(absence);
		registry.setAccommodation(accommodation);
		registry.setCatering(catering);
		registry.setWorkingTime(workingTime);
		registry.setDate(registryDate);
		
		if(!registry.getAbsence().equals("nd.")) {
			registry.setCatering(null);
			registry.setAccommodation(null);
			registry.setWorkingTime(0);
		}
		
		else if(registry.getCatering().getId()==0 || registry.getAccommodation().getId()==0) {
			if(registry.getCatering().getId()==0) {
				registry.setCatering(null);
			}
			
			if(registry.getAccommodation().getId()==0) {
				registry.setAccommodation(null);
			}
		}
		registry.setDateOfIssue(new Date());
		session.merge(registry);
		
	}

	@Override
	public List<Registry> getRegistryList(int projectId, Date registryDate) {
		Session session = sessionFactory.getCurrentSession();
		Query<Registry> theQuery = session.createQuery("select r from Registry r inner join r.project p where r.date=:registryDate and p.id=:projectId", Registry.class);
		theQuery.setParameter("registryDate", registryDate);
		theQuery.setParameter("projectId", projectId);
		List<Registry> registryList = theQuery.getResultList();
		return registryList;
	}

	@Override
	public void deleteRegistries(int projectId, Date registryDate) {
		Session session = sessionFactory.getCurrentSession();
		Query<Registry> theQuery = session.createQuery("select r from Registry r inner join r.project p where r.date=:registryDate and p.id=:projectId", Registry.class);
		theQuery.setParameter("registryDate", registryDate);
		theQuery.setParameter("projectId", projectId);
		List<Registry> registryList = theQuery.getResultList();
		for(Registry tmpRegistry: registryList) {
			tmpRegistry.setProject(null);
			tmpRegistry.setEmployee(null);
			tmpRegistry.setCatering(null);
			tmpRegistry.setAccommodation(null);
			session.delete(tmpRegistry);
		}
	}

	@Override
	public List<Date> getRegistryDates(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select r.date from Registry r inner join r.project p where p.id=:projectId group by r.date order by r.date desc";
		Query<Date> theQuery = session.createQuery(hql, Date.class);
		theQuery.setParameter("projectId", projectId);
		List<Date> projectDates = theQuery.getResultList();
		return projectDates;
	}
	
	

	@Override
	public List<Date> getRegistryDates() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select r.date from Registry r group by r.date order by r.date desc";
		Query<Date> theQuery = session.createQuery(hql, Date.class);
		List<Date> projectDates = theQuery.getResultList();
		return projectDates;
	}

	@Override
	public User getProjectManager(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		Hibernate.initialize(project.getUser());
		User user = project.getUser();
		return user;
	}
	
	
	@Override
	public List<Registry> copyRegistgryList(int projectId, Date newDate, Date oldDate) throws IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry {
		Session session = sessionFactory.getCurrentSession();
		Query<Registry> theQuery = session.createQuery("select r from Registry r inner join r.project p where r.date=:oldDate and p.id=:projectId", Registry.class);
		theQuery.setParameter("oldDate", oldDate);
		theQuery.setParameter("projectId", projectId);
		List<Registry> historicalRegistryList = theQuery.getResultList();
		Project project = session.get(Project.class, projectId);
		Hibernate.initialize(project.getEmployees());
		List<Employee> employees = project.getEmployees();
		List<Registry> updatedRegistryList = new ArrayList<Registry>();
		
		List<Integer> intRegistryList = new ArrayList<Integer>();
		List<Integer> intEmployeeList = new ArrayList<Integer>();
		for(Registry tmpRgistry: historicalRegistryList) {
			intRegistryList.add(tmpRgistry.getEmployee().getId());
		}
		
		intRegistryList.sort(Comparator.naturalOrder());
		
		for(Employee tmpEmployee: employees) {
			intEmployeeList.add(tmpEmployee.getId());
		}
		intEmployeeList.sort(Comparator.naturalOrder());
		
		if(employees.size()!=historicalRegistryList.size()){
			throw new IncompatibleSizeOfRegistryLists("Lista rejestrów dla ¿¹danej daty historycznej ma inn¹ liczbê rekordów ni¿ obecna liczba pracowników");
		}
		else {
			for (int i=0; i<intEmployeeList.size();i++) {
				if(intEmployeeList.get(i)!=intRegistryList.get(i)) {
					throw new DifferentEmployeesInRegistry("Obecna lista pracowników ró¿ni siê od listy pracowników z rejestru dla ¿adanej daty historycznej");
				}
			}
			
			for(Registry tmpRegistry: historicalRegistryList) {
				Registry registry = new Registry(newDate, tmpRegistry.getWorkingTime(), tmpRegistry.getAbsence());
				registry.setEmployee(tmpRegistry.getEmployee());
				registry.setProject(tmpRegistry.getProject());
				registry.setCatering(tmpRegistry.getCatering());
				registry.setAccommodation(tmpRegistry.getAccommodation());
				session.saveOrUpdate(registry);
				updatedRegistryList.add(registry);
			}
		}
		return updatedRegistryList;
	}

	@Override
	public List<Registry> getAllRegistries() {
		Session session = sessionFactory.getCurrentSession();
		Query<Registry> theQuery = session.createQuery("from Registry r order by r.date desc", Registry.class);
		List<Registry> registryList = theQuery.getResultList();
		return registryList;
	}

	@Override
	public List<Registry> filterRegistries(int projectId, Date startDate,
			Date endDate, boolean status) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer hql = new StringBuffer("select r from Registry r inner join r.project p");
		Map<String, Object> params = new HashMap<String, Object>();
		boolean first=true;
		boolean second=false;
		
		if(projectId!=0) {
			hql.append(first ? " where ":"");
			hql.append("p.id=:projectId");
			params.put("projectId", projectId);
		}
		else if(projectId==0) {
			second=true;
		}
		if(startDate!=null) {
			hql.append(second ? " where ":"  and ");
			hql.append("r.date>=:startDate");
			params.put("startDate", startDate);
		}
		if(endDate!=null) {
			hql.append(" and r.date<=:endDate");
			params.put("endDate", endDate);
		}
		if(status==false) {
			hql.append(" and p.projectStatus=0");
		}
		if(status==true) {
			hql.append(" and p.projectStatus=1");
		}
		
		Query<Registry> theQuery = session.createQuery(hql.toString(), Registry.class);
		Iterator<String> iter = params.keySet().iterator();
		while(iter.hasNext()) {
			String name = iter.next();
			Object value = params.get(name);
			theQuery.setParameter(name, value);
		}
		
		List<Registry> registries = theQuery.getResultList();
		return registries;
	}
	
	
}
