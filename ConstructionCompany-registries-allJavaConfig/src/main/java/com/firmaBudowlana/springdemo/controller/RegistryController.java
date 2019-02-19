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
import com.firmaBudowlana.springdemo.service.RegistryService;
import com.firmaBudowlana.springdemo.service.UserService;

@Controller
@RequestMapping("/rejestr")
public class RegistryController {
	
	//handle all the functions referring to CRUD for Registry.class
	//additionally - methods handling creating Accommodation and Catering objects
	//available for each authenticated app user
	
	@Autowired
	private RegistryService registryService;
	
	@Autowired
	private UserService userService;

	@Value("#{nieobecnoscOpcje}")
	private Map<String, String> absenceOptions;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
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
	
	@ExceptionHandler(value=IncorrectDateFormat.class)
	public String incorrectDateFormat(Exception exc) {
		exc.printStackTrace();
		return "incorrectDateFormat";
	}
	
	@GetMapping("/wypelnijRejestr")
	public String registryForm(Model modelMap) {
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		User myUser = userService.findByUsername(username);
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		Iterator<GrantedAuthority> iter = authorities.iterator();
		String strRole = iter.next().toString();
		String strPosition = registryService.convertAuthorityToPosition(strRole);
		List<Project> fullProjectList = registryService.getOngoingProjects();
		List<Project> projectsList = registryService.getOngoingManagerProjects(myUser.getId());
		Registry registry = new Registry();
		List<Catering> cateringList = registryService.getCatering();
		List<Accommodation> accommodationList = registryService.getAccommodationsList();
		modelMap.addAttribute("position", strPosition);
		modelMap.addAttribute("fullProjectList", fullProjectList);
		modelMap.addAttribute("projectList", projectsList);
		modelMap.addAttribute("registry", registry);
		modelMap.addAttribute("absence", absenceOptions);
		modelMap.addAttribute("cateringList", cateringList);
		modelMap.addAttribute("accommodationList", accommodationList);
		return "registry-panel";
	}

	@PostMapping("/potwierdzRejestr")
	public String matchRegistryWithEmployees(@Valid @ModelAttribute(name = "registry") Registry registry,
			@RequestParam(name = "id") int registryId, @RequestParam(name = "project") int projectId,
			@RequestParam(name = "registryDate") String stringRegistryDate, @RequestParam(name = "workingTime") int workingTime,
			@RequestParam(name = "absence") String absence, @RequestParam(name = "catering") int cateringId,
			@RequestParam(name = "accommodation") int accommodationId, Model theModel)
			throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStringDate = registryService.checkStrDateBeforeParse(stringRegistryDate);
		Date registryDate = registryService.parseDate(correctStringDate);
		List<Registry> registries = registryService.matchRegistriesWithEmployees(projectId, registryDate, workingTime, absence, cateringId, accommodationId);
		Project project = registryService.getProject(projectId);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("registryDate", registryDate);
		theModel.addAttribute("project", project);
		return "confirm-registry";
	}

	@GetMapping("/dodajRejestr")
	public String addRegistry(Model theModel) {
		Registry registry = new Registry();
		List<Catering> cateringList = registryService.getCatering();
		List<Accommodation> accommodationList = registryService.getAccommodationsList();
		theModel.addAttribute("cateringList", cateringList);
		theModel.addAttribute("accommodationList", accommodationList);
		theModel.addAttribute("registry", registry);
		theModel.addAttribute("absence", absenceOptions);
		return "add-Registry";
	}

	@GetMapping("/potwierdzRejestr/update")
	public String editRegistry(@RequestParam(name = "registryId") int registryId, Model theModel) {
		Registry registry = registryService.getRegistry(registryId);
		Date theDate = registry.getDate();
		String strDate = registryService.convertDateToString(theDate);
		System.out.println(strDate);
		List<Catering> cateringList = registryService.getCatering();
		List<Accommodation> accommodationList = registryService.getAccommodationsList();
		theModel.addAttribute("registry", registry);
		theModel.addAttribute("strDate", strDate);
		theModel.addAttribute("cateringList", cateringList);
		theModel.addAttribute("accommodationList", accommodationList);
		theModel.addAttribute("absence", absenceOptions);
		return "add-Registry";
	}

	@PostMapping("/potwierdzRejestr/zapiszRejestr")
	public String saveRegistry(@ModelAttribute(name = "id") int registryId,
			@RequestParam(name = "project.id") int projectId, @RequestParam(name = "date") String stringRegistryDate,
			@RequestParam(name="workingTime") int workingTime, @RequestParam(name="absence") String absence,
			@RequestParam(name="catering.id") int cateringId, @RequestParam(name="accommodation.id") int accommodationId,
			Model theModel) throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStringDate = registryService.checkStrDateBeforeParse(stringRegistryDate);
		Date registryDate = registryService.parseDate(correctStringDate);
		registryService.saveRegistry(registryId, workingTime, registryDate, absence, cateringId, accommodationId);
		Project project = registryService.getProject(projectId);
		List<Registry> registries = registryService.getRegistryList(projectId, registryDate);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		theModel.addAttribute("registries", registries);
		return "registry-list";
	}

	@GetMapping("/potwierdzRejestr/usun")
	public String deleteRegistries(@RequestParam(name = "projectId") int projectId,
			@RequestParam(name = "registryDate") String strDate, Model theModel)
			throws ParseException, DateNotInScopeException {
		String convertableDateStr = registryService.convertUnparseableDateString(strDate);
		Date registryDate = registryService.parseDate(convertableDateStr);
		registryService.deleteRegistries(projectId, registryDate);
		Project project = registryService.getProject(projectId);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		return "delete-registries";
	}

	@GetMapping("/wybierzProjektDoSkopiowania")
	public String selectProjectToRegistryCopy(Model theModel) {
		org.springframework.security.core.userdetails.User theUser 
				= (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		List<Project> projects = registryService.getOngoingManagerProjects(myUser.getId());
		List<Project> allProjects = registryService.getOngoingProjects();
		Project project = new Project();
		theModel.addAttribute("projects", projects);
		theModel.addAttribute("allProjects", allProjects);
		theModel.addAttribute("project", project);
		return "select-project-to-copy";
	}
	
	@PostMapping("/wybierzDateProjektu")
	public String selectDateToRegistryCopy(@ModelAttribute(name = "project") Project project, Model theModel) {
		int projectId = project.getId();
		List<Date> registryDates = registryService.getRegistryDates(projectId);
		User user = registryService.getProjectManager(projectId);
		Registry registry = new Registry();
		registry.setProject(project);
		project = registryService.getProject(projectId);
		theModel.addAttribute("registryDates", registryDates);
		theModel.addAttribute("project", project);
		theModel.addAttribute("user", user);
		theModel.addAttribute("registry", registry);
		return "select-date-to-copy";

	}

	@PostMapping("/kopiaRejestru/rejestr")
	public String showRegistryCopy(@RequestParam(name = "project.id") int projectId, @RequestParam(name = "date") String strOldDate,
			@RequestParam(name = "newDate") String strNewDate, Model theModel) throws ParseException,
			DateNotInScopeException, IncompatibleSizeOfRegistryLists, DifferentEmployeesInRegistry, IncorrectDateFormat {
		String correctStrDate = registryService.checkStrDateBeforeParse(strNewDate);
		Project project = registryService.getProject(projectId);
		Date oldDate = registryService.parseDate(strOldDate);
		Date newDate = registryService.parseDate(correctStrDate);
		List<Registry> registries = registryService.copyRegistgryList(projectId, newDate, oldDate);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", newDate);
		theModel.addAttribute("registries", registries);
		return "registry-list";
	}

	@GetMapping("/edytujRejestr")
	public String filterRegistriesToEdit(Model theModel) {
		org.springframework.security.core.userdetails.User theUser = 
				(org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		List<Project> projectList = registryService.getOngoingManagerProjects(myUser.getId());
		List<Project> fullProjectList = registryService.getOngoingProjects();
		Project project = new Project();
		theModel.addAttribute("project", project);
		theModel.addAttribute("projectList", projectList);
		theModel.addAttribute("fullProjectList", fullProjectList);
		return "select-registry-to-edit";
	}

	@PostMapping("/edytujRejestr/lista")
	public String registriesListToEdit(@ModelAttribute(name = "project") Project project,
			@RequestParam(name = "registryDate") String strRegistryDate,
			Model theModel) throws ParseException, DateNotInScopeException, IncorrectDateFormat {
		String correctStrDate = registryService.checkStrDateBeforeParse(strRegistryDate);
		int projectId = project.getId();
		project = registryService.getProject(projectId);
		Date registryDate = registryService.parseDate(correctStrDate);		
		List<Registry> registries = registryService.getRegistryList(projectId, registryDate);
		theModel.addAttribute("registries", registries);
		theModel.addAttribute("project", project);
		theModel.addAttribute("registryDate", registryDate);
		return "registry-list";
	}
	
	
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
		registryService.saveCatering(catering);
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
		registryService.saveAccommodation(accommodation);
		return "accommodation-confirmation";
	}

}
