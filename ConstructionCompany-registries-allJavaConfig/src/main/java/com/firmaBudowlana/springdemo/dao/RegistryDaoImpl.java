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
	
	// all the methods handling Registry.class
	
	@Autowired
	private SessionFactory sessionFactory;

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
	public void saveRegistry(int registryId, int workingTime, Date registryDate, String absence, int cateringId, int accommodationId) {
		Session session = sessionFactory.getCurrentSession();
		
		Registry registry = session.get(Registry.class, registryId);
		
		registry.setAbsence(absence);		
		registry.setWorkingTime(workingTime);
		registry.setDate(registryDate);
		
		if(accommodationId!=0) {
			Accommodation accommodation = session.get(Accommodation.class, accommodationId);
			registry.setAccommodation(accommodation);
		}
		
		if(cateringId!=0) {
			Catering catering = session.get(Catering.class, cateringId);
			registry.setCatering(catering);
		}
		
		if(!registry.getAbsence().equals("nd.")) {
			registry.setCatering(null);
			registry.setAccommodation(null);
			registry.setWorkingTime(0);
		}
		
		if(cateringId==0 || accommodationId==0) {
			if(cateringId==0) {
				registry.setCatering(null);
			}
			
			if(accommodationId==0) {
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
	
	//setting dynamic query to filter registries
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
		
		//iterating to set all the parameters put in the map
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
