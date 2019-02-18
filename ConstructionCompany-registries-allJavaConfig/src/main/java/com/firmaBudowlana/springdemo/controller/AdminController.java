package com.firmaBudowlana.springdemo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.entity.Employee;
import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DateNotInScopeException;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;
import com.firmaBudowlana.springdemo.service.RegistryService;
import com.firmaBudowlana.springdemo.service.UserService;
import com.firmaBudowlana.springdemo.user.AppUser;

@Controller
@RequestMapping("/koordynator")
public class AdminController {
	
	//the following methods handle all the app functions available only for users having the role of 'ADMIN'
	
	@Autowired
	private RegistryService registryService;
	
	@Autowired
	private UserService userService;
	
	@Resource(name="nieobecnoscOpcje")
	private Map<String, String> absenceOptions;

	@InitBinder
	public void initBinSder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(Date.class, "data", new CustomDateEditor(dateFormat, false));
		dataBinder.registerCustomEditor(String.class, stringTrimmer);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public String constraintViolationExceptionHandler(Exception exc) {
		exc.printStackTrace();
		return "ConstraintViolationException";
	}

	@ExceptionHandler(value = IncompatibleSizeOfRegistryLists.class)
	public String incompatibleSizeOfRegistryListsHandler(Exception exc) {
		exc.printStackTrace();
		return "incompatibleSizeOfRegistryLists";
	}

	@ExceptionHandler(value = DateNotInScopeException.class)
	public String dateNotInScopeExceptionHandler(Exception exc) {
		exc.printStackTrace();
		return "DateNotInScopeException";
	}

	@ExceptionHandler(value = DifferentEmployeesInRegistry.class)
	public String differentEmployeesInRegistry(Exception exc) {
		exc.printStackTrace();
		return "differentEmployeesInRegistry";
	}
	
	@GetMapping("/rejestrujUzytkownika")
	public String registerUser(Model theModel) {
		theModel.addAttribute("appUser", new AppUser());
		return "registration-form";
	}
	
	@PostMapping("/weryfikujRejestracje")
	public String saveManager(@Valid @ModelAttribute(name = "appUser") AppUser theAppUser,
			BindingResult theBindingResult, @RequestParam(name="userId", required=false) Integer userId, Model theModel) {
		
		String username = theAppUser.getUsername();
		System.out.println("App user name: "+theAppUser.getUsername());
		
		if (theBindingResult.hasErrors()) {
			return "registration-form";
		}
		
		User existingUser = userService.findByUsername(username);
		
		if(existingUser!=null && userId==null) {
			theModel.addAttribute("appUser", new AppUser());
			theModel.addAttribute("registrationError", "Ten u¿ytkownik ju¿ istnieje w bazie danych");
			return "registration-form";
		}
		
		else if(userId!=null) {
			userService.updateTheUser(userId, theAppUser);
			return "registration-confirmation";
		}
		
		userService.save(theAppUser);
		return "registration-confirmation";
	}
	
	@GetMapping("/listaKierownikow")
	public String managerList(Model theModel) {
		List<User> userList = registryService.getManagerListWithoutAdmin();
		theModel.addAttribute("userList", userList);
		return "manager-list";
	}
	
	@GetMapping("/updateKierownika")
	public String updateUser(@RequestParam(name="userId") int userId, Model theModel) {
		AppUser theAppUser = userService.populateAppUserToUpdate(userId);
		System.out.println(userId);
		theModel.addAttribute("appUser", theAppUser);
		theModel.addAttribute("userId", userId);
		return "registration-form";
	}

	@GetMapping("/usunKierownika")
	public String deleteManager(@RequestParam("userId") int userId, Model theModel) {
		registryService.deleteManager(userId);
		return "redirect:/koordynator/listaKierownikow";
	}
	
	@GetMapping("/dodajProjekt")
	public String addProject(Model theModel) {
		Project project = new Project();
		LinkedHashMap<Integer, String> userList = registryService.getManagersWithoutAdmin();
		List<Project> projectList = registryService.getOngoingProjects();
		theModel.addAttribute("project", project);
		theModel.addAttribute("userList", userList);
		theModel.addAttribute("projectList", projectList);
		return "add-project";
	}

	@PostMapping("/zapiszProjekt")
	public String saveProject(@Valid @ModelAttribute(name = "project") Project project,
			BindingResult theBindingResult, @RequestParam(name = "user.id") int userId, Model theModel) {
		if (theBindingResult.hasErrors()) {
			return "add-project";
		}
		registryService.saveProject(project, userId);
		return "redirect:/koordynator/dodajProjekt";
	}

	@GetMapping("/updateProjektu")
	public String updateProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = registryService.getProject(projectId);
		LinkedHashMap<Integer, String> userList = registryService.getManagers();
		theModel.addAttribute("project", project);
		theModel.addAttribute("userList", userList);
		return "add-project";
	}

	@GetMapping("/usunProjekt")
	public String deleteProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = registryService.getProject(projectId);
		theModel.addAttribute("project", project);
		registryService.deleteProject(projectId);
		return "redirect:/koordynator/dodajProjekt";
	}
	
	@GetMapping("/dodajPracownika")
	public String addEmployee(Model theModel) {
		Employee employee = new Employee();
		List<Employee> employeeList = registryService.getEmployees();
		theModel.addAttribute("employee", employee);
		theModel.addAttribute("employeeList", employeeList);
		return "add-employee";
	}

	@PostMapping("/zapiszPracownika")
	public String saveEmployee(@Valid @ModelAttribute(name = "employee") Employee employee,
			BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "add-employee";
		}
		registryService.saveEmployee(employee);
		return "redirect:/koordynator/dodajPracownika";
	}

	@GetMapping("/updatePracownika")
	public String updateEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		Employee employee = registryService.getEmployee(employeeId);
		theModel.addAttribute("employee", employee);
		return "add-employee";
	}

	@GetMapping("/usunPracownika")
	public String deleteEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		Employee employee = registryService.getEmployee(employeeId);
		theModel.addAttribute("employee", employee);
		registryService.deleteEmployee(employeeId);
		return "redirect:/koordynator/dodajPracownika";
	}

	@GetMapping("/przypiszPracownika")
	public String matchEmployeeWithProject(Model theModel) {
		Project project = new Project();
		List<Project> projectList = registryService.getOngoingProjects();
		List<Employee> employeeList = registryService.getAvailableEmployees();
		LinkedHashMap<Project, Long> employeesPerProject = registryService.getNumberOfEmployeesPerProject();
		theModel.addAttribute("project", project);
		theModel.addAttribute("projectList", projectList);
		theModel.addAttribute("employeeList", employeeList);
		theModel.addAttribute("employeesPerProject", employeesPerProject);
		return "assign-employee";
	}

	@PostMapping("/dodajPracownikowDoProjektu")
	public String addEmployeesToProject(@RequestParam("id") int projectId,
			@RequestParam("employees") List<Integer> employeesId) {
		registryService.matchEmployeesWithProject(projectId, employeesId);
		return "redirect:/koordynator/przypiszPracownika";
	}

	@GetMapping("przypiszPracownika/update")
	public String updateEmployeesOfProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = registryService.getProject(projectId);
		List<Employee> projectEmployees = registryService.getEmployeesOfProject(projectId);
		theModel.addAttribute("project", project);
		theModel.addAttribute("employeeList", projectEmployees);
		return "assign-employee-update";
	}

	@GetMapping("/przypiszPracownika/usun")
	public String dischargeEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		registryService.dischargeEmployee(employeeId);
		return "redirect:/koordynator/przypiszPracownika";
	}
	@GetMapping("/pelnaListaRejestrow")
	public String fullRegistryList(Model theModel) {
		List<Project> projects = registryService.getAllProjects();
		List<Registry> registries = registryService.getAllRegistries();
		List<Date> registryDates = registryService.getRegistryDates();
		theModel.addAttribute("projectList", projects);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("registryDates", registryDates);
		return "full-registry-list";
	}

	@PostMapping("/pelnaListaRejestrow/Filtruj")
	public String filtrujListeRejestrow(@RequestParam(name = "projectId") int projectId,
			@RequestParam(name = "startDate") String startStringDate,
			@RequestParam(name = "endDate") String endStringDate, @RequestParam(name = "status") String status,
			Model theModel) throws ParseException, DateNotInScopeException {
		Date startDate = registryService.parseDate(startStringDate);
		Date endDate = registryService.parseDate(endStringDate);
		boolean booleanStatus = Boolean.parseBoolean(status);
		String strStatus = registryService.convertToStringStatus(booleanStatus);
		List<Registry> filteredRegistries = registryService.filterRegistries(projectId, startDate, endDate, booleanStatus);
		Project project = registryService.getProject(projectId);
		theModel.addAttribute("startDate", startStringDate);
		theModel.addAttribute("endDate", endStringDate);
		theModel.addAttribute("status", strStatus);
		theModel.addAttribute("filteredRegistries", filteredRegistries);
		theModel.addAttribute("project", project);
		return "filtered-registry-list";
	}

}
