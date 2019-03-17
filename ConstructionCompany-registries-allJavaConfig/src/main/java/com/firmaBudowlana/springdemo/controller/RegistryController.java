package com.firmaBudowlana.springdemo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.firmaBudowlana.springdemo.entity.Catering;
import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.entity.Accommodation;
import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.Registry;
import com.firmaBudowlana.springdemo.exceptions.DateNotInScopeException;
import com.firmaBudowlana.springdemo.exceptions.DifferentEmployeesInRegistry;
import com.firmaBudowlana.springdemo.exceptions.IncompatibleSizeOfRegistryLists;
import com.firmaBudowlana.springdemo.exceptions.IncorrectDateFormat;
import com.firmaBudowlana.springdemo.service.AccommodationService;
import com.firmaBudowlana.springdemo.service.CateringService;
import com.firmaBudowlana.springdemo.service.DateParserService;
import com.firmaBudowlana.springdemo.service.ManagerService;
import com.firmaBudowlana.springdemo.service.ProjectService;
import com.firmaBudowlana.springdemo.service.RegistryService;
import com.firmaBudowlana.springdemo.service.UserService;

@Controller
@RequestMapping("/rejestr")
public class RegistryController {
	
	//contains all the methods handling these functions, which are available for each authenticated app user
	//it includes all the functions referring to CRUD for Registry.class
	//additionally - methods handling of creating and retrieving Accommodation and Catering objects
	
	@Autowired
	private RegistryService registryService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CateringService cateringService;
	
	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private DateParserService dateParser;
	
	@Value("#{nieobecnoscOpcje}")
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
	
	//Correct date format is "yyyy-mm-dd", if the inputted differs from this format - IncorrectDateFormat exception will be thrown
	@ExceptionHandler(value=IncorrectDateFormat.class)
	public String incorrectDateFormat(Exception exc) {
		exc.printStackTrace();
		return "incorrectDateFormat";
	}
	
	//helper method to display polish names of user's roles
	private static String convertAuthorityToPosition(String strRole) {
		String positionString=null;
		
		if(strRole.equalsIgnoreCase("ROLE_ADMIN")) {
			positionString="Koordynator";
		}else {
			positionString="Kierownik budowy";
		}
		return positionString;
	}
	
	//handles function of filling out registry form
	//form contains option to select project for which registry should be prepared
	//user can point the project from dropdown list, which is populated only by projects assigned to him
	//admin can point the project from the list of all ongoing projects
	@GetMapping("/wypelnijRejestr")
	public String registryForm(Model modelMap) {
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		User myUser = userService.findByUsername(username);
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		Iterator<GrantedAuthority> iter = authorities.iterator();
		String strRole = iter.next().toString();
		String strPosition = convertAuthorityToPosition(strRole);
		List<Project> fullProjectList = projectService.getOngoingProjects();
		List<Project> projectsList = projectService.getOngoingManagerProjects(myUser.getId());
		Registry registry = new Registry();
		List<Catering> cateringList = cateringService.getCatering();
		List<Accommodation> accommodationList = accommodationService.getAccommodationsList();
		modelMap.addAttribute("position", strPosition);
		modelMap.addAttribute("fullProjectList", fullProjectList);
		modelMap.addAttribute("projectList", projectsList);
		modelMap.addAttribute("registry", registry);
		modelMap.addAttribute("absence", absenceOptions);
		modelMap.addAttribute("cateringList", cateringList);
		modelMap.addAttribute("accommodationList", accommodationList);
		return "registry-panel";
	}
	
	//matching registry form with all the project's employees
	@PostMapping("/potwierdzRejestr")
	public String matchRegistryWithEmployees(@Valid @ModelAttribute(name = "registry") Registry registry,
			@RequestParam(name = "id") int registryId, @RequestParam(name = "project") int projectId,
			@RequestParam(name = "registryDate") String stringRegistryDate, @RequestParam(name = "workingTime") int workingTime,
			@RequestParam(name = "absence") String absence, @RequestParam(name = "catering") int cateringId,
			@RequestParam(name = "accommodation") int accommodationId, Model theModel)
			throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStringDate = dateParser.checkStrDateBeforeParse(stringRegistryDate);
		Date registryDate = dateParser.parseDate(correctStringDate);
		List<Registry> registries = registryService.matchRegistriesWithEmployees(projectId, registryDate, workingTime, absence, cateringId, accommodationId);
		Project project = projectService.getProject(projectId);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("registryDate", registryDate);
		theModel.addAttribute("project", project);
		return "confirm-registry";
	}

	@GetMapping("/dodajRejestr")
	public String addRegistry(Model theModel) {
		Registry registry = new Registry();
		List<Catering> cateringList = cateringService.getCatering();
		List<Accommodation> accommodationList = accommodationService.getAccommodationsList();
		theModel.addAttribute("cateringList", cateringList);
		theModel.addAttribute("accommodationList", accommodationList);
		theModel.addAttribute("registry", registry);
		theModel.addAttribute("absence", absenceOptions);
		return "add-Registry";
	}
	
	//update registry record for a specific employee
	@GetMapping("/potwierdzRejestr/update")
	public String editRegistry(@RequestParam(name = "registryId") int registryId, Model theModel) {
		Registry registry = registryService.getRegistry(registryId);
		Date theDate = registry.getDate();
		String strDate = dateParser.convertDateToString(theDate);
		System.out.println(strDate);
		List<Catering> cateringList = cateringService.getCatering();
		List<Accommodation> accommodationList = accommodationService.getAccommodationsList();
		theModel.addAttribute("registry", registry);
		theModel.addAttribute("strDate", strDate);
		theModel.addAttribute("cateringList", cateringList);
		theModel.addAttribute("accommodationList", accommodationList);
		theModel.addAttribute("absence", absenceOptions);
		return "add-Registry";
	}
	
	//returns registry list after updating registry for a pecific employee
	@PostMapping("/potwierdzRejestr/zapiszRejestr")
	public String saveRegistry(@ModelAttribute(name = "id") int registryId,
			@RequestParam(name = "project.id") int projectId, @RequestParam(name = "date") String stringRegistryDate,
			@RequestParam(name="workingTime") int workingTime, @RequestParam(name="absence") String absence,
			@RequestParam(name="catering.id") int cateringId, @RequestParam(name="accommodation.id") int accommodationId,
			Model theModel) throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStringDate = dateParser.checkStrDateBeforeParse(stringRegistryDate);
		Date registryDate = dateParser.parseDate(correctStringDate);
		registryService.saveRegistry(registryId, workingTime, registryDate, absence, cateringId, accommodationId);
		Project project = projectService.getProject(projectId);
		List<Registry> registries = registryService.getRegistryList(projectId, registryDate);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		theModel.addAttribute("registries", registries);
		return "registry-list";
	}
	
	//delete all registries for a specific date
	//it is not allowed to add or delete registry entry for a single employee
	@GetMapping("/potwierdzRejestr/usun")
	public String deleteRegistries(@RequestParam(name = "projectId") int projectId,
			@RequestParam(name = "registryDate") String strDate, Model theModel)
			throws ParseException, DateNotInScopeException {
		String convertableDateStr = dateParser.convertUnparseableDateString(strDate);
		Date registryDate = dateParser.parseDate(convertableDateStr);
		registryService.deleteRegistries(projectId, registryDate);
		Project project = projectService.getProject(projectId);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		return "delete-registries";
	}
	
	//selecting project of registry to copy
	@GetMapping("/wybierzProjektDoSkopiowania")
	public String selectProjectToRegistryCopy(Model theModel) {
		org.springframework.security.core.userdetails.User theUser 
				= (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		List<Project> projects = projectService.getOngoingManagerProjects(myUser.getId());
		List<Project> allProjects = projectService.getOngoingProjects();
		Project project = new Project();
		theModel.addAttribute("projects", projects);
		theModel.addAttribute("allProjects", allProjects);
		theModel.addAttribute("project", project);
		return "select-project-to-copy";
	}
	
	//selecting project of registry to copy
	@PostMapping("/wybierzDateProjektu")
	public String selectDateToRegistryCopy(@ModelAttribute(name = "project") Project project, Model theModel) {
		int projectId = project.getId();
		List<Date> registryDates = registryService.getRegistryDates(projectId);
		User user = managerService.getProjectManager(projectId);
		Registry registry = new Registry();
		registry.setProject(project);
		project = projectService.getProject(projectId);
		theModel.addAttribute("registryDates", registryDates);
		theModel.addAttribute("project", project);
		theModel.addAttribute("user", user);
		theModel.addAttribute("registry", registry);
		return "select-date-to-copy";

	}
	
	
	//displays copy of registry from selected past date
	@PostMapping("/kopiaRejestru/rejestr")
	public String showRegistryCopy(@RequestParam(name = "project.id") int projectId, @RequestParam(name = "date") String strOldDate,
			@RequestParam(name = "newDate") String strNewDate, Model theModel) throws ParseException,
			DateNotInScopeException, IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry, IncorrectDateFormat {
		String correctStrDate = dateParser.checkStrDateBeforeParse(strNewDate);
		Project project = projectService.getProject(projectId);
		Date oldDate = dateParser.parseDate(strOldDate);
		Date newDate = dateParser.parseDate(correctStrDate);
		List<Registry> registries = registryService.copyRegistgryList(projectId, newDate, oldDate);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", newDate);
		theModel.addAttribute("registries", registries);
		return "registry-list";
	}
	
	//edit registry - input registry date
	@GetMapping("/edytujRejestr")
	public String filterRegistriesToEdit(Model theModel) {
		org.springframework.security.core.userdetails.User theUser = 
				(org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		List<Project> projectList = projectService.getOngoingManagerProjects(myUser.getId());
		List<Project> fullProjectList = projectService.getOngoingProjects();
		Project project = new Project();
		theModel.addAttribute("project", project);
		theModel.addAttribute("projectList", projectList);
		theModel.addAttribute("fullProjectList", fullProjectList);
		return "select-registry-to-edit";
	}
	
	//returns list of registry to edit
	@PostMapping("/edytujRejestr/lista")
	public String registriesListToEdit(@ModelAttribute(name = "project") Project project,
			@RequestParam(name = "registryDate") String strRegistryDate,
			Model theModel) throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStrDate = dateParser.checkStrDateBeforeParse(strRegistryDate);
		int projectId = project.getId();
		project = projectService.getProject(projectId);
		Date registryDate = dateParser.parseDate(correctStrDate);		
		List<Registry> registries = registryService.getRegistryList(projectId, registryDate);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		return "registry-list";
	}
	
	//methods handling creating and retrieving catering and accommodations objects
	
	@GetMapping("/dodajCatering")
	public String addCatering(Model theModel) {
		Catering catering = new Catering();
		theModel.addAttribute("catering", catering);
		return "add-catering";
	}

	@PostMapping("/zapiszCatering")
	public String saveCatering(@Valid @ModelAttribute("catering") Catering catering, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "add-catering";
		}
		cateringService.saveCatering(catering);
		return "catering-confirmation";
	}

	@GetMapping("/dodajNocleg")
	public String addAccommodation(Model theModel) {
		Accommodation accomodation = new Accommodation();
		theModel.addAttribute("accommodation", accomodation);
		return "add-accommodation";
	}

	@PostMapping("/zapiszNocleg")
	public String saveAccommodation(@Valid @ModelAttribute("accommodation") Accommodation accommodation, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "add-accommodation";
		}
		accommodationService.saveAccommodation(accommodation);
		return "accommodation-confirmation";
	}

}
