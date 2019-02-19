package com.firmaBudowlana.springdemo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.converter.DateConverter;
import com.firmaBudowlana.springdemo.dao.RegistryDao;
import com.firmaBudowlana.springdemo.entity.Catering;
import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.entity.Accommodation;
import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DateNotInScopeException;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;
import com.firmaBudowlana.springdemo.exceptions.IncorrectDateFormat;

@Service
public class RegistryServiceImpl implements RegistryService {
	
	//all the methods referring to application's business logic
	
	@Autowired
	private RegistryDao registryDao;

	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private DateNotInScopeException dateScopeException;

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@Transactional
	public void saveManager(User kierownik) {
		registryDao.saveManager(kierownik);
	}

	@Override
	@Transactional
	public LinkedHashMap<Integer, String> getManagers() {
		LinkedHashMap<Integer, String> listaKierownikow = registryDao.getManagers();
		return listaKierownikow;
	}
	
	@Override
	@Transactional
	public LinkedHashMap<Integer, String> getManagersWithoutAdmin() {
		return registryDao.getManagersWithoutAdmin();
	}

	@Override
	@Transactional
	public List<User> getManagerList() {
		return registryDao.getManagerList();
	}
	
	@Override
	@Transactional
	public List<User> getManagerListWithoutAdmin() {
		return registryDao.getManagerListWithoutAdmin();
	}

	@Override
	@Transactional
	public void deleteManager(int userId) {
		registryDao.deleteManager(userId);
	}

	@Override
	@Transactional
	public void saveProject(Project project, int userId) {
		registryDao.saveProject(project, userId);
	}

	@Override
	@Transactional
	public List<Project> getAllProjects() {
		return registryDao.getAllProjects();
	}

	@Override
	@Transactional
	public List<Project> getOngoingProjects() {
		return registryDao.getOngoingProjects();
	}
	
	@Override
	@Transactional
	public List<Project> getOngoingManagerProject(String username) {
		return registryDao.getOngoingManagerProject(username);
	}

	@Override
	@Transactional
	public void deleteProject(int projectId) {
		registryDao.deleteProject(projectId);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		registryDao.saveEmployee(employee);
	}

	@Override
	@Transactional
	public List<Employee> getEmployees() {
		return registryDao.getEmployees();
	}

	@Override
	@Transactional
	public void deleteEmployee(int employeeId) {
		registryDao.deleteEmployee(employeeId);
	}

	@Override
	@Transactional
	public Employee getEmployee(int employeeId) {
		return registryDao.getEmployee(employeeId);
	}

	@Override
	@Transactional
	public List<Employee> getAvailableEmployees() {
		return registryDao.getAvailableEmployees();
	}

	@Override
	@Transactional
	public LinkedHashMap<Project, Long> getNumberOfEmployeesPerProject() {
		return registryDao.getNumberOfEmployeesPerProject();
	}

	@Override
	@Transactional
	public void matchEmployeesWithProject(int projectId, List<Integer> employeesId) {
		registryDao.matchEmployeesWithProject(projectId, employeesId);
	}

	@Override
	@Transactional
	public void dischargeEmployee(int employeeId) {
		registryDao.dischargeEmployee(employeeId);
	}

	@Override
	@Transactional
	public void saveCatering(Catering catering) {
		registryDao.saveCatering(catering);
	}

	@Override
	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		registryDao.saveAccommodation(accommodation);
	}

	@Override
	@Transactional
	public List<Project> getManagerProjects(int userId) {
		return registryDao.getManagerProjects(userId);
	}
	
	@Override
	@Transactional
	public List<Project> getOngoingManagerProjects(int userId) {
		return registryDao.getOngoingManagerProjects(userId);
	}

	@Override
	@Transactional
	public User getManager(int userId) {
		return registryDao.getManager(userId);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeesOfProject(int projectId) {
		return registryDao.getEmployeesOfProject(projectId);
	}

	@Override
	@Transactional
	public List<Catering> getCatering() {
		return registryDao.getCatering();
	}
	
	@Override
	@Transactional
	public Catering getCatering(int cateringId) {
		return registryDao.getCatering(cateringId);
	}

	@Override
	@Transactional
	public List<Accommodation> getAccommodationsList() {
		return registryDao.getAccommodationsList();
	}
	
	@Override
	@Transactional
	public Accommodation getAccommodation(int accommodationId) {
		return registryDao.getAccommodation(accommodationId);
	}
	
	@Override
	@Transactional
	public Project getProject(int projectId) {
		return registryDao.getProject(projectId);
	}
	
	//helper method to handle exceptions of incompatible date format
	
	@Override
	public String checkStrDateBeforeParse(String strDate) throws IncorrectDateFormat {
		
		if(strDate==null || strDate.isEmpty()) {
			throw new IncorrectDateFormat("data nie mo¿e byæ pusta");
		}
		else if(strDate.length()!=10) {
			throw new IncorrectDateFormat("prawid³owy format daty ma 10 znaków, podany ci¹g znaków ma inn¹ d³ugoœæ");
		}
		else if(!(Character.isDigit(strDate.charAt(0))
				&&Character.isDigit(strDate.charAt(1))
				&&Character.isDigit(strDate.charAt(2))
				&&Character.isDigit(strDate.charAt(3))
				&&strDate.charAt(4)=='-'
				&&Character.isDigit(strDate.charAt(5))
				&&Character.isDigit(strDate.charAt(6))
				&&strDate.charAt(7)=='-'
				&&Character.isDigit(strDate.charAt(8))
				&&Character.isDigit(strDate.charAt(9)))) {
			throw new IncorrectDateFormat("nieprawid³owy format daty - prawid³owy format to 'yyyy-MM-dd'");
		}
		
		return strDate;
	}
	
	//helper method to handle exceptions of incompatible date format
	
	@Override
	public Date parseDate(String dateStr) throws ParseException, DateNotInScopeException {
	
		Date theDate = formatter.parse(dateStr);
		final LocalDate MIN_LOCAL_DATE = LocalDate.of(2019, 1, 1);
		final LocalDate MAX_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());
		if (dateConverter.convertToLocalDateViaInstant(theDate).isBefore(MIN_LOCAL_DATE)
				|| dateConverter.convertToLocalDateViaInstant(theDate).isAfter(MAX_LOCAL_DATE)) {
				throw new DateNotInScopeException("Podano datê spoza zakresu - mo¿esz podaæ datê nie wczeœniejsz¹ ni¿ '2019-01-01' i nie póŸniejsz¹ ni¿ data dzisiejsza");
			}
		
		return theDate;
	}
	
	//helper method to handle exceptions of incompatible date format
	
	@Override
	public String convertUnparseableDateString(String dateStr) {
		String strMonth = dateStr.substring(4,7);
		String strDay = dateStr.substring(8, 10);
		String strYear = dateStr.substring(24,28);
		
		if(strMonth.equals("Jan")) {
			strMonth="01";
		}
		else if(strMonth.equals("Feb")) {
			strMonth="02";
		}
		else if(strMonth.equals("Mar")) {
			strMonth="03";
		}
		else if(strMonth.equals("Apr")) {
			strMonth="04";
		}
		else if(strMonth.equals("May")) {
			strMonth="05";
		}
		else if(strMonth.equals("Jun")) {
			strMonth="06";
		}
		else if(strMonth.equals("Jul")) {
			strMonth="07";
		}
		else if(strMonth.equals("Aug")) {
			strMonth="08";
		}
		else if(strMonth.equals("Sep")) {
			strMonth="09";
		}
		else if(strMonth.equals("Oct")) {
			strMonth="10";
		}
		else if(strMonth.equals("Nov")) {
			strMonth="11";
		}
		else if(strMonth.equals("Dec")) {
			strMonth="12";
		}
		
		String correctDateStr = strYear+"-"+strMonth+"-"+strDay;
		return correctDateStr;
	}

	@Override
	public String convertDateToString(Date theDate) {
		String dateStr = formatter.format(theDate);
		return dateStr;
	}

	@Override
	@Transactional
	public List<Registry> matchRegistriesWithEmployees(int projectId, Date registryDate, int workingTime, String absence,
			int cateringId, int accommodationId) {
		return registryDao.matchRegistriesWithEmployees(projectId, registryDate, workingTime, absence, cateringId, accommodationId);
	}

	@Override
	@Transactional
	public Registry getRegistry(int registryId) {
		return registryDao.getRegistry(registryId);
	}

	@Override
	@Transactional
	public Project getProjectOfRegistry(int registryId) {
		return registryDao.getProjectOfRegistry(registryId);
	}

	@Override
	@Transactional
	public Employee getEmployeeOfRegistry(int registryId) {
		return registryDao.getEmployeeOfRegistry(registryId);
	}
	
	@Override
	@Transactional
	public void cleanRegistryBeforeUpdate(Registry registry) {
		registryDao.cleanRegistryBeforeUpdate(registry);	
	}

	@Override
	@Transactional
	public void saveRegistry(Registry registry) {
		registryDao.saveRegistry(registry);	
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
	public User getProjectManager(int projectId) {
		return registryDao.getProjectManager(projectId);
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
	public List<Registry> filterRegistries(int projectId, Date startDate,
			Date endDate, boolean status) {
		return registryDao.filterRegistries(projectId, startDate, endDate, status);
	}

	@Override
	public String convertToStringStatus(boolean status) {
		String strStatus="";
		if(status==false) {
			strStatus="aktywny";
		}
		else {
			strStatus="zakoñczony";
		}
		return strStatus;
	}

	@Override
	public String convertAuthorityToPosition(String strRole) {
		String positionString=null;
		
		if(strRole.equalsIgnoreCase("ROLE_ADMIN")) {
			positionString="Koordynator";
		}else {
			positionString="Kierownik budowy";
		}
		return positionString;
	}
	
}
