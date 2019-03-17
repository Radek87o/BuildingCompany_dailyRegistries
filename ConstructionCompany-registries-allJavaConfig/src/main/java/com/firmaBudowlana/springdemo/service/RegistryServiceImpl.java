package com.firmaBudowlana.springdemo.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.RegistryDao;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;

@Service
public class RegistryServiceImpl implements RegistryService {

	// all the methods handling Registry.class

	@Autowired
	private RegistryDao registryDao;

	@Override
	@Transactional
	public List<Registry> matchRegistriesWithEmployees(int projectId, Date registryDate, int workingTime,
			String absence, int cateringId, int accommodationId) {
		return registryDao.matchRegistriesWithEmployees(projectId, registryDate, workingTime, absence, cateringId,
				accommodationId);
	}

	@Override
	@Transactional
	public Registry getRegistry(int registryId) {
		return registryDao.getRegistry(registryId);
	}

	@Override
	@Transactional
	public List<Registry> getRegistryList(int projectId, Date registryDate) {
		return registryDao.getRegistryList(projectId, registryDate);
	}

	@Override
	@Transactional
	public void saveRegistry(int registryId, int workingTime, Date registryDate, String absence, int cateringId,
			int accommodationId) {
		registryDao.saveRegistry(registryId, workingTime, registryDate, absence, cateringId, accommodationId);

	}

	@Override
	@Transactional
	public void deleteRegistries(int projectId, Date registryDate) {
		registryDao.deleteRegistries(projectId, registryDate);
	}

	@Override
	@Transactional
	public List<Date> getRegistryDates(int projectId) {
		return registryDao.getRegistryDates(projectId);
	}

	@Override
	@Transactional
	public List<Date> getRegistryDates() {
		return registryDao.getRegistryDates();
	}

	@Override
	@Transactional
	public List<Registry> copyRegistgryList(int projectId, Date newDate, Date oldDate)
			throws IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry {
		return registryDao.copyRegistgryList(projectId, newDate, oldDate);
	}

	@Override
	@Transactional
	public List<Registry> getAllRegistries() {
		return registryDao.getAllRegistries();
	}

	@Override
	@Transactional
	public List<Registry> filterRegistries(int projectId, Date startDate, Date endDate, boolean status) {
		return registryDao.filterRegistries(projectId, startDate, endDate, status);
	}
}
