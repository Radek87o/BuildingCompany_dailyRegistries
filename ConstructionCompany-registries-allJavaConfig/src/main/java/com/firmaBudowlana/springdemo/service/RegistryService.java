package com.firmaBudowlana.springdemo.service;

import java.util.Date;
import java.util.List;

import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;

public interface RegistryService {
	
	public List<Registry> matchRegistriesWithEmployees(int projectId, Date registryDate, int workingTime,
			String absence, int cateringId, int accommodationId);

	public Registry getRegistry(int registryId);

	public void saveRegistry(int registryId, int workingTime, Date registryDate, String absence, int cateringId,
			int accommodationId);

	public void deleteRegistries(int projectId, Date registryDate);

	public List<Registry> getRegistryList(int projectId, Date registryDate);

	public List<Date> getRegistryDates(int projectId);

	public List<Date> getRegistryDates();

	public List<Registry> copyRegistgryList(int projectId, Date newDate, Date oldDate)
			throws IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry;

	public List<Registry> getAllRegistries();

	public List<Registry> filterRegistries(int projectId, Date startDate, Date endDate, boolean status);
}
