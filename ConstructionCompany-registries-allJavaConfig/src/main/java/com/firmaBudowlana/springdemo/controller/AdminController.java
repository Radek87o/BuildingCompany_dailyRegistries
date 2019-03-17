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
import com.firmaBudowlana.springdemo.service.DateParserService;
import com.firmaBudowlana.springdemo.service.EmployeeService;
import com.firmaBudowlana.springdemo.service.ManagerService;
import com.firmaBudowlana.springdemo.service.ProjectService;
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
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DateParserService dateParser;
	
	@Resource(name="nieobecnoscOpcje")
	private Map<String, String> absenceOptions;
	
	//protects from accepting white space values for input fields
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(Date.class, "data", new CustomDateEditor(dateFormat, false));
		dataBinder.registerCustomEditor(String.class, stringTrimmer);
	}
	
	//returns view informing that the particular record has been already added to the database
	@ExceptionHandler(value = ConstraintViolationException.class)
	public String constraintViolationExceptionHandler(Exception exc) {
		exc.printStackTrace();
		return "ConstraintViolationException";
	}
	
	//refers to function 'copy registry for past date'
	//Admin can modify list of project's employees in the course of its implementation
	//If the employees list size differs between current registry and past registry, IncompatibleSizeOfRegistryLists exception will be thrown 
	//and will be returned the before mentioned view
	@ExceptionHandler(value = IncompatibleSizeOfRegistryLists.class)
	public String incompatibleSizeOfRegistryListsHandler(Exception exc) {
		exc.printStackTrace();
		return "incompatibleSizeOfRegistryLists";
	}

	//it is not allowed to fill out a registry form with future date
	//In this case, DateNotInScopeException will be thrown and will be returned the before mentioned view
	@ExceptionHandler(value = DateNotInScopeException.class)
	public String dateNotInScopeExceptionHandler(Exception exc) {
		exc.printStackTrace();
		return "DateNotInScopeException";
	}
	
	//refers to function 'copy registry for past date'
	//Admin can modify list of project's employees in the course of its implementation
	//If employees lists for current and past registry differ each other, DifferentEmployeesInRegistry exception will be thrown
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
	
	//validating registration form
	//only admin is allowed to register new manager
	//if users exists - returns populated form to update manager
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
		List<User> userList = managerService.getManagerListWithoutAdmin();
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
		managerService.deleteManager(userId);
		return "redirect:/koordynator/listaKierownikow";
	}
	
	//returns view allowing to create project and assign it to manager
	@GetMapping("/dodajProjekt")
	public String addProject(Model theModel) {
		Project project = new Project();
		LinkedHashMap<Integer, String> userList = managerService.getManagersWithoutAdmin();
		List<Project> projectList = projectService.getOngoingProjects();
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
		projectService.saveProject(project, userId);
		return "redirect:/koordynator/dodajProjekt";
	}

	@GetMapping("/updateProjektu")
	public String updateProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = projectService.getProject(projectId);
		LinkedHashMap<Integer, String> userList = managerService.getManagers();
		theModel.addAttribute("project", project);
		theModel.addAttribute("userList", userList);
		return "add-project";
	}

	@GetMapping("/usunProjekt")
	public String deleteProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = projectService.getProject(projectId);
		theModel.addAttribute("project", project);
		projectService.deleteProject(projectId);
		return "redirect:/koordynator/dodajProjekt";
	}
	
	@GetMapping("/dodajPracownika")
	public String addEmployee(Model theModel) {
		Employee employee = new Employee();
		List<Employee> employeeList = employeeService.getEmployees();
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
		employeeService.saveEmployee(employee);
		return "redirect:/koordynator/dodajPracownika";
	}

	@GetMapping("/updatePracownika")
	public String updateEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		Employee employee = employeeService.getEmployee(employeeId);
		theModel.addAttribute("employee", employee);
		return "add-employee";
	}

	@GetMapping("/usunPracownika")
	public String deleteEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		Employee employee = employeeService.getEmployee(employeeId);
		theModel.addAttribute("employee", employee);
		employeeService.deleteEmployee(employeeId);
		return "redirect:/koordynator/dodajPracownika";
	}
	
	//assigning employee to project
	//admin can assign to ongoing project only the employees not assigned yet to any ongoing project
	//returns view with table calculating the current number of employees assigned to the particular project
	@GetMapping("/przypiszPracownika")
	public String matchEmployeeWithProject(Model theModel) {
		Project project = new Project();
		List<Project> projectList = projectService.getOngoingProjects();
		List<Employee> employeeList = employeeService.getAvailableEmployees();
		LinkedHashMap<Project, Long> employeesPerProject = employeeService.getNumberOfEmployeesPerProject();
		theModel.addAttribute("project", project);
		theModel.addAttribute("projectList", projectList);
		theModel.addAttribute("employeeList", employeeList);
		theModel.addAttribute("employeesPerProject", employeesPerProject);
		return "assign-employee";
	}
	
	//assigning employee to the project
	@PostMapping("/dodajPracownikowDoProjektu")
	public String addEmployeesToProject(@RequestParam("id") int projectId,
			@RequestParam("employees") List<Integer> employeesId) {
		employeeService.matchEmployeesWithProject(projectId, employeesId);
		return "redirect:/koordynator/przypiszPracownika";
	}
	
	//returns view containing list of employees assigned to project
	@GetMapping("przypiszPracownika/update")
	public String updateEmployeesOfProject(@RequestParam(name = "projectId") int projectId, Model theModel) {
		Project project = projectService.getProject(projectId);
		List<Employee> projectEmployees = employeeService.getEmployeesOfProject(projectId);
		theModel.addAttribute("project", project);
		theModel.addAttribute("employeeList", projectEmployees);
		return "assign-employee-update";
	}
	
	//it signs the employee out of project and returns the object back to the pool of available employees
	@GetMapping("/przypiszPracownika/usun")
	public String dischargeEmployee(@RequestParam(name = "employeeId") int employeeId, Model theModel) {
		employeeService.dischargeEmployee(employeeId);
		return "redirect:/koordynator/przypiszPracownika";
	}
	
	@GetMapping("/pelnaListaRejestrow")
	public String fullRegistryList(Model theModel) {
		List<Project> projects = projectService.getAllProjects();
		List<Registry> registries = registryService.getAllRegistries();
		List<Date> registryDates = registryService.getRegistryDates();
		theModel.addAttribute("projectList", projects);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("registryDates", registryDates);
		return "full-registry-list";
	}
	
	//helper method to display polish name of project status
	private static String convertToStringStatus(boolean status) {
		String strStatus="";
		if(status==false) {
			strStatus="aktywny";
		}
		else {
			strStatus="zakoñczony";
		}
		return strStatus;
	}
	
	@PostMapping("/pelnaListaRejestrow/Filtruj")
	public String filterRegistryList(@RequestParam(name = "projectId") int projectId,
			@RequestParam(name = "startDate") String startStringDate,
			@RequestParam(name = "endDate") String endStringDate, @RequestParam(name = "status") String status,
			Model theModel) throws ParseException, DateNotInScopeException {
		Date startDate = dateParser.parseDate(startStringDate);
		Date endDate = dateParser.parseDate(endStringDate);
		boolean booleanStatus = Boolean.parseBoolean(status);
		String strStatus = convertToStringStatus(booleanStatus);
		List<Registry> filteredRegistries = registryService.filterRegistries(projectId, startDate, endDate, booleanStatus);
		Project project = projectService.getProject(projectId);
		theModel.addAttribute("startDate", startStringDate);
		theModel.addAttribute("endDate", endStringDate);
		theModel.addAttribute("status", strStatus);
		theModel.addAttribute("filteredRegistries", filteredRegistries);
		theModel.addAttribute("project", project);
		return "filtered-registry-list";
	}

}
